package com.example.movie.helpers

import android.content.Intent
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
import com.example.movie.activities.getmovList
import com.example.movie.models.Movie_listItem
import com.example.movie.movie_description
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso

class movieadapter(private val movielist: List<Movie_listItem>):
        RecyclerView.Adapter<movieadapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.movie,parent,false)
            return ViewHolder(itemView = inflate)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
           // getmovList.put(movielist[position].NAME, movielist[position].id)
           // Log.d("Response", "movies : ${movielist.size}")
            holder.itemView.setOnClickListener {
                //Log.d("movies", "ID: ${movielist[position].id }")
                val intent = Intent(holder.itemView.context,movie_description::class.java)
                intent.putExtra("movie_id",movielist[position].id)
                intent.putExtra("movie_name",movielist[position].NAME)
                holder.itemView.context.startActivity(intent)
            }

            return holder.bind(movielist[position])
        }

        override fun getItemCount(): Int {
            return movielist.size
        }

        inner  class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            var holder = itemView.findViewById<MaterialCardView>(R.id.holder)
            var img = itemView.findViewById<ImageView>(R.id.img)
            var title = itemView.findViewById<TextView>(R.id.title)
            var ratingBar = itemView.findViewById<RatingBar>(R.id.ratingBar)
            var no_rat =itemView.findViewById<TextView>(R.id.no_rat)
            fun bind(movies: Movie_listItem) {
                holder.id = movies.id
                Picasso.get().load(movies.COVER).into(img)
                title.text = movies.NAME
                if (movies.rating.toInt().equals(0)){
                    no_rat.text = "no ratings yet"
                    ratingBar.isVisible = false
                    //Log.d("rating","${no_rat.text}")
                }
                else{
                    no_rat.isVisible = false
                    ratingBar.rating = movies.rating.toFloat()
                }

            }
        }
    }