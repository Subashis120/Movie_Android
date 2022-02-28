package com.example.movie

import android.content.Intent
import android.os.Bundle
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
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.movie.*
import kotlinx.android.synthetic.main.search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class all_movie():AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_movies)
        pBar3.isVisible = true

        home1.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
        all_movies()
    }

    private fun all_movies(){
        val dest = Service.buidservice(Interfaces::class.java)
        val requestCall = dest.getMovieList()

        requestCall.enqueue(object : Callback<List<Movie_listItem>>{
            override fun onResponse(
                call: Call<List<Movie_listItem>>,
                response: Response<List<Movie_listItem>>,
            ) {
                if (response.isSuccessful){
                    pBar3.isVisible = false
                    movies.apply {
                        setHasFixedSize(true)
                        layoutManager = GridLayoutManager(this@all_movie,2)
                        adapter = movieadapter(response.body()!!)
                    }
                }
                else{
                    Toast.makeText(this@all_movie, "Something went wrong ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Movie_listItem>>, t: Throwable) {
                Toast.makeText(this@all_movie, "Something went wrong $t", Toast.LENGTH_SHORT).show()
            }

        })
    }
}