package com.melvin.ongandroid.view

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.melvin.ongandroid.R
import com.melvin.ongandroid.businesslogic.data.DataSource
import com.melvin.ongandroid.databinding.FragmentLoginBinding
import com.melvin.ongandroid.model.repository.RepoImpl
import com.melvin.ongandroid.viewmodel.UserViewModel
import com.melvin.ongandroid.viewmodel.VMFactory
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    var emailIsValid = false
    var passwordIsValid = false
    private val userViewModel by viewModels<UserViewModel> { VMFactory(RepoImpl(DataSource()))}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        _binding!!.btnLogin.setOnClickListener {
            _binding!!.prBar.visibility = View.VISIBLE
            userViewModel.postToken(
                _binding!!.tvEmail.text.toString(),
                _binding!!.tvPassword.text.toString()
            )
        }

        _binding!!.btnLogin.isEnabled = false

        _binding!!.tvEmail.addTextChangedListener{
            hideMessageUserNotExist()
            emailIsValid = userViewModel.validateEmail(it.toString())
        }

        _binding!!.tvPassword.addTextChangedListener {
            hideMessageUserNotExist()
            passwordIsValid = userViewModel.validatePassword(it.toString())
        }

        val filter =
            InputFilter { source, start, end, dest, dstart, dend ->
                for (i in start until end) {
                    if (!Character.toString(source[i]).matches("[a-zA-Z0-9]+".toRegex())) {
                        _binding!!.tvPassword.error = getString(R.string.login_tv_error_only_letters_and_digits)
                        return@InputFilter ""
                    }
                }
                null
            }

        _binding!!.tvPassword.filters = arrayOf(filter)

        _binding!!.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }
        _binding!!.tvPassword.filters = arrayOf(filter)

        setObservers()

        return binding.root
    }

    private fun hideMessageUserNotExist() {
        _binding!!.tvEmail.error = null
        _binding!!.tvPassword.error = null
    }

    private fun setObservers() {
        userViewModel.liveDataUser.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.success == true) {
                    _binding!!.prBar.visibility = View.GONE
                    findNavController().navigate(R.id.homeFragment)
                } else {
                    _binding!!.prBar.visibility = View.GONE
                    _binding!!.tvEmail.error = getString(R.string.login_et_error_user_and_password)
                    _binding!!.tvPassword.error = getString(R.string.login_et_error_user_and_password)
                }
            }
        }
        userViewModel.authException.observe(viewLifecycleOwner, this::handleException)
        userViewModel.validEmail.observe(viewLifecycleOwner) {
            if (it != null) {
                _binding!!.btnLogin.isEnabled = userViewModel.validPassword.value == true && it == true
                if (!it) {
                    _binding!!.tvEmail.error =  getString(R.string.login_et_error_email_invalid)
                }
            }
        }
        userViewModel.validPassword.observe(viewLifecycleOwner) {
            if (it != null) {
                _binding!!.btnLogin.isEnabled = userViewModel.validEmail.value == true && it == true
                if (!it) {
                    _binding!!.tvPassword.error =  getString(R.string.login_et_error_password_invalid)
                }
            }
        }
    }
    private fun handleException(exception: Throwable?) {
        if (exception is HttpException)
            when (exception.code()) {
                400 -> showDialog (getString(R.string.login_dg_bad_request))
                404 -> showDialog (getString(R.string.login_dg_resource_not_found))
                in 500..599 -> showDialog (getString(R.string.login_dg_server_error))
                else -> showDialog (getString(R.string.login_dg_unknown_error))
            }
        if (exception is IOException)
            showDialog(getString(R.string.login_dg_without_internet))
        if (exception is UnknownHostException)
            showDialog(getString(R.string.login_dg_without_internet))
    }

    private fun showDialog(message: String) {
        MaterialAlertDialogBuilder(requireContext()).setMessage(message).setPositiveButton(getString(R.string.ok)){
                dialog, which -> {}
        }.show()
    }
}