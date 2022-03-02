package com.melvin.ongandroid.view

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.internal.CallbackManagerImpl
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.melvin.ongandroid.R
import com.melvin.ongandroid.model.DataSource.DataSource
import com.melvin.ongandroid.businesslogic.data.PrefHelper
import com.melvin.ongandroid.databinding.ActivityLoginBinding
import com.melvin.ongandroid.model.repository.Constant
import com.melvin.ongandroid.model.repository.Constant.Companion.RC_SIGN_IN
import com.melvin.ongandroid.model.repository.RepoImpl
import com.melvin.ongandroid.viewmodel.UserViewModel
import com.melvin.ongandroid.viewmodel.VMFactory
import retrofit2.HttpException
import java.io.IOException

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    var emailValid = false
    var passwordValid = false
    private val userViewModel by viewModels<UserViewModel> { VMFactory(RepoImpl(DataSource())) }
    private lateinit var prefHelper: PrefHelper
    private lateinit var callbackManager: CallbackManager
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefHelper = PrefHelper(this)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.isEnabled = false

        googleLogin()
        facebookLogin()
        setListeners()
        emailValidation()
        passwordValidation()
        setObservers()
    }

    override fun onBackPressed() {
        finishAffinity()
        finish()
    }

    private fun setListeners() {
        binding.btnSignUp.setOnClickListener { viewSignUpActivity() }
        binding.btnLogin.setOnClickListener {
            binding.prBar.visibility = View.VISIBLE

            userViewModel.postToken(
                binding.tvEmail.text.toString(),
                binding.tvPassword.text.toString()
            )
        }
    }

    private fun passwordValidation() {
        binding.tvPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                hideMessageUserNotExist()
                passwordValid = binding.tvPassword.text.toString().length >= 4
                validFields()
            }
        })
        val filter =
            InputFilter { source, start, end, dest, dstart, dend ->
                for (i in start until end) {
                    if (!Character.toString(source[i]).matches("[a-zA-Z0-9]+".toRegex())) {
                        binding.tvPassword.error =
                            getString(R.string.login_tv_error_only_letters_and_digits)
                        return@InputFilter ""
                    }
                }
                null
            }
        binding.tvPassword.filters = arrayOf(filter)
    }

    private fun emailValidation() {
        binding.tvEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                hideMessageUserNotExist()
                if (Patterns.EMAIL_ADDRESS.matcher(binding.tvEmail.text.toString())
                        .matches()
                ) {
                    emailValid = true
                } else {
                    emailValid = false
                    binding.tvEmail.error = getString(R.string.login_tv_error_invalid_mail)
                }
                validFields()
            }
        })
    }

    private fun viewSignUpActivity() {
        startActivity(Intent(this, SignUpActivity::class.java))
        finish()
    }

    private fun hideMessageUserNotExist() {
        binding.tvEmail.error = null
        binding.tvPassword.error = null
    }

    fun validFields() {
        binding.btnLogin.isEnabled = emailValid && passwordValid
    }

    private fun setObservers() {
        userViewModel.liveDataUser.observe(this) {
            if (it != null) {
                if (it.success == true) {
                    binding.prBar.visibility = View.GONE
                    saveSession(
                        binding.tvEmail.text.toString(),
                        binding.tvPassword.text.toString(),
                        it.data?.user?.name!!
                    )
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                    finish()
                } else {
                    binding.prBar.visibility = View.GONE
                    binding.tvEmail.error =
                        getString(R.string.login_et_error_user_and_password)
                    binding.tvPassword.error =
                        getString(R.string.login_et_error_user_and_password)
                }
            }
        }
        userViewModel.authException.observe(this, this::handleException)
    }

    private fun handleException(exception: Throwable?) {
        if (exception is HttpException)
            when (exception.code()) {
                400 -> showDialog(getString(R.string.login_dg_bad_request))
                404 -> showDialog(getString(R.string.login_dg_resource_not_found))
                in 500..599 -> showDialog(getString(R.string.login_dg_server_error))
                else -> showDialog(getString(R.string.login_dg_unknown_error))
            }
        if (exception is IOException)
            showDialog(getString(R.string.login_dg_without_internet))
    }

    private fun showDialog(message: String) {
        MaterialAlertDialogBuilder(this).setMessage(message)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                binding.prBar.visibility = View.GONE
            }.show()
    }

    private fun saveSession(username: String, password: String, name:String) {
        prefHelper.put(Constant.PREF_USERNAME, username)
        prefHelper.put(Constant.PREF_PASSWORD, password)
        prefHelper.put(Constant.PREF_NAME, name)
        prefHelper.put(Constant.PREF_IS_LOGIN, true)
    }

    private fun googleLogin() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = Firebase.auth

        binding.googleButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK) {
            // Login Facebook
            if(requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()) {
                callbackManager.onActivityResult(requestCode, resultCode, data)
            } else if (requestCode == RC_SIGN_IN) { // login with Google
                val response = IdpResponse.fromResultIntent(data)
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)

                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)

                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    showDialog("Ocurrio un error ${response?.error?.errorCode}")
                    Log.w(TAG, "Google sign in failed", e)
                }
            }
        } else {
            // usuario preciona back en el dialogo de inicio se sesion con redes sociales:
            showDialog("No se pudo iniciar sesión")
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun facebookLogin() {
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().loginBehavior = LoginBehavior.WEB_ONLY
        binding.btFacebook.setOnClickListener {
            binding.prBar.visibility = View.VISIBLE

            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))

            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {

                    override fun onSuccess(result: LoginResult?) {
                        if (result != null) {
                            //guardamos el login en firebase
                            val token = result.accessToken
                            val credential = FacebookAuthProvider.getCredential(token.token)
                            firebaseSignInWithCredential(credential)
                        }else{
                            showDialog("Ocurrió un error inesperado")
                        }
                    }

                    override fun onCancel() {
                        binding.prBar.visibility = View.GONE
                        showDialog("Se canceló inicio de sesión")
                    }

                    override fun onError(error: FacebookException?) {
                        binding.prBar.visibility = View.GONE
                        showDialog("No se pudo iniciar sesión")
                    }
                }
            )
        }
    }

    private fun firebaseSignInWithCredential(credential: AuthCredential) {
        binding.prBar.visibility = View.VISIBLE
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                    finish()
                } else {
                    MaterialAlertDialogBuilder(this).setMessage("no se pudo iniciar sesion correctamente")
                        .setCancelable(false)
                        .setPositiveButton("Reintentar") { dialog, which ->
                            firebaseSignInWithCredential(credential)
                        }
                        .setNegativeButton("Cancelar"){dialog, which ->
                            binding.prBar.visibility = View.GONE
                            LoginManager.getInstance().logOut()
                        }
                        .show()
                }
            }
    }

}