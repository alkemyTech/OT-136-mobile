package com.melvin.ongandroid.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.melvin.ongandroid.databinding.FragmentTestimonialsBinding
import com.melvin.ongandroid.model.response.Testimonials
import com.melvin.ongandroid.viewmodel.TestimonialsViewModel

class TestimonialsFragment : Fragment() {
    private var _binding: FragmentTestimonialsBinding? = null
    private val binding get() = _binding!!
    private val vm: TestimonialsViewModel by viewModels()
    private lateinit var adapter: TestimonialsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestimonialsBinding.inflate(inflater, container, false)

        vm.getTestimonials()
        setObservers()

        return binding.root
    }

    private fun setObservers() {
        vm.testimonials.observe(viewLifecycleOwner,{
            if (it != null){
                initRecyclerView(vm.testimonials.value!!)
            }
        })
    }

    private fun initRecyclerView(value: Testimonials) {
        adapter = TestimonialsAdapter(value)
        binding.rvTestimonials.layoutManager = LinearLayoutManager(context)
        binding.rvTestimonials.adapter = adapter
    }

}