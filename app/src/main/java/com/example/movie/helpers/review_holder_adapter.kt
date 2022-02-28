package com.example.movie.helpers

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import com.example.movie.models.Movie_listItem
import com.example.movie.models.Review
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.squareup.picasso.Picasso

class review_holder_adapter(private val reviews:List<Review>):
    RecyclerView.Adapter<review_holder_adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int, ): review_holder_adapter.ViewHolder {
        val inflate : View = LayoutInflater.from(parent.context).inflate(R.layout.review_holder,parent,false)
        return ViewHolder(itemView = inflate)
    }

    override fun onBindViewHolder(holder: review_holder_adapter.ViewHolder, position: Int) {
        return holder.bind(reviews[position])
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    inner  class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var uname = itemView.findViewById<MaterialTextView>(R.id.name)
        var review = itemView.findViewById<MaterialTextView>(R.id.review)
        fun bind(reviews: Review) {
           uname.text = reviews.USER.toString()
            review.text = reviews.Review

        }
    }


}