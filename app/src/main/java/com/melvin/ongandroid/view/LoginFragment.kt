package com.melvin.ongandroid.view

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import androidx.navigation.fragment.findNavController
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.FragmentLoginBinding
import com.melvin.ongandroid.viewmodel.UserViewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    var emailValid = false
    var passwordValid = false
    val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        _binding!!.btnLogin.setOnClickListener {
            //move to activity Home
            userViewModel.postToken(
                _binding!!.tvEmail.text.toString(),
                _binding!!.tvPassword.text.toString()
            )
        }

        _binding!!.btnLogin.setEnabled(false)

        _binding!!.tvEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(_binding!!.tvEmail.text.toString())
                        .matches()
                ) {
                    emailValid = true
                } else {
                    emailValid = false
                    _binding!!.tvEmail.setError("Invalid Email")
                }
                validFields()
            }
        })

        _binding!!.tvPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (_binding!!.tvPassword.text.toString().length >= 4) {
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
                        _binding!!.tvPassword.setError("Only letters and digits")
                        return@InputFilter ""
                    }
                }
                null
            }
        _binding!!.tvPassword.setFilters(arrayOf(filter))

        _binding!!.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }

        return binding.root
    }


    fun validFields() {
        if (emailValid == true && passwordValid == true) {
            _binding!!.btnLogin.setEnabled(true)
        } else {
            _binding!!.btnLogin.setEnabled(false)
        }

    }

}