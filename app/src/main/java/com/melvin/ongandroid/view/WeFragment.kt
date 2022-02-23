package com.melvin.ongandroid.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.melvin.ongandroid.R
import com.melvin.ongandroid.model.DataSource.DataSource
import com.melvin.ongandroid.businesslogic.vo.Resource
import com.melvin.ongandroid.databinding.FragmentWeBinding
import com.melvin.ongandroid.model.We
import com.melvin.ongandroid.model.repository.RepoImpl
import com.melvin.ongandroid.view.adapters.WeAdapter
import com.melvin.ongandroid.viewmodel.VMFactory
import com.melvin.ongandroid.viewmodel.WeViewModel

class WeFragment : Fragment(), WeAdapter.OnNewClickListener {
    private var _binding: FragmentWeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<WeViewModel>() { VMFactory(RepoImpl(DataSource())) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeBinding.inflate(inflater, container, false)

        setUpRecyclerView()
        viewModel.fetchWeList()

        viewModel.listWe.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Success -> {
                    binding.prBar.visibility = View.GONE
                    binding.prError.visibility = View.GONE
                    binding.rvWe.adapter = WeAdapter(requireContext(), result.data, this)
                }
                is Resource.Failure -> {
                    binding.prBar.visibility = View.GONE
                    binding.prError.visibility = View.VISIBLE
                    errorMessage()
                }
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it == true) {
                    binding.prBar.visibility = View.VISIBLE
                    binding.prError.visibility = View.GONE
                } else {
                    binding.prBar.visibility = View.GONE
                }
            }
        }

        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.rvWe.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onNewClick(we: We) {
        val bundle = Bundle()
        bundle.putParcelable("we", we)
        findNavController().navigate(R.id.detailsWeFragment, bundle)
    }

    private fun errorMessage() {
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setMessage("Ha ocurrido un error obteniendo la información")
            alertDialog.setPositiveButton("Reintentar"){_,_->
                (activity as MainActivity).refreshWeFragment()
            }
            alertDialog.setNegativeButton("Cancelar"){_,_->
            }
            alertDialog.show()
        }
    }

