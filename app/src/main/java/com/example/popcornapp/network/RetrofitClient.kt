package com.example.popcornapp.network

//Archivo para crear la URL del API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // Colocar la base URL
    private const val BASE_URL = "https://api.themoviedb.org/"

    // Construir la URL completa
    val api: TheMovieApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheMovieApi::class.java)
    }

}