package com.melvin.ongandroid.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.melvin.ongandroid.databinding.FragmentDetailsWeBinding
import com.melvin.ongandroid.model.We

class DetailsWeFragment: Fragment() {
    private var _binding: FragmentDetailsWeBinding? = null
    private val binding get() = _binding!!

    private lateinit var we: We

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireArguments().let{
            we=it.getParcelable("we")!!
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsWeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fbIntent: Intent = Uri.parse("${we.facebook}").let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage)}

        val lkinIntent: Intent = Uri.parse("${we.linkedin}").let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage)
        }

        Glide.with(this).load(we.photo).centerCrop().into(binding.imageMember)
        binding.tvWe.text=we.name
        binding.tvAboutUs.text=we.descript
        binding.facebookButton.setOnClickListener { startActivity(fbIntent)}
        binding.linkedinButton.setOnClickListener { startActivity(lkinIntent)}
    }

}