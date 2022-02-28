package com.example.movie

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.movie.activities.MainActivity
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer.*

class drawer: AppCompatActivity() {

    private val SHARED_PREFS = "shared prefs"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        Log.d("sharedpred_from_drawer","user_id: ${sharedPreferences.getInt("id_key",0)}")
        val name = sharedPreferences.getString("name_key","User")
        //val email = sharedPreferences.getString("email_key","xyz@gmail.com")

        val prefs: SharedPreferences.Editor = sharedPreferences.edit()

        if (name != null){
            welcome.text = "Welcome $name"
        }

        val home1 = findViewById<MaterialButton>(R.id.home)
        home1.setOnClickListener{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
        }
        /*logout.setOnClickListener {
            prefs.clear()
            prefs.apply()
            finish()
            overridePendingTransition(0, 0)
            startActivity(getIntent())
            overridePendingTransition(0, 0)
            Toast.makeText(applicationContext,"You are logged Out...",Toast.LENGTH_LONG).show()
            startActivity(Intent(this@drawer, login::class.java))
            Log.d("sharedpred_from_main","user_id: ${sharedPreferences.getInt("id_key",0)}")
        }*/
    }
}