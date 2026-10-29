package com.example.popcornapp.network
//Archivo para generar la respuesta del API
import com.example.popcornapp.modelo.TheMovieResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TheMovieApi {

    @GET("3/discover/movie")

    //METODO PARA BUSCAR LAS PELICULAS CON FILTROS

    fun obtenerPeliculas(
        @Header("Authorization") token: String,
        @Query("language") language: String = "es-MX",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("with_genres") generos: String,
        @Query("vote_average.gte") puntajeMinimo: Int? = null
    ): retrofit2.Call<TheMovieResponse>

}