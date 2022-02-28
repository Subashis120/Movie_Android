package com.example.movie.helpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import com.example.movie.models.Movie_listItem

class searchAdapter(private val searchlist: List<Movie_listItem>):
    RecyclerView.Adapter<searchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): searchAdapter.ViewHolder {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.title,parent,false)
        return ViewHolder(itemView = inflate)
    }

    override fun onBindViewHolder(holder: searchAdapter.ViewHolder, position: Int) {
        return holder.bind(searchlist)
    }

    override fun getItemCount(): Int {
        //Log.d("size of string","${searchlist.size}")
        return searchlist.size
    }

    inner  class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var title = itemView.findViewById<TextView>(R.id.titl)
        fun bind(lists: List<Movie_listItem>){
                title.text = lists.toString()
        }

    }
}

