package com.melvin.ongandroid.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.melvin.ongandroid.R
import com.melvin.ongandroid.model.DataSource.DataSource
import com.melvin.ongandroid.databinding.ActivitySignUpBinding
import com.melvin.ongandroid.model.DefaultResponse
import com.melvin.ongandroid.model.response.User
import com.melvin.ongandroid.model.repository.RepoImpl
import com.melvin.ongandroid.model.service.OnAPIResponse
import com.melvin.ongandroid.viewmodel.SignUpViewModel
import com.melvin.ongandroid.viewmodel.VMFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class SignUpActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var user: User
    private val viewModel by viewModels<SignUpViewModel> { VMFactory(RepoImpl(DataSource())) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {

            val email = binding.tvEmail.text.toString().trim()
            val password = binding.tvPassword.text.toString().trim()
            val name = binding.tvName.text.toString().trim()

            if (email.isEmpty()) {
                binding.tvEmail.error = getString(R.string.email_required)
                binding.tvEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.tvPassword.error = getString(R.string.password_required)
                binding.tvPassword.requestFocus()
                return@setOnClickListener
            }

            if (name.isEmpty()) {
                binding.tvName.error = getString(R.string.name_required)
                binding.tvName.requestFocus()
                return@setOnClickListener
            }

            user = User(name, email, password)
            binding.prBar.visibility = View.VISIBLE
            responseRegister(user)
        }

        viewModel.liveState.observe(this) {
            binding.btnSignUp.isEnabled = it
        }

        binding.tvName.doAfterTextChanged {
            viewModel.checkState(
                it.toString(),
                binding.tvEmail.text.toString(),
                binding.tvPassword.text.toString(),
                binding.tvConfirmPassword.text.toString()
            )
        }

        binding.tvEmail.doAfterTextChanged {
            viewModel.checkState(
                binding.tvName.text.toString(),
                it.toString(),
                binding.tvPassword.text.toString(),
                binding.tvConfirmPassword.text.toString()
            )
        }

        binding.tvPassword.doAfterTextChanged {
            viewModel.checkState(
                binding.tvName.text.toString(),
                binding.tvEmail.text.toString(),
                it.toString(),
                binding.tvConfirmPassword.text.toString()
            )
        }

        binding.tvConfirmPassword.doAfterTextChanged {
            viewModel.checkState(
                binding.tvName.text.toString(),
                binding.tvEmail.text.toString(),
                binding.tvPassword.text.toString(),
                it.toString()
            )
        }
    }

    private suspend fun callRetro(user: User) {
        viewModel.postUser(user, this, object : OnAPIResponse {
            override fun onSuccess(response: Response<DefaultResponse>) {
                binding.prBar.visibility = View.GONE
                dialogBuilder()
            }

            override fun onFailure(msg: String) {
                binding.prBar.visibility = View.GONE
            }

            override fun onLoading(response: Response<DefaultResponse>) {
                binding.prBar.visibility = View.VISIBLE
            }
        })
    }

    private fun responseRegister(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            callRetro(user)
        }
    }

    fun dialogBuilder() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.alert_title)
        builder.setMessage(R.string.alert_message)
        builder.setPositiveButton(R.string.ok) { dialog, which ->
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        builder.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}