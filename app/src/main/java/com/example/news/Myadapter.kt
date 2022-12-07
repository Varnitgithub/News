package com.example.news

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView


class Myadapter(private var listner:setonitemclclick) : RecyclerView.Adapter<Myadapter.Myviewholder>() {
    private val items = ArrayList<NewsData>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myviewholder {
        val itemview =
            LayoutInflater.from(parent.context).inflate(R.layout.itemnewslayout, parent, false)
        val holder = Myviewholder(itemview)
        itemview.setOnClickListener {
            listner.onitemclicks(items[holder.adapterPosition])
        }
        return holder

    }

    override fun onBindViewHolder(holder: Myviewholder, position: Int) {

        val currentitem = items[position]
        holder.itemtitle.text = currentitem.title
        holder.itemauthor.text = currentitem.author
        holder.itemdescription.text = currentitem.description
        holder.imageurl.text = currentitem.url
        Glide.with(holder.itemView.context).load(currentitem.urlToImage).into(holder.itemimage)
        holder.publishedAt.text = currentitem.publishedAt


    }

    override fun getItemCount(): Int {
        return items.size

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateddata(updateddata: ArrayList<NewsData>) {
        items.clear()
        items.addAll(updateddata)
        notifyDataSetChanged()
    }

    class Myviewholder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var itemimage: ShapeableImageView = itemView.findViewById(R.id.image)
        var itemtitle: TextView = itemView.findViewById(R.id.title)
        var itemauthor: TextView = itemView.findViewById(R.id.author)
        var itemdescription: TextView = itemView.findViewById(R.id.description)
        var imageurl: TextView = itemView.findViewById(R.id.urltoimage)
        var publishedAt: TextView = itemView.findViewById(R.id.publishedAt)


    }

    interface setonitemclclick {
        fun onitemclicks(items: NewsData)


    }
}
