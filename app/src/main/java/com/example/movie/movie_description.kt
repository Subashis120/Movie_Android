package com.example.movie

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie.activities.MainActivity
import com.example.movie.helpers.cat_holder_adapter
import com.example.movie.helpers.review_holder_adapter
import com.example.movie.login
import com.example.movie.models.Movie_listItem
import com.example.movie.models.Rating
import com.example.movie.models.Review
import com.example.movie.services.Interfaces
import com.example.movie.services.Service
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.all_movies.*
import kotlinx.android.synthetic.main.button_cat.view.*
import kotlinx.android.synthetic.main.description.*
import kotlinx.android.synthetic.main.description.view.*
import kotlinx.android.synthetic.main.login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.RoundingMode
import java.text.DecimalFormat


class movie_description(): AppCompatActivity() {

    private val SHARED_PREFS = "shared prefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.description)
        pBar4.isVisible = true
        home2.setOnClickListener {
            Log.d("from desc","clicked")
            startActivity(Intent(this, MainActivity::class.java))
        }

        send.isGone = true
        //urat.isGone = true
        rating_view.isGone = true
        //(urat.getParent() as ViewGroup).removeView(urat)


        val bundle: Bundle? = intent.extras
        var movie_id = bundle!!.getInt("movie_id")
        val movie_id_cat = bundle!!.getInt("cat_id")
        val movie_name = bundle.getString("movie_name")

        //Log.d("from description", "cat name: ${getcatList["Hollywood"]}")



        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val user_id = sharedPreferences.getInt("id_key", 0)
        Log.d("from description", "user ID: ${user_id}")

        rat.setOnClickListener {

            if (user_id.equals(0)) {
                Toast.makeText(this@movie_description, "please login to rate", Toast.LENGTH_SHORT)
                    .show()
                Log.d("from-description", "please login")
                startActivity(Intent(this,com.example.movie.login::class.java))
            }

            urat.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                Toast.makeText(applicationContext, "rating is: ${rating}", Toast.LENGTH_LONG)
                    .show()
            }

            movie_nam.text = movie_name


            rating_view.isVisible = true
            cancel.setOnClickListener {
                rating_view.isGone = true
            }

            done.setOnClickListener {
                val user_rating = urat.rating.toInt()
                if (!user_rating.equals(0.0)) {
                    val service = Service.buidservice(Interfaces::class.java)
                    val response = service.sendRating(user_rating, movie_id, user_id)

                    response.enqueue(object : Callback<Rating> {
                        override fun onResponse(
                            call: Call<Rating>,
                            response: Response<Rating>,
                        ) {
                            if (response.isSuccessful) {
                                finish()
                                overridePendingTransition(0, 0)
                                startActivity(getIntent())
                                overridePendingTransition(0, 0)
                                Toast.makeText(this@movie_description,
                                    "rating submitted: ${urat.rating}",
                                    Toast.LENGTH_LONG).show()
                                Log.d("Rating", "Response is : ${response.body()}")
                            }
                        }

                        override fun onFailure(call: Call<Rating>, t: Throwable) {
                            Log.d("Rating", "Response is : $t")
                        }

                    })
                    rating_view.isGone = true

                }
            }
        }

            val service = Service.buidservice(Interfaces::class.java)
            val response = service.getMovie(movie_id)
            var rat1 = ""

            response.enqueue(object : Callback<Movie_listItem> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(
                    call: Call<Movie_listItem>,
                    response: Response<Movie_listItem>,
                ) {
                    if (response.isSuccessful) {
                        pBar4.isVisible = false
                        Log.d("Response", "Movie is: ${response.body()}")
                        desc.apply {
                            Picasso.get().load(response.body()?.COVER).into(cover)
                            title.text = response.body()?.NAME
                            if (response.body()!!.rating.toInt().equals(0)) {
                                no_rat.text = "no ratings yet"
                                rating.isVisible = false
                                ratingBar.isVisible = false
                                Log.d("rating", "${no_rat.text}")
                            } else {
                                no_rat.isVisible = false
                                rating.text = response.body()?.rating.toString()
                                val num = response.body()!!.rating.toFloat()
                                val df = DecimalFormat("#.#")
                                df.roundingMode = RoundingMode.CEILING
                                //Log.d("rating from rounding","without float rating is: ${df.format(num)}")
                                //Log.d("rating from rounding","with float rating is: ${df.format(num).toFloat()}")
                                rat1 = df.format(num)
                                ratingBar.rating = rat1.toFloat()
                            }
                            rating.text = rat1
                            //ratingBar.rating = rat.
                            cast.text = response.body()?.CAST
                            description.text = response.body()?.DESCRIPTION
                            Log.d("review", "${response.body()?.reviews?.size}")
                            reviews.apply {
                                setHasFixedSize(true)
                                layoutManager = LinearLayoutManager(this@movie_description,
                                    LinearLayoutManager.VERTICAL,
                                    false)
                                adapter = review_holder_adapter(response.body()!!.reviews)
                            }
                            cats.apply {
                                setHasFixedSize(true)
                                layoutManager = LinearLayoutManager(this@movie_description,
                                    LinearLayoutManager.HORIZONTAL,
                                    false)
                                adapter = cat_holder_adapter(response.body()!!.CATEGORIES)

                            }

                        }
                    } else {
                        Log.d("Response", "Movie is: no movie found${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Movie_listItem>, t: Throwable) {
                    Log.d("movie_description", "error: $t")
                }

            })


            /* val thumbs_up = findViewById<ShapeableImageView>(R.id.thumbs_up)
        val thumbs_down = findViewById<ShapeableImageView>(R.id.thumbs_down)
        var thumbs_change: Int = 1

        var thumbs_up_count = findViewById<MaterialTextView>(R.id.thumbs_up_count)
        var thumbs_down_count = findViewById<MaterialTextView>(R.id.thumbs_down_count)

        thumbs_up.setOnClickListener {
            if (thumbs_change == 1) {
                thumbs_up.setImageResource(R.drawable.ic_baseline_thumb_up_blue_24)
                thumbs_change = 2
            } else {
                thumbs_up.setImageResource(R.drawable.ic_baseline_thumb_up_24)
                thumbs_change = 1
            }
        }

        thumbs_down.setOnClickListener {
            if (thumbs_change == 1) {
                thumbs_down.setImageResource(R.drawable.ic_baseline_thumb_down_blue_24)
                thumbs_change =2
            } else {
                thumbs_down.setImageResource(R.drawable.ic_baseline_thumb_down_24)
                thumbs_change =1
            }
        }*/
            user_rev.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                    //Log.d("review-text","not changed")
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    send.isVisible = true
                }

                override fun afterTextChanged(s: Editable?) {
                    val length = user_rev.length()
                    if (length.equals(0)){
                        send.isVisible = false
                    }
                }

            })


            send.setOnClickListener {
                if (user_id.equals(0)) {
                    Toast.makeText(this@movie_description,
                        "please login to write a review",
                        Toast.LENGTH_SHORT).show()
                    Log.d("from-description", "please login")
                    startActivity(Intent(this@movie_description, login::class.java))
                }
                //Toast.makeText(this, "send button clicked", Toast.LENGTH_LONG).show()
                val review_response =
                    service.sendReview(user_rev.editableText.toString(), movie_id, user_id)

                review_response.enqueue(object : Callback<Review> {
                    override fun onResponse(call: Call<Review>, response: Response<Review>) {
                        if (response.isSuccessful) {
                            Log.d("review", "id: ${response.body()?.id}")
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                            Toast.makeText(this@movie_description,
                                "review sent",
                                Toast.LENGTH_SHORT).show()
                        }

                    }

                    override fun onFailure(call: Call<Review>, t: Throwable) {
                        Log.d("review-error", "code:$t")
                    }

                })
            }


        }
    }

