package com.budianto.mytourismapp.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.budianto.mytourismapp.R
import com.budianto.mytourismapp.core.domain.model.Tourism
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.items_list_tourism.view.*

class TourismAdapter : RecyclerView.Adapter<TourismAdapter.MyViewHolder>() {

    private var listTourism = ArrayList<Tourism>()

    var onItemClick: ((Tourism) -> Unit)? = null

    fun setData(newListTourism: List<Tourism>?){
        if (newListTourism == null) return
        listTourism.clear()
        listTourism.addAll(newListTourism)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.items_list_tourism, parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listTourism[position]
        holder.bindTo(data)
    }

    override fun getItemCount(): Int = listTourism.size

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindTo(tourism: Tourism){
            with(itemView){
                Glide.with(itemView.context)
                    .load(tourism.image)
                    .into(iv_item_image)

                tv_item_title.text = tourism.name
                tv_item_subtitle.text = tourism.address
            }
        }

        init {
            itemView.setOnClickListener{
                onItemClick?.invoke(listTourism[adapterPosition])
            }
        }
    }

}