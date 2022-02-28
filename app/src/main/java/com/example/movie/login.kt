package com.example.movie

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Color.*
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.movie.activities.MainActivity
import com.example.movie.models.login_user
import com.example.movie.services.Interfaces
import com.example.movie.services.*
import kotlinx.android.synthetic.main.login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class login:AppCompatActivity() {

    private val SHARED_PREFS = "shared prefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        pBar.isVisible = false
        home.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        login.setOnClickListener {


            if (username.editableText.toString().trim().isEmpty()) {
                username.error = "Email Required"
                username.requestFocus()
                return@setOnClickListener
            }
            if (password.editableText.toString().trim().isEmpty()) {
                password.error = "Password required"
                password.requestFocus()
                return@setOnClickListener
            }
            pBar.isVisible = true
            //login.isClickable = false
            //username.isClickable = false
            //password.isClickable = false

            val sharedPreferences: SharedPreferences = this.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
            val editor:SharedPreferences.Editor = sharedPreferences.edit()
            //Log.d(username.editableText.toString(),password.editableText.toString())
            //val destination =service_user.buidservice(Interfaces::class.java)
            //val requestCall = destination.getUser(username.editableText.toString(),username.editableText.toString(), password.editableText.toString())

            val service = service_user.createService(Interfaces::class.java)

            val response = service.getUser(username.editableText.toString(),username.editableText.toString(),password.editableText.toString())
            response.enqueue(object : Callback<login_user> {
                @SuppressLint("CommitPrefEdits")
                override fun onResponse(
                    call: Call<login_user>,
                    response: Response<login_user>,
                    ) {
                        Log.d("Response","UserID: ${response.body()?.user?.id}")

                    if (response.body()?.user?.id != null) {
                       // val name = response.body()?.user?.email!!.split("@")[0]
                        val name = response.body()?.user?.first_name
                        val email = response.body()?.user?.email
                        val user_id = response.body()?.user?.id


                        if (user_id != null) {
                            editor.putInt("id_key", user_id)
                        }
                        editor.putString("name_key", name)
                        editor.putString("email_key", email)
                        editor.apply()
                        Toast.makeText(applicationContext,"Logged in successfully",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@login, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(applicationContext,"No user found..please enter again", Toast.LENGTH_LONG).show()
                        finish()
                        overridePendingTransition(0, 0)
                        startActivity(getIntent())
                        overridePendingTransition(0, 0)

                    }

                    Log.d("sharedpred","user_id: ${sharedPreferences.getInt("id_key",0)}")


                    }

                    override fun onFailure(call: Call<login_user>, t: Throwable) {
                        Toast.makeText(this@login,"Something might went wrong $t", Toast.LENGTH_LONG).show()

                    }

                })

            }

            register1.setOnClickListener {
                val intent = Intent(this@login, register::class.java)
                startActivity(intent)
            }
        }
    }


