package com.example.movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.core.view.isVisible
import com.example.movie.activities.MainActivity
import com.example.movie.models.login_user
import com.example.movie.services.Interfaces
import com.example.movie.services.service_user
import com.google.android.material.button.MaterialButton
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.login.*
import kotlinx.android.synthetic.main.register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class register():AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        pBar1.isVisible = false
        home4.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        val reg = findViewById<MaterialButton>(R.id.register)
        reg.setOnClickListener{
            if (email.editableText.toString().trim().isEmpty()) {
                email.error = "Email Required"
                email.requestFocus()
                return@setOnClickListener
            }
            if (first_name.editableText.toString().trim().isEmpty()) {
                first_name.error = "first_name Required"
                first_name.requestFocus()
                return@setOnClickListener
            }
            if (last_name.editableText.toString().trim().isEmpty()) {
                last_name.error = "last_name Required"
                last_name.requestFocus()
                return@setOnClickListener
            }
            if (pass.editableText.toString().trim().isEmpty()) {
                pass.error = "Password Required"
                pass.requestFocus()
                return@setOnClickListener
            }
            else if (pass.editableText.toString().trim().length < 8 || pass.editableText.toString().isDigitsOnly()){
                pass.error = "Password must be 8 characters or more and alphanumeric"
                pass.requestFocus()
                return@setOnClickListener
            }

            if (confirm.editableText.toString().trim().isEmpty()) {
                confirm.error = "Confirm Password Required"
                confirm.requestFocus()
                return@setOnClickListener
            }
            else if (confirm.editableText.toString().trim().length < 8 || confirm.editableText.toString().isDigitsOnly()){
                confirm.error = "Password must be 8 characters or more and alphanumeric"
                confirm.requestFocus()
                return@setOnClickListener
            }
            else if (confirm.editableText.toString().trim() != pass.editableText.toString().trim()){
                confirm.error = "Passwords must match"
                confirm.requestFocus()
                return@setOnClickListener
            }
            pBar1.isVisible = true

            val service = service_user.createService(Interfaces::class.java)

            val response1 = service.postUser("",email.editableText.toString(),pass.editableText.toString(),
                confirm.editableText.toString(), first_name.editableText.toString(),last_name.editableText.toString())
            response1.enqueue(object : Callback<login_user> {
                override fun onResponse(
                    call: Call<login_user>,
                    response: Response<login_user>,
                ) {
                    Log.d("Response","UserID: ${response.body()}")
                    if (response.isSuccessful){
                        //val user = response.body()!!
                        //Log.d("Response", "userList size: ${user}")
                        Toast.makeText(applicationContext,"Registered successfully",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@register, com.example.movie.login::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this@register,"Registration Unsuccessfull ${response.message()}", Toast.LENGTH_LONG).show()

                    }
                }

                override fun onFailure(call: Call<login_user>, t: Throwable) {
                    Toast.makeText(this@register,"Something might went wrong $t", Toast.LENGTH_LONG).show()

                }

            })




            /* val retrofit = Retrofit.Builder()
                 .baseUrl("http://10.0.2.2:8000/rest-auth/")
                 .build()

             val service = retrofit.create(Interfaces::class.java)

             val params = HashMap<String?, String?>()
             params["username"] = ""
             params["email"] = email.editableText.toString()
             params["first_name"] = first_name.editableText.toString()
             params["last_name"] = last_name.editableText.toString()
             params["password1"] = pass.editableText.toString()
             params["password2"] = confirm.editableText.toString()

             CoroutineScope(Dispatchers.IO).launch {

                 // Do the POST request and get response
                 val response = service.postUser(params)

                 withContext(Dispatchers.Main) {
                     if (response.isSuccessful) {

                         // Convert raw JSON to pretty JSON using GSON library
                         val gson = GsonBuilder().setPrettyPrinting().create()
                         val prettyJson = gson.toJson(
                             JsonParser.parseString(
                                 response.body()
                                     ?.toString() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                             )
                         )
                         val intent = Intent(this@register, com.example.movie.login::class.java)
                         intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                         startActivity(intent)

                         Log.d("OnResponse :", response.message())

                     } else {

                         Log.e("RETROFIT_ERROR", response.code().toString())

                     }
                 }
             }*/

        }
    }
}