package com.melvin.ongandroid.view

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.melvin.ongandroid.databinding.FragmentTestimonialsBinding
import com.melvin.ongandroid.model.Testimonials
import com.melvin.ongandroid.view.adapters.TestimonialsAdapter
import com.melvin.ongandroid.viewmodel.TestimonialsViewModel
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

class TestimonialsFragment : Fragment() {
    private var _binding: FragmentTestimonialsBinding? = null
    private val binding get() = _binding!!
    private val vm: TestimonialsViewModel by viewModels()
    private lateinit var adapter: TestimonialsAdapter

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
        vm.testimonials.observe(viewLifecycleOwner, {
            if (it != null) {
                initRecyclerView(vm.testimonials.value!!)
            }
        })
        vm.testimonialsException.observe(this, this::handleException)

    }

    private fun initRecyclerView(value: Testimonials) {
        adapter = TestimonialsAdapter(value)
        binding.rvTestimonials.layoutManager = LinearLayoutManager(context)
        binding.rvTestimonials.adapter = adapter
    }

    private fun handleException(exception: Throwable?) {
        if (exception is HttpException || exception is IOException || exception is UnknownHostException) {
             val alertDialog = AlertDialog.Builder(context)
                alertDialog.setMessage("Ha ocurrido un error obteniendo la información")
                alertDialog.setPositiveButton("Reintentar"){_,_->
                    vm.getTestimonials()
                }
                alertDialog.setNegativeButton("Cancelar"){_,_->

                }
                alertDialog.show()
        }
    }


}