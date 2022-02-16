package com.melvin.ongandroid.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.melvin.ongandroid.databinding.ItemSlideHomeBinding
import com.melvin.ongandroid.model.Slides
import com.melvin.ongandroid.model.SlidesData
import com.squareup.picasso.Picasso

class SlidesAdapter(private var slides: Slides)  : RecyclerView.Adapter<SlidesAdapter.ViewHolder>(){

    class ViewHolder (private val binding: ItemSlideHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(slide: SlidesData){
            binding.tvDescSlide.text =slide.description
            binding.tvDescName.text = slide.name
            Picasso.get().load(slide.image).into(binding.ivSlideImg)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemSlideHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(slides.data[position])
    }

    override fun getItemCount(): Int {
        return slides.data.size
    }

}