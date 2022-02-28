package com.example.movie.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie.*
import com.example.movie.helpers.CategoryAdapter
import com.example.movie.helpers.searchAdapter
import com.example.movie.helpers.trendmovieadapter
import kotlinx.android.synthetic.main.activity_main.*
import com.example.movie.models.CategoryItem
import com.example.movie.models.Movie_listItem
import com.example.movie.services.Service
import com.example.movie.services.Interfaces
import com.google.android.material.button.MaterialButton
import com.smarteist.autoimageslider.SliderView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList


var getmovList = hashMapOf<String,Int>()
//val cover: ArrayList<String> = ArrayList()
class MainActivity : AppCompatActivity() {
    private val SHARED_PREFS = "shared prefs"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        pBar2.isVisible = true
        account.setBackgroundResource(R.drawable.ic_baseline_account_circle_24)
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val user_id = sharedPreferences.getInt("id_key", 0)
        Log.d("from main-activity", "user ID: ${user_id}")
        if (!user_id.equals(0)){
            account.setBackgroundResource(R.drawable.ic_baseline_power_settings_new_24)
            account.setOnClickListener {
                //startActivity(Intent(this@MainActivity, drawer::class.java))
                editor.clear()
                editor.apply()
                finish()
                overridePendingTransition(0, 0)
                startActivity(getIntent())
                overridePendingTransition(0, 0)
                Toast.makeText(applicationContext,"You are logged Out...",Toast.LENGTH_LONG).show()
                startActivity(Intent(this@MainActivity, login::class.java))
                Log.d("sharedpred_from_main","user_id: ${sharedPreferences.getInt("id_key",0)}")

            }
        }else{
            account.setBackgroundResource(R.drawable.ic_baseline_account_circle_24)
            account.setOnClickListener{
                startActivity(Intent(this,com.example.movie.login::class.java))
            }
        }

        loadCategories()
        all_movies()
        val imageSlider = findViewById<SliderView>(R.id.imageSlider)
        val drawer1 = findViewById<ImageButton>(R.id.drawer_menu)
        val account = findViewById<ImageButton>(R.id.account)
        val view = findViewById<MaterialButton>(R.id.view)
        sear.isVisible=false
        val imageList : ArrayList<String> = ArrayList()

        drawer1.setOnClickListener{
            val intent = Intent(this, drawer::class.java)
            startActivity(intent)
        }



        view.setOnClickListener{
            val intent = Intent(this, all_movie::class.java)
            startActivity(intent)
        }
       /* object : CountDownTimer(10000, 2000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d("from-actvity", "activity in progress")
            }

            override fun onFinish() {
                finish()
                overridePendingTransition(0, 0)
                startActivity(getIntent())
                overridePendingTransition(0, 0)
            }
        }.start()*/

        imageList.add("https://brock002.pythonanywhere.com/static/1.jpg")
        imageList.add("https://brock002.pythonanywhere.com/static/2.jpg")
        imageList.add("https://brock002.pythonanywhere.com/static/3.jpg")
        imageList.add("https://brock002.pythonanywhere.com/static/4.jpg")
        imageList.add("https://brock002.pythonanywhere.com/static/5.jpg")
        //Log.d("from main activity","list:$getmovList")
        search.setOnClickListener {
            startActivity(Intent(this,search_content::class.java))
            //Log.d("from main activity","list:$getmovList")

        }
        setImgInSlider(imageList,imageSlider)
    }


    private fun loadCategories() {
        //initiate the service
        val destinationCategory = Service.buidservice(Interfaces::class.java)
        val requestCall = destinationCategory.getCatList()

        //make network call asynchronously
        requestCall.enqueue(object : Callback<List<CategoryItem>>{
            override fun onResponse(call: Call<List<CategoryItem>>, response: Response<List<CategoryItem>>){
                //Log.d("Response","onResponse: ${response.body()}")
                if (response.isSuccessful){
                    val catList = response.body()!!
                    /*for (x in catList) {
                        getcatList.put(x.NAME, x.id)
                    }*/
                    //Log.d("Response", "getcatlist: $getcatList")
                    pBar2.isVisible = false
                    category.apply{
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                        adapter = CategoryAdapter(catList)
                    }
                }else{
                    Toast.makeText(this@MainActivity, "Something went wrong ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }
             override fun onFailure(call: Call<List<CategoryItem>>, t: Throwable){
                Toast.makeText(this@MainActivity,"Something might went wrong $t", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun all_movies(){
        val dest = Service.buidservice(Interfaces::class.java)
        val requestCall = dest.getMovieList()

        requestCall.enqueue(object : Callback<List<Movie_listItem>> {
            override fun onResponse(
                call: Call<List<Movie_listItem>>,
                response: Response<List<Movie_listItem>>,
            ) {
                if (response.isSuccessful){

                    movie_trend.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = trendmovieadapter(response.body()!!)
                    }

                }
                else{
                    Toast.makeText(this@MainActivity, "Something went wrong ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Movie_listItem>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Something went wrong $t", Toast.LENGTH_SHORT).show()
            }

        })
    }


    private fun setImgInSlider(images: ArrayList<String>, imageSlider: SliderView) {
        val adapter = MySlider()
        adapter.renewItems(images)
        imageSlider.setSliderAdapter(adapter)
        imageSlider.isAutoCycle = true
        imageSlider.startAutoCycle()
    }


    }

