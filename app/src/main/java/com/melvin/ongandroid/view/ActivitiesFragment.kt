package com.melvin.ongandroid.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.FragmentActivitiesBinding
import com.melvin.ongandroid.model.repository.ResourceBase
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

        viewModelActivities.liveDataActivities.observe(viewLifecycleOwner) {
            when (it.status) {
                ResourceBase.Status.SUCCESS -> {
                    binding.prbarLoading.root.isVisible = false
                    if (it.data != null) {
                        initRecyclerView(it.data.dataActivities)
                    }
                }
                ResourceBase.Status.LOADING -> {
                    binding.prbarLoading.root.isVisible = true
                }
                ResourceBase.Status.ERROR -> {
                    alerDialogError()
                }
            }
        }

        return binding.root
    }

    fun initRecyclerView(activitiesList: Array<DataActivities>) {

        val adapter = ActivitiesAdapter(context,activitiesList)
        binding.rvActivities.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rvActivities.adapter = adapter
    }

    private fun alerDialogError() {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Falla Del Sistema")
        alertDialog.setMessage("Ha ocurrido un error obteniendo la información")
        alertDialog.setPositiveButton("Reintentar") { _, _ ->
            findNavController().navigate(R.id.activitiesFragment)
        }
        alertDialog.setNegativeButton("Cancelar") { _, _ ->

        }
        alertDialog.show()
    }
}