package com.melvin.ongandroid.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.melvin.ongandroid.R
import com.melvin.ongandroid.businesslogic.data.DataSource
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
    ): View? {
        _binding = FragmentWeBinding.inflate(inflater, container, false)

        setUpRecyclerView()

        viewModel.fetchWeList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.prBar.visibility = View.VISIBLE
                    binding.prError.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.prBar.visibility = View.GONE
                    binding.prError.visibility = View.GONE
                    binding.rvWe.adapter = WeAdapter(requireContext(), result.data, this)
                }
                is Resource.Failure -> {
                    binding.prBar.visibility = View.GONE
                    binding.prError.visibility = View.VISIBLE
                    Toast.makeText(
                        requireContext(),
                        R.string.login_dg_without_internet,
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        })

        return binding.root
    }

    private fun setUpRecyclerView() {
        val appContext = requireContext().applicationContext
        val recyclerView = binding.rvWe
        binding.rvWe.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onNewClick(we: We) {
        Toast.makeText(requireContext(), R.string.news_coming, Toast.LENGTH_LONG).show()
    }
}