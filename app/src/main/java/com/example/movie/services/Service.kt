package com.example.movie.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Service {
    private const val URL = "https://brock002.pythonanywhere.com/api/"
    //private const val URL = "http://192.168.123.72:8000/movie_api/"

    //CREATE HTTP CLIENT
    val okhttp = OkHttpClient.Builder()

    //retrofit builder
    private val builder = Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).client(
        okhttp.build())

    //create retrofit Instance
    val retrofit = builder.build()

    //we will use this class to create an anonymous inner class function that
    //implements User List Interface

    fun <T> buidservice(serviceType : Class<T>) : T{
        return retrofit.create(serviceType)
    }
}
