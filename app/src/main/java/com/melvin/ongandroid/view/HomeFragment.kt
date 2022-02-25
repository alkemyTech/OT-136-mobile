package com.melvin.ongandroid.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.melvin.ongandroid.R
import com.melvin.ongandroid.businesslogic.data.PrefHelper
import com.melvin.ongandroid.model.DataSource.DataSource
import com.melvin.ongandroid.businesslogic.vo.Resource
import com.melvin.ongandroid.databinding.FragmentHomeBinding
import com.melvin.ongandroid.model.New
import com.melvin.ongandroid.model.Slides
import com.melvin.ongandroid.model.repository.RepoImpl
import com.melvin.ongandroid.viewmodel.HomeViewModel
import com.melvin.ongandroid.viewmodel.VMFactory
import com.melvin.ongandroid.model.Testimonials
import com.melvin.ongandroid.model.repository.Constant
import com.melvin.ongandroid.model.response.User
import com.melvin.ongandroid.view.adapters.NewsAdapter
import com.melvin.ongandroid.view.adapters.OnNewClickListener
import com.melvin.ongandroid.view.adapters.SlidesAdapter
import com.melvin.ongandroid.view.adapters.TestimonialsAdapter


class HomeFragment : Fragment(), OnNewClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>{ VMFactory(RepoImpl(DataSource())) }
    private lateinit var testimonialsAdapter: TestimonialsAdapter
    private lateinit var slidesAdapter: SlidesAdapter
    //val user = Firebase.auth.currentUser
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseAuthListener = FirebaseAuth.AuthStateListener {
        val user = Firebase.auth.currentUser
        setName(user)
        }


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
        firebaseAuth!!.addAuthStateListener(this.firebaseAuthListener!!)
        setUpNewsRecyclerView()
        hideSectionTestimonials(true)
        hideSectionSlides(true)
        viewModel.getSlides()
        viewModel.getTestimonials()
        setObservers()

        viewModel.getSlides()

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
                is Resource.Failure->{
                    binding.prBar.visibility=View.GONE
                    binding.prError.visibility=View.VISIBLE
                    alerDialogMasiveError()
                    binding.retryButton.setOnClickListener {
                        (activity as MainActivity).refreshFr()
                    }
                }
            }
        }

        viewModel.slides.observe(viewLifecycleOwner){
            if (it != null){
                if (it.data.isEmpty()){
                    hideSectionSlides(true)
                    binding.prBar.visibility=View.GONE
                    binding.prError.visibility=View.VISIBLE
                    binding.retryButton.setOnClickListener {
                        (activity as MainActivity).refreshFr()
                    }
                }else setupSlidesRecyclerView(viewModel.slides.value!!)
            }else hideSectionSlides(true)
        }
    }    

    private fun setUpNewsRecyclerView() {
        val appContext = requireContext().applicationContext
        val recyclerView = binding.rvNews
        recyclerView.layoutManager = LinearLayoutManager(appContext, LinearLayoutManager.HORIZONTAL, false)
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

    override fun onClickedNewsArrow() {
        findNavController().navigate(R.id.newsFragment)
    }

    private fun alerDialogMasiveError(){
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Falla Del Sistema")
        alertDialog.setMessage("Error General")
        alertDialog.setPositiveButton("Reintentar"){_,_->
            findNavController().navigate(R.id.homeFragment)
        }
        alertDialog.setNegativeButton("Cancelar"){_,_->

        }
        alertDialog.show()
    }

    private fun setName(user: FirebaseUser?){
        user?.let{
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl
            binding.tvWelcome.text="Bienvenid@ $name"
        }
    }
    override fun onStart() {
        super.onStart()
        firebaseAuth!!.addAuthStateListener(this.firebaseAuthListener!!)
    }
}



