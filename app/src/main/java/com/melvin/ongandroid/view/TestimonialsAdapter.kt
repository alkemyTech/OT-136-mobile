package com.melvin.ongandroid.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.ItemTestimonialHomeBinding
import com.melvin.ongandroid.model.TestimonialData
import com.melvin.ongandroid.model.Testimonials
import com.squareup.picasso.Picasso

class TestimonialsAdapter(private var testimonials: Testimonials)  : RecyclerView.Adapter<TestimonialsAdapter.ViewHolder>(){

    class ViewHolder (private val binding: ItemTestimonialHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(testimonial: TestimonialData){
            binding.tvDesc.text = testimonial.description
            Picasso.get().load(testimonial.image).into(binding.ivTestimonial)
        }
        fun showArrow(){
            binding.cvItem.setContentPadding(40,40,40,40)
            binding.tvDesc.isVisible = false
            binding.ivTestimonial.setImageResource(R.drawable.ic_baseline_arrow_right_24)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemTestimonialHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        if (position <= testimonials.data.lastIndex){
            holder.bind(testimonials.data[position])
        }else { holder.showArrow() }
    }

    override fun getItemCount(): Int {
        return testimonials.data.size + 1
    }

}