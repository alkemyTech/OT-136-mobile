package com.melvin.ongandroid.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.FragmentContactBinding


class ContactFragment : Fragment() {
    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!
    var emailValid = false
    var nameValid = false
    var numberValid = false
    var consultationValid = false



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactBinding.inflate(inflater, container, false)

        _binding!!.tvName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if ((binding!!.tvName.text.toString().length >= 6) &&
                    ( _binding!!.tvName.text.toString().length <=35 ))
                {
                    nameValid = true
                } else {
                    _binding!!.tvName.error = getString(R.string.contact_error_name)
                    nameValid = false
                }
                validFields()
            }
        })

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
                    _binding!!.tvEmail.error = getString(R.string.login_tv_error_invalid_mail)
                }
                validFields()
            }
        })

        _binding!!.tvPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if ((binding!!.tvPhone.text.toString().length >= 6) &&
                    ( _binding!!.tvPhone.text.toString().length <=10 ))
                 {
                    numberValid = true
                } else {
                    _binding!!.tvPhone.error = getString(R.string.contact_error_phone)
                    numberValid = false
                }
                validFields()
            }
        })

        _binding!!.tvConsultation.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {

                if (binding!!.tvConsultation.text.toString().length >= 20){
                    consultationValid = true
                }else{
                    _binding!!.tvConsultation.error = getString(R.string.contact_error_consultation)
                    consultationValid = false
                }
                validFields()
            }
        })

        return binding.root

    }

    fun validFields() {
        _binding!!.btnSend.isEnabled = emailValid && consultationValid && nameValid && numberValid
    }


}