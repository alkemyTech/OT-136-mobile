package com.melvin.ongandroid.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup import android.widget.Toast
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
import com.melvin.ongandroid.model.Slides
import com.melvin.ongandroid.model.repository.RepoImpl
import com.melvin.ongandroid.viewmodel.HomeViewModel
import com.melvin.ongandroid.viewmodel.VMFactory
import com.melvin.ongandroid.model.Testimonials
import com.melvin.ongandroid.view.adapters.*


class HomeFragment : Fragment(), OnNewClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>{ VMFactory(RepoImpl(DataSource())) }
    private lateinit var testimonialsHomeAdapter: TestimonialsHomeAdapter
    private lateinit var slidesAdapter: SlidesAdapter
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseAuthListener = FirebaseAuth.AuthStateListener {
        val user = Firebase.auth.currentUser
        setName(user)
        }
    lateinit var prefHelper: PrefHelper


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
        /*prefHelper = PrefHelper(requireContext())
        val userEmail = prefHelper.getString(Constant.PREF_USERNAME)
        viewModel.fetchUser(userEmail)*/

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
        testimonialsHomeAdapter = TestimonialsHomeAdapter(value)
        binding.rvTestimonials.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvTestimonials.adapter = testimonialsHomeAdapter
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
        if(user!=null){
            val name = user.displayName
            val email = user.email
            binding.tvWelcome.text="Bienvenid@ $name"
        }/*else {
            viewModel.userName.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Resource.Success -> {
                        val name=result.data.name
                        binding.tvWelcome.text = "Bienvenid@ $name"
                    }
                    is Resource.Failure->{binding.tvWelcome.text = "Bienvenid@"}
                }
            }
        }*/
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth!!.addAuthStateListener(this.firebaseAuthListener!!)
    }
}



