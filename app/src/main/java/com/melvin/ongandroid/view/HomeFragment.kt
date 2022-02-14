package com.melvin.ongandroid.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.melvin.ongandroid.R
import com.melvin.ongandroid.businesslogic.data.DataSource
import com.melvin.ongandroid.businesslogic.vo.Resource
import com.melvin.ongandroid.databinding.FragmentHomeBinding
import com.melvin.ongandroid.model.New
import com.melvin.ongandroid.model.Slides
import com.melvin.ongandroid.model.repository.RepoImpl
import com.melvin.ongandroid.viewmodel.HomeViewModel
import com.melvin.ongandroid.viewmodel.VMFactory
import com.melvin.ongandroid.model.Testimonials


class HomeFragment : Fragment(),NewsAdapter.OnNewClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>{ VMFactory(RepoImpl(DataSource())) }

    private lateinit var testimonialsAdapter: TestimonialsAdapter

    private lateinit var slidesAdapter:SlidesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity!!.finish()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setUpNewsRecyclerView()


        hideSectionTestimonials(true)
        hideSectionSlides(true)
        viewModel.getSlides()
        viewModel.getTestimonials()
        setObservers()

        return binding.root
    }

    private fun hideSectionTestimonials(hide: Boolean) {
        binding.rvTestimonials.isVisible = !hide
        binding.tvTitleTestimonials.isVisible = !hide
    }

    private fun setObservers() {
        viewModel.testimonials.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.data.isEmpty()) {
                    hideSectionTestimonials(true)
                } else setupTestimonialsRecyclerView(viewModel.testimonials.value!!)
            } else hideSectionTestimonials(true)
        }
        viewModel.fetchNewsList.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.prBar.visibility = View.VISIBLE
                    binding.prError.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.prBar.visibility = View.GONE
                    binding.prError.visibility = View.GONE
                    binding.rvNews.adapter = NewsAdapter(requireContext(), result.data, this)
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
        }

        viewModel.slides.observe(viewLifecycleOwner){
            if (it != null){
                if (it.data.isEmpty()){
                    hideSectionSlides(true)
                }else setupSlidesRecyclerView(viewModel.slides.value!!)
            }else hideSectionSlides(true)
        }
    }    

    private fun setUpNewsRecyclerView() {

        val appContext = requireContext().applicationContext
        val recyclerView = binding.rvNews
        recyclerView.layoutManager = LinearLayoutManager(appContext, LinearLayoutManager.HORIZONTAL, false)

    }

    override fun onNewClick(new: New) {
        Toast.makeText(requireContext(),R.string.news_coming,Toast.LENGTH_LONG).show()
    }

    private fun setupTestimonialsRecyclerView(value: Testimonials) {
        testimonialsAdapter = TestimonialsAdapter(value)
        binding.rvTestimonials.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvTestimonials.adapter = testimonialsAdapter
        hideSectionTestimonials(false)
    }

    private fun setupSlidesRecyclerView(value: Slides) {
        slidesAdapter = SlidesAdapter(value)
        binding.rvSlides.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvSlides.adapter = slidesAdapter
        hideSectionSlides(false)

    }

    private fun hideSectionSlides(hide: Boolean) {
        binding.rvSlides.isVisible = !hide
        binding.rvSlides.isVisible = !hide
    }
}

