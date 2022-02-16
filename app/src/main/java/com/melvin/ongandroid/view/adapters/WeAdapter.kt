package com.melvin.ongandroid.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.melvin.ongandroid.businesslogic.vo.BaseViewHolder
import com.melvin.ongandroid.databinding.WeRowBinding
import com.melvin.ongandroid.model.We

class WeAdapter(private val context: Context, private val weList:List<We>,
                private val itemClickListener:OnNewClickListener):
RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnNewClickListener{
        fun onNewClick(we:We)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            WeRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MainViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is MainViewHolder->holder.bind(weList[position])
        }
    }

    override fun getItemCount(): Int {
        return weList.size
    }

    inner class MainViewHolder(private val itemBinding: WeRowBinding):
        BaseViewHolder<We>(itemBinding.root) {
        override fun bind(item: We) {
            Glide.with(context).load(item.photo).centerCrop().into(itemBinding.ivPortada)
            itemBinding.tvTitulo.text=item.title
            itemBinding.tvDesc.text=item.descript
            itemView.setOnClickListener {itemClickListener.onNewClick(item)}
        }
    }
}