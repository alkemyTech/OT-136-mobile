package com.melvin.ongandroid.view

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.melvin.ongandroid.R
import com.melvin.ongandroid.businesslogic.data.DataSource
import com.melvin.ongandroid.databinding.FragmentLoginBinding
import com.melvin.ongandroid.model.repository.RepoImpl
import com.melvin.ongandroid.viewmodel.SignUpViewModel
import com.melvin.ongandroid.viewmodel.UserViewModel
import com.melvin.ongandroid.viewmodel.VMFactory
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    var emailValid = false
    var passwordValid = false
    private val userViewModel by viewModels<UserViewModel>(){ VMFactory(RepoImpl(DataSource())) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        _binding!!.btnLogin.setOnClickListener {
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
                hideMessageUserNotExist()
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
                hideMessageUserNotExist()
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
        binding.tvPassword.setFilters(arrayOf(filter))

        setObservers()

        return binding.root
    }

    private fun hideMessageUserNotExist() {
        _binding!!.tvEmail.error = null
        _binding!!.tvPassword.error = null
    }


    fun validFields() {
        if (emailValid == true && passwordValid == true) {
            _binding!!.btnLogin.setEnabled(true)
        } else {
            _binding!!.btnLogin.setEnabled(false)
        }
    }

    private fun setObservers() {
        userViewModel.liveDataUser.observe(viewLifecycleOwner,{
            if (it != null){
                if (it.success){
                    Toast.makeText(context, "El usuario existe. Vamos al HOME", Toast.LENGTH_SHORT).show()
                } else{
                    _binding!!.tvEmail.error = getString(R.string.login_et_error_user_and_password)
                    _binding!!.tvPassword.error = getString(R.string.login_et_error_user_and_password)
                }

            }
        })
        userViewModel.authException.observe(viewLifecycleOwner, this::handleException)
    }

    private fun handleException(exception: Throwable?) {
        if (exception is HttpException)
            when (exception.code()) {
                400 -> showDialog ("Solicitud incorrecta")
                404 -> showDialog ("No se encontró el recurso")
                in 500..599 -> showDialog ("Ocurrió un error en el servidor")
                else -> showDialog ("Error desconocido")
            }
        if (exception is IOException)
            showDialog("Verifique su conexión a internet y vuelva a intentarlo")
        if (exception is UnknownHostException)
            showDialog("Verifique su conexión a internet y vuelva a intentarlo")

    }

    private fun showDialog(message: String) {
        MaterialAlertDialogBuilder(requireContext()).setMessage(message).setPositiveButton("Ok"){
                dialog, which -> {}
        }.show()
    }
}