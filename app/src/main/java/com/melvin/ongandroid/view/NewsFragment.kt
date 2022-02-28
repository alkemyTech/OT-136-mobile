package com.melvin.ongandroid.view

import android.app.AlertDialog
import android.os.Bundle
import android.util.AttributeSet
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.melvin.ongandroid.R
import com.melvin.ongandroid.businesslogic.vo.Resource
import com.melvin.ongandroid.databinding.FragmentHomeBinding
import com.melvin.ongandroid.databinding.FragmentNewsBinding
import com.melvin.ongandroid.model.New
import com.melvin.ongandroid.model.repository.RepoImpl
import com.melvin.ongandroid.view.adapters.NewsAdapter
import com.melvin.ongandroid.view.adapters.NewsFragmentAdapter
import com.melvin.ongandroid.view.adapters.SlidesAdapter
import com.melvin.ongandroid.view.adapters.TestimonialsAdapter
import com.melvin.ongandroid.viewmodel.HomeViewModel
import com.melvin.ongandroid.viewmodel.NewsViewModel
import com.melvin.ongandroid.viewmodel.VMFactory
import kotlin.coroutines.EmptyCoroutineContext


class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<NewsViewModel>()
    private lateinit var adapter: NewsFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.fetchNewsList.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.prBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    if (result.data.isEmpty()){
                        binding.prBar.visibility = View.GONE
                        alerDialogEmpty()
                    }else{
                        binding.prBar.visibility = View.GONE
                        initRecyclerView(result.data)
                    }
                }
                is Resource.Failure -> {
                    binding.prBar.visibility = View.GONE
                    alerDialogError()
                }
            }
        }
    }

    private fun initRecyclerView(items: List<New>) {
        adapter = NewsFragmentAdapter(items)
        binding.rvNews.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        binding.rvNews.adapter = adapter
    }

    private fun showDialog(message: String) {
        MaterialAlertDialogBuilder(requireContext()).setMessage(message)
            .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                {}
            }.show()
    }

    private fun alerDialogError() {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle(R.string.error_system_dialog)
        alertDialog.setMessage(R.string.message_dialog_error)
        alertDialog.setPositiveButton(R.string.reintent_dialog) { _, _ ->
            findNavController().navigate(R.id.newsFragment)
        }
        alertDialog.setNegativeButton(R.string.cancel_dialog) { _, _ ->

        }
        alertDialog.show()
    }

    private fun alerDialogEmpty() {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle(R.string.error_system_dialog)
        alertDialog.setMessage(R.string.message_dialog_empty)
        alertDialog.setPositiveButton(R.string.reintent_dialog) { _, _ ->
            findNavController().navigate(R.id.newsFragment)
        }
        alertDialog.setNegativeButton(R.string.cancel_dialog) { _, _ ->

        }
        alertDialog.show()
    }

}