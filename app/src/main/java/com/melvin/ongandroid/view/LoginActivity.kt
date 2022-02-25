package com.melvin.ongandroid.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
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
import java.net.UnknownHostException
import java.security.Principal

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    var emailValid = false
    var passwordValid = false
    private val userViewModel by viewModels<UserViewModel> { VMFactory(RepoImpl(DataSource())) }
    lateinit var prefHelper: PrefHelper
    private val callbackManager = CallbackManager.Factory.create()


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
                        binding.tvPassword.text.toString(),
                        binding.tvEmail.text.toString()
                    )
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
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
            .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                binding.prBar.visibility = View.GONE
            }.show()
    }

    private fun saveSession(username: String, password: String) {
        prefHelper.put(Constant.PREF_USERNAME, username)
        prefHelper.put(Constant.PREF_PASSWORD, password)
        prefHelper.put(Constant.PREF_IS_LOGIN, true)
    }

    private fun googleLogin() {

        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        binding.googleButton.setOnClickListener {
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setIsSmartLockEnabled(false)
                    .build(),
                RC_SIGN_IN
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                showDialog("Ocurrio un error ${response?.error?.errorCode}")
            }


        }
    }

    fun facebookLogin() {
        binding.fbButton.setOnClickListener {
            binding.prBar.visibility = View.VISIBLE
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {

                    override fun onSuccess(result: LoginResult?) {
                        if (result != null) {
                            //guardamos el login en firebase
                            binding.prBar.visibility = View.VISIBLE
                            val token = result.accessToken
                            val credential = FacebookAuthProvider.getCredential(token.token)
                            FirebaseAuth.getInstance().signInWithCredential(credential)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        startActivity(
                                            Intent(
                                                applicationContext,
                                                MainActivity::class.java
                                            )
                                        )
                                        finish()
                                    } else {
                                        // no se pudo autenticar con firebase
                                    }
                                }
                        }

                    }

                    override fun onCancel() {

                    }

                    override fun onError(error: FacebookException?) {
                        binding.prBar.visibility = View.INVISIBLE
                        showDialog("No se pudo iniciar sesión")
                    }

                })
        }
    }
}