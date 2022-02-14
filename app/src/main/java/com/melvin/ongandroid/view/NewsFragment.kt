package com.melvin.ongandroid.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.melvin.ongandroid.R
import com.melvin.ongandroid.businesslogic.data.DataSource
import com.melvin.ongandroid.businesslogic.vo.Resource
import com.melvin.ongandroid.databinding.FragmentHomeBinding
import com.melvin.ongandroid.databinding.FragmentNewsBinding
import com.melvin.ongandroid.model.New
import com.melvin.ongandroid.model.repository.RepoImpl
import com.melvin.ongandroid.viewmodel.NewsViewModel
import com.melvin.ongandroid.viewmodel.VMFactory


class NewsFragment : Fragment(), NewsAdapter.OnNewClickListener {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<NewsViewModel>(){ VMFactory(RepoImpl(DataSource())) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        setUpRecyclerView()
        viewModel.fetchNewsList.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resource.Loading->{
                    binding.prBar.visibility=View.VISIBLE
                    binding.prError.visibility=View.GONE
                }
                is Resource.Success->{
                    binding.prBar.visibility=View.GONE
                    binding.prError.visibility=View.GONE
                    binding.rvNews.adapter=NewsAdapter(requireContext(), result.data, this)
                }
                is Resource.Failure->{
                    binding.prBar.visibility=View.GONE
                    binding.prError.visibility=View.VISIBLE

                    val snack = Snackbar.make(requireView(), "Ha ocurrido un error obteniendo la información"
                        ,Snackbar.LENGTH_INDEFINITE)
                    snack.setAction("Reitentar", View.OnClickListener {

                    })
                    snack.show()
                }

            }
        })

        return binding.root
    }

    private fun setUpRecyclerView() {
        val appContext = requireContext().applicationContext
        val recyclerView = binding.rvNews
        recyclerView.layoutManager = LinearLayoutManager(appContext)
    }

    override fun onNewClick(new: New) {
        Toast.makeText(requireContext(),R.string.news_coming,Toast.LENGTH_LONG).show()
    }
}