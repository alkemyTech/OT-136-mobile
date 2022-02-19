package com.melvin.ongandroid.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.melvin.ongandroid.businesslogic.vo.Resource
import com.melvin.ongandroid.databinding.FragmentActivitiesBinding
import com.melvin.ongandroid.model.response.DataActivities
import com.melvin.ongandroid.view.adapters.ActivitiesAdapter
import com.melvin.ongandroid.viewmodel.ActivitiesViewModel



class ActivitiesFragment : Fragment() {

    private val viewModelActivities by viewModels<ActivitiesViewModel>()
    private lateinit var binding: FragmentActivitiesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActivitiesBinding.inflate(inflater, container, false)

        viewModelActivities.getActivities()

        viewModelActivities.liveDataActivities.observe(viewLifecycleOwner){ result ->
            when (result) {
                is Resource.Loading<*> -> {
                    binding.prbarLoading.visibility = View.VISIBLE
                }
            }
        }

        return binding.root
    }
    // recyclerView.layoutManager = GridLayoutManager(this,2)

    fun initRecyclerView(activitiesList: Array<DataActivities>) {
        val adapter = ActivitiesAdapter(activitiesList)
        binding.rvActivities.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvActivities.adapter = adapter
    }
}