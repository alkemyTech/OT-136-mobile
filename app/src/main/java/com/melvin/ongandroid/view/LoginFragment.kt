package com.melvin.ongandroid.view


import androidx.fragment.app.Fragment


class LoginFragment : Fragment() {

    /*private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    var emailValid = false
    var passwordValid = false
    private var listener: OnRegister? = null


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
                    _binding!!.tvEmail.error = getString(R.string.login_tv_error_invalid_mail)
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
                passwordValid = _binding!!.tvPassword.text.toString().length >= 4
                validFields()
            }
        })
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

        _binding!!.tvPassword.filters = arrayOf(filter)

        setObservers()
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity!!.finish()
            }
        })
        return binding.root
    }

    private fun hideMessageUserNotExist() {
        _binding!!.tvEmail.error = null
        _binding!!.tvPassword.error = null
    }

    fun validFields() {
        _binding!!.btnLogin.isEnabled = emailValid && passwordValid
    }

    private fun setObservers() {
        userViewModel.liveDataUser.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.success!!) {
                    (activity as MainActivity).saveSession(
                        binding.tvPassword.text.toString(),
                        binding.tvEmail.text.toString()
                    )
                    findNavController().navigate(R.id.homeFragment)
                    listener?.onClickRegister()
                } else {

                    _binding!!.prBar.visibility = View.GONE
                    _binding!!.tvEmail.error = getString(R.string.login_et_error_user_and_password)
                }
            }
        }
        userViewModel.liveDataUser.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.success == true) {
                    _binding!!.prBar.visibility = View.GONE

                } else {
                    _binding!!.prBar.visibility = View.GONE
                    _binding!!.tvEmail.error =
                        getString(R.string.login_et_error_user_and_password)
                    _binding!!.tvPassword.error =
                        getString(R.string.login_et_error_user_and_password)
                }
            }
        }
        userViewModel.authException.observe(viewLifecycleOwner, this::handleException)
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
        MaterialAlertDialogBuilder(requireContext()).setMessage(message).setPositiveButton(getString(R.string.ok)){
                dialog, which -> {}
        }.show()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnRegister) {
            listener = context
        }
    }
    override fun onDetach() {
        super.onDetach()
        listener = null
    }*/
}