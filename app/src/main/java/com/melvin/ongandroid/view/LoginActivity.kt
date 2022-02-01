package com.melvin.ongandroid.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.ActivityLoginBinding
import com.melvin.ongandroid.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    var emailValid = false
    var passwordValid = false
    val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        bind()



        binding.btnLogin.setOnClickListener {
            //move to activity Home
            userViewModel.postToken(
                binding.tvEmail.text.toString(),
                binding.tvPassword.text.toString()
            )
        }

        binding.btnSignUp.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }

        binding.btnLogin.setEnabled(false)

        binding.tvEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(binding.tvEmail.text.toString())
                        .matches()
                ) {
                    emailValid = true
                } else {
                    emailValid = false
                    binding.tvEmail.setError("Invalid Email")
                }
                validFields()
            }
        })

        binding.tvPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.tvPassword.text.toString().length >= 4) {
                    passwordValid = true
                } else {
                    passwordValid = false
                }
                validFields()
            }
        })
        val filter =
            InputFilter { source, start, end, dest, dstart, dend ->
                for (i in start until end) {
                    if (!Character.toString(source[i]).matches("[a-zA-Z0-9]+".toRegex())) {
                        binding.tvPassword.setError("Only letters and digits")
                        return@InputFilter ""
                    }
                }
                null
            }
        binding.tvPassword.setFilters(arrayOf(filter))


    }

    private fun bind() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun validFields() {
        if (emailValid == true && passwordValid == true) {
            binding.btnLogin.setEnabled(true)
        } else {
            binding.btnLogin.setEnabled(false)
        }

    }

}