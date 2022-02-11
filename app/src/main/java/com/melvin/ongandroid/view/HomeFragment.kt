package com.melvin.ongandroid.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.melvin.ongandroid.databinding.FragmentHomeBinding
import com.melvin.ongandroid.model.Testimonials
import com.melvin.ongandroid.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val vm: HomeViewModel by viewModels()
    private lateinit var testimonialsAdapter: TestimonialsAdapter


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
        hideSectionTestimonials(true)
        vm.getTestimonials()
        setObservers()

        return binding.root
    }

    private fun hideSectionTestimonials(hide: Boolean) {
        binding.rvTestimonials.isVisible = !hide
        binding.tvTitleTestimonials.isVisible = !hide
    }


    private fun setObservers() {
        vm.testimonials.observe(viewLifecycleOwner,{
            if (it != null){
                if (it.data.isEmpty()){
                    hideSectionTestimonials(true)
                }else setupTestimonialsRecyclerView(vm.testimonials.value!!)
            }else hideSectionTestimonials(true)
        })
    }

    private fun setupTestimonialsRecyclerView(value: Testimonials) {
        testimonialsAdapter = TestimonialsAdapter(value)
        binding.rvTestimonials.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvTestimonials.adapter = testimonialsAdapter
        hideSectionTestimonials(false)
    }
}