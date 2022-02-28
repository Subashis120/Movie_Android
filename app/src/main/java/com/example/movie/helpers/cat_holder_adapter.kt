package com.example.movie.helpers

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import com.example.movie.categroy_movie
import com.example.movie.models.CategoryItem
import com.google.android.material.button.MaterialButton

class cat_holder_adapter(private val category: List<CategoryItem>):
    RecyclerView.Adapter<cat_holder_adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.button_cat,parent,false)
        return ViewHolder(itemView = inflate)
    }

    @SuppressLint("LongLogTag")
    override fun onBindViewHolder(holder: cat_holder_adapter.ViewHolder, position: Int) {
       // Log.d("from-cat-holder", "${category[position].id}")
        holder.btn.setOnClickListener {
                //Log.d("from-cat-holder", "categories ID: ${getcatList["$category"]}")
                val intent = Intent(holder.btn.context, categroy_movie::class.java)
                intent.putExtra("category_id",category[position].id)
                intent.putExtra("category_name",category[position].NAME)
                holder.btn.context.startActivity(intent)
        }
        return holder.bind(category[position])
    }

    override fun getItemCount(): Int {
        return category.size
    }

    inner  class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var btn = itemView.findViewById<MaterialButton>(R.id.catbtn)
        fun bind(cat: CategoryItem) {
            btn.text = cat.NAME
            btn.id = cat.id

        }
    }


}