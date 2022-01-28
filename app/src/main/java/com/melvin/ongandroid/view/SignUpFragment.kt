package com.melvin.ongandroid.view

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.melvin.ongandroid.R
import com.melvin.ongandroid.businesslogic.data.DataSource
import com.melvin.ongandroid.businesslogic.domain.OnRequest

import com.melvin.ongandroid.businesslogic.domain.RepoImpl
import com.melvin.ongandroid.databinding.FragmentSignUpBinding
import com.melvin.ongandroid.model.DefaultResponse
import com.melvin.ongandroid.model.User
import com.melvin.ongandroid.viewmodel.SignUpViewModel
import com.melvin.ongandroid.viewmodel.VMFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var user: User
    private val viewModel by viewModels<SignUpViewModel>(){ VMFactory(RepoImpl(DataSource())) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)



        _binding!!.getButton.setOnClickListener {

            val email= _binding!!.etEmail.text.toString().trim()
            val password= _binding!!.etPassword.text.toString().trim()
            val name= _binding!!.etName.text.toString().trim()

            if (email.isEmpty()){
                _binding!!.etEmail.error="Email required"
                _binding!!.etEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()){
                _binding!!.etPassword.error="Email required"
                _binding!!.etPassword.requestFocus()
                return@setOnClickListener
            }

            if (name.isEmpty()){
                _binding!!.etName.error="Email required"
                _binding!!.etName.requestFocus()
                return@setOnClickListener
            }

            user=User(name, email, password)
            viewModel.postUser(user, context)



        }

        return binding.root
    }





}

