package com.example.movie

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movie.activities.MainActivity
import com.example.movie.helpers.movieadapter
import com.example.movie.models.Movie_listItem
import com.example.movie.services.Interfaces
import com.example.movie.services.Service
import kotlinx.android.synthetic.main.all_movies.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class categroy_movie(): AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_movies)

        pBar3.isVisible = true

        val bundle : Bundle? = intent.extras
        val cat_id = bundle!!.getInt("category_id")
        val cat_name = bundle.getString("category_name")
        Log.d("from category_movie", "movie ID: ${cat_id}")

        home1.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        heading.text = cat_name

        all_movies(cat_id)
    }


    private fun all_movies(cat_id: Int) {
        val dest = Service.buidservice(Interfaces::class.java)
        val requestCall = dest.get_cat_Movie(cat_id)

        requestCall.enqueue(object : Callback<List<Movie_listItem>> {
            @SuppressLint("LongLogTag")
            override fun onResponse(
                call: Call<List<Movie_listItem>>,
                response: Response<List<Movie_listItem>>,
            ) {
                if (response.isSuccessful){
                    pBar3.isVisible = false
                    Log.d("from-category-description","${response.body()}")
                    if (response.body()!!.isEmpty()){
                        no_movie.text = "No movies yet..."
                        layout_mov.setBackgroundColor(Color.BLACK)
                    }
                    movies.apply {
                        setHasFixedSize(true)
                        layoutManager = GridLayoutManager(this@categroy_movie,2)
                        adapter = movieadapter(response.body()!!)
                    }
                }
                else{
                    Toast.makeText(this@categroy_movie, "Something went wrong ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Movie_listItem>>, t: Throwable) {
                Toast.makeText(this@categroy_movie, "Something went wrong $t", Toast.LENGTH_SHORT).show()
            }

        })
    }
}