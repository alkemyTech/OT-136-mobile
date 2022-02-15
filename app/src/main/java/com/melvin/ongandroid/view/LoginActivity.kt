package com.melvin.ongandroid.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.melvin.ongandroid.R
import com.melvin.ongandroid.businesslogic.data.DataSource
import com.melvin.ongandroid.businesslogic.data.PrefHelper
import com.melvin.ongandroid.databinding.ActivityLoginBinding
import com.melvin.ongandroid.model.repository.Constant
import com.melvin.ongandroid.model.repository.RepoImpl
import com.melvin.ongandroid.viewmodel.UserViewModel
import com.melvin.ongandroid.viewmodel.VMFactory
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    var emailValid = false
    var passwordValid = false
    private val userViewModel by viewModels<UserViewModel> { VMFactory(RepoImpl(DataSource())) }
    lateinit var prefHelper: PrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefHelper = PrefHelper(this)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.isEnabled = false

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
                if (it.success!!) {
                    saveSession(
                        binding.tvPassword.text.toString(),
                        binding.tvEmail.text.toString()
                    )
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()

                } else {
                    binding.tvEmail.error = getString(R.string.login_et_error_user_and_password)
                }
            }
        }
        userViewModel.liveDataUser.observe(this) {
            if (it != null) {
                if (it.success == true) {
                    binding.prBar.visibility = View.GONE
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
        if (exception is UnknownHostException)
            showDialog(getString(R.string.login_dg_without_internet))
    }

    private fun showDialog(message: String) {
        MaterialAlertDialogBuilder(this).setMessage(message).setPositiveButton(getString(R.string.ok)){
                dialog, which -> {}
        }.show()
    }

    private fun saveSession(username: String, password: String){
        prefHelper.put( Constant.PREF_USERNAME, username )
        prefHelper.put( Constant.PREF_PASSWORD, password )
        prefHelper.put( Constant.PREF_IS_LOGIN, true)
    }
}