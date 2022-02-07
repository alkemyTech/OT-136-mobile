package com.melvin.ongandroid.view


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.melvin.ongandroid.R
import com.melvin.ongandroid.businesslogic.data.DataSource
import com.melvin.ongandroid.model.repository.RepoImpl
import com.melvin.ongandroid.databinding.FragmentSignUpBinding
import com.melvin.ongandroid.model.DefaultResponse
import com.melvin.ongandroid.model.User
import com.melvin.ongandroid.viewmodel.SignUpViewModel
import com.melvin.ongandroid.viewmodel.VMFactory

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var user: User
    private val viewModel by viewModels<SignUpViewModel>() { VMFactory(RepoImpl(DataSource())) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        _binding!!.btnSignUp.setOnClickListener {

            val email = _binding!!.tvEmail.text.toString().trim()
            val password = _binding!!.tvPassword.text.toString().trim()
            val name = _binding!!.tvName.text.toString().trim()

            if (email.isEmpty()) {
                _binding!!.tvEmail.error = getString(R.string.email_required)
                _binding!!.tvEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                _binding!!.tvPassword.error = getString(R.string.password_required)
                _binding!!.tvPassword.requestFocus()
                return@setOnClickListener
            }

            if (name.isEmpty()) {
                _binding!!.tvName.error = getString(R.string.name_required)
                _binding!!.tvName.requestFocus()
                return@setOnClickListener
            }

            user = User(name, email, password)
            viewModel.postUser(user, context)
        }

        viewModel.liveState.observe(viewLifecycleOwner, {
            _binding!!.btnSignUp.isEnabled = it
        })

        _binding!!.tvName.doAfterTextChanged {
            viewModel.checkState(
                it.toString(),
                binding.tvEmail.text.toString(),
                binding.tvPassword.text.toString(),
                binding.tvConfirmPassword.text.toString()
            )
        }

        _binding!!.tvEmail.doAfterTextChanged {
            viewModel.checkState(
                binding.tvName.text.toString(),
                it.toString(),
                binding.tvPassword.text.toString(),
                binding.tvConfirmPassword.text.toString()
            )
        }

        _binding!!.tvPassword.doAfterTextChanged {
            viewModel.checkState(
                binding.tvName.text.toString(),
                binding.tvEmail.text.toString(),
                it.toString(),
                binding.tvConfirmPassword.text.toString()
            )
        }

        _binding!!.tvConfirmPassword.doAfterTextChanged {
            viewModel.checkState(
                binding.tvName.text.toString(),
                binding.tvEmail.text.toString(),
                binding.tvPassword.text.toString(),
                it.toString()
            )
        }
        return binding.root
    }
}

