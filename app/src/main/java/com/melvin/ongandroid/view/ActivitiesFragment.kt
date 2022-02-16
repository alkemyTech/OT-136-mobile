package com.melvin.ongandroid.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.FragmentActivitiesBinding


class ActivitiesFragment : Fragment() {

    private lateinit var binding: FragmentActivitiesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentActivitiesBinding.inflate(inflater, container, false)
        return binding.root
    }
   // recyclerView.layoutManager = GridLayoutManager(this,2)

}