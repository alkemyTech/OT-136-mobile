package com.melvin.ongandroid.view

import androidx.fragment.app.Fragment

class SignUpFragment : Fragment() {
    /*private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var user: User
    private val viewModel by viewModels<SignUpViewModel>() { VMFactory(RepoImpl(DataSource())) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
            binding!!.prBar.visibility = View.VISIBLE
            responseRegistrer(user)
        }

        viewModel.liveState.observe(viewLifecycleOwner) {
            _binding!!.btnSignUp.isEnabled = it
        }

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

    suspend fun callRetro(user: User) {
        viewModel.postUser(user, context, object : OnAPIResponse {
            override fun onSuccess(response: Response<DefaultResponse>) {
                _binding!!.prBar.visibility = View.GONE
                dialogBuilder()
            }

            override fun onFailure(msg: String) {
                _binding!!.prBar.visibility = View.GONE
            }

            override fun onLoading(response: Response<DefaultResponse>) {
            _binding!!.prBar.visibility = View.VISIBLE
            }

        })
    }

    private fun responseRegistrer(user:User) {
        CoroutineScope(Dispatchers.IO).launch {
            callRetro(user)
        }
    }

    fun dialogBuilder() {

        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.alert_title)
        builder.setMessage(R.string.alert_message)
        builder.setPositiveButton(R.string.ok) { dialog, which ->
            findNavController().navigate(R.id.loginFragment)
        }
        builder.show()
    }*/
}