package com.melvin.ongandroid.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.ItemListActivitiesBinding
import com.melvin.ongandroid.model.response.DataActivities
import com.squareup.picasso.Picasso

class ActivitiesAdapter(val activitiesList: Array<DataActivities>) :
    RecyclerView.Adapter<ActivitiesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivitiesHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_activities, parent, false)
        return ActivitiesHolder(view)
    }

    override fun getItemCount(): Int {
        return activitiesList.size
    }

    override fun onBindViewHolder(holder: ActivitiesHolder, position: Int) {
        holder.render(activitiesList[position])
    }
}

class ActivitiesHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemListActivitiesBinding.bind(view)

    fun render(activities: DataActivities) {
        binding.tvTitle.text = activities.name
        Picasso.get().load("https://image.tmdb.org/t/p/w500/${activities.image}")
            .into(binding.ivActivities)
    }
}