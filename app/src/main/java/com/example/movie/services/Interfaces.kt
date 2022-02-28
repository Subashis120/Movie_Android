package com.example.movie.services

import com.example.movie.models.*
import retrofit2.*
import retrofit2.http.*

interface Interfaces {
     @GET("categories")
     fun getCatList() : Call<List<CategoryItem>>

     @GET("movies")
     fun getMovieList() : Call<List<Movie_listItem>>

     @GET("movies/{id}")
     fun getMovie(@Path("id") id:Int) : Call<Movie_listItem>

     @FormUrlEncoded
     @POST("registration/")
     fun postUser(
          @Field("username") username:String,
          @Field("email") email:String,
          @Field("password1") password1:String,
          @Field("password2") password2:String,
          @Field("first_name") first_name:String,
          @Field("last_name") last_name:String,) : Call<login_user>

     @FormUrlEncoded
     @POST("login/")
     fun getUser(@Field("username") username:String,
     @Field("email") email:String,
     @Field("password") password:String,)
     : Call<login_user>

     @FormUrlEncoded
     @POST("reviews/")
     fun sendReview(
          @Field("Review") Review:String,
          @Field("MOVIE") MOVIE:Int,
          @Field("USER") USER:Int
     ):Call<Review>

     @GET("categories/{id}/movies")
     fun get_cat_Movie(@Path("id") id:Int) : Call<List<Movie_listItem>>

     @FormUrlEncoded
     @POST("ratings/")
     fun sendRating(
          @Field("Count") Count: Int,
          @Field("MOVIE") MOVIE:Int,
          @Field("USER") USER:Int
     ):Call<Rating>

}