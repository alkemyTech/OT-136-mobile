package com.melvin.ongandroid.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.ItemListActivitiesBinding
import com.melvin.ongandroid.model.response.DataActivities
import com.squareup.picasso.Picasso

class ActivitiesAdapter(val context: Context?, val activitiesList: Array<DataActivities>) :
    RecyclerView.Adapter<ActivitiesAdapter.ActivitiesHolder>() {

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


    inner class ActivitiesHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemListActivitiesBinding.bind(view)

        fun render(activities: DataActivities) {

            binding.tvTitle.text = activities.name
            Glide.with(context!!).load(activities.image).centerCrop().into(binding.ivActivities)

        }
    }
}