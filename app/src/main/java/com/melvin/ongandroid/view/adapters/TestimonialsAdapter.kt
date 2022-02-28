package com.melvin.ongandroid.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.melvin.ongandroid.databinding.ItemTestimonialsFragmentBinding
import com.melvin.ongandroid.model.TestimonialData
import com.melvin.ongandroid.model.Testimonials
import com.squareup.picasso.Picasso

class TestimonialsAdapter(private var testimonials: Testimonials)  : RecyclerView.Adapter<TestimonialsAdapter.ViewHolder>(){

    class ViewHolder (private val binding: ItemTestimonialsFragmentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(testimonial: TestimonialData){
            binding.tvDesc.text = testimonial.description
            binding.tvTitle.text= testimonial.name
            Picasso.get().load(testimonial.image).into(binding.ivTestimonial)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemTestimonialsFragmentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(testimonials.data[position])
    }

    override fun getItemCount(): Int {
        return testimonials.data.size
    }
}