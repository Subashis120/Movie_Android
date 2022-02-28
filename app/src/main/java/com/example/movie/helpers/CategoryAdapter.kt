package com.example.movie.helpers

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import com.example.movie.categroy_movie
import com.example.movie.helpers.CategoryAdapter.*
import com.example.movie.models.CategoryItem
import com.example.movie.movie_description
import com.google.android.material.button.MaterialButton


class CategoryAdapter(private val categorylist: List<CategoryItem>):
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.button,parent,false)
        return ViewHolder(itemView = inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Log.d("Response", "categories : ${categorylist.size}")
        holder.btn.setOnClickListener {
            //Log.d("Response", "categories ID: ${categorylist[position].id}")
            val intent = Intent(holder.btn.context,categroy_movie::class.java)
            intent.putExtra("category_id",categorylist[position].id)
            intent.putExtra("category_name",categorylist[position].NAME)
            holder.btn.context.startActivity(intent)
        }

        return holder.bind(categorylist[position])
    }

    override fun getItemCount(): Int {
        return categorylist.size
    }

    inner  class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var btn = itemView.findViewById<MaterialButton>(R.id.cat)
        fun bind(category: CategoryItem){
            btn.id = category.id
            btn.text = category.NAME
        }

    }
}