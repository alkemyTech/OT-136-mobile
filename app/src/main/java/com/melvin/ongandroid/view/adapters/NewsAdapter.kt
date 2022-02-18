package com.melvin.ongandroid.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.melvin.ongandroid.R
import com.melvin.ongandroid.businesslogic.vo.BaseViewHolder
import com.melvin.ongandroid.databinding.NewsRowBinding
import com.melvin.ongandroid.model.New

class NewsAdapter(private val context: Context,
                  private val newsList:List<New>,
                  private val itemClickListener: OnNewClickListener)
    : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private val limit = 5

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            NewsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MainViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is MainViewHolder ->{
                when(position){
                    limit-1-> holder.showArrow()
                    else-> holder.bind(newsList[position])
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return if(newsList.size>=limit){
            limit
        }else newsList.size
    }

    inner class MainViewHolder(private val itemBinding: NewsRowBinding):
        BaseViewHolder<New>(itemBinding.root) {
        override fun bind(item: New) {
            Glide.with(context).load(item.photo).centerCrop().into(itemBinding.ivPortada)
            itemBinding.tvTitulo.text=item.title
            itemBinding.tvDesc.text=item.descript
        }

        fun showArrow(){
            itemBinding.cvItem.setContentPadding(40,40,40,40)
            itemBinding.tvDesc.isVisible = false
            itemBinding.tvTitulo.isVisible=false
            itemBinding.cvItem.setCardBackgroundColor(null)
            itemBinding.cvItem.cardElevation=0f
            itemBinding.ivPortada.setImageResource(R.drawable.ic_arrow_next)
            itemBinding.cvItem.setOnClickListener {
                itemClickListener.onClickedNewsArrow()
            }
        }
    }
}
interface OnNewClickListener{
    fun onClickedNewsArrow()
}