package com.example.movie.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object service_user {
    private const val URL = "https://brock002.pythonanywhere.com/rest-auth/"

    //CREATE HTTP CLIENT
    private val loggingInterceptor = HttpLoggingInterceptor()


    private val httpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

    private val builder = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)

    private val retrofit = builder.build()

    init {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }

    fun <S> createService(
        serviceClass: Class<S>
    ): S {
        return retrofit.create(serviceClass)
    }
}