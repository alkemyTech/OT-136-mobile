package com.melvin.ongandroid.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.melvin.ongandroid.databinding.ItemNewsFragmentBinding
import com.melvin.ongandroid.model.New
import com.squareup.picasso.Picasso

class NewsFragmentAdapter(private val newsList:List<New>): RecyclerView.Adapter<NewsFragmentAdapter.ViewHolder>() {
    class ViewHolder (private val binding: ItemNewsFragmentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(new: New) {
            binding.tvDescription.text = new.descript
            binding.tvTitle.text = new.title
            Picasso.get().load(new.photo).into(binding.ivNew)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsFragmentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}