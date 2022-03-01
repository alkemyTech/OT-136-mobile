package com.melvin.ongandroid.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.melvin.ongandroid.R
import com.melvin.ongandroid.businesslogic.vo.MainApplication
import com.melvin.ongandroid.databinding.ItemTestimonialHomeBinding
import com.melvin.ongandroid.model.TestimonialData
import com.melvin.ongandroid.model.Testimonials
import com.squareup.picasso.Picasso

class TestimonialsHomeAdapter(private var testimonials: Testimonials,
                              private val itemClickListener: OnTestClickListener)  : RecyclerView.Adapter<TestimonialsHomeAdapter.ViewHolder>(){

    inner class ViewHolder (private val binding: ItemTestimonialHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(testimonial: TestimonialData){
            binding.tvDesc.text = testimonial.description
            binding.tvTitulo.text= testimonial.name
            Picasso.get().load(testimonial.image).into(binding.ivTestimonial)
        }
        fun showArrow(){
            binding.cvItem.setContentPadding(40,40,40,40)
            binding.tvDesc.isVisible = false
            binding.tvTitulo.isVisible=false
            binding.cvItem.setCardBackgroundColor(null)
            binding.cvItem.cardElevation=0f
            binding.ivTestimonial.setImageResource(R.drawable.ic_arrow_next)
            binding.ivTestimonial.setOnClickListener {
                itemClickListener.onClickedTestArrow()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemTestimonialHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position <= testimonials.data.lastIndex){
            holder.bind(testimonials.data[position])
        } else { holder.showArrow() }
    }

    override fun getItemCount(): Int {
        return testimonials.data.size + 1
    }
}

interface OnTestClickListener{
    fun onClickedTestArrow()
}