package com.example.popcornapp.modelo
//Archivo para tener respuesta del API
data class TheMovieResponse(

    val page: Int,
    val results: List<Peliculas>,
    val total_pages: Int,
    val total_results: Int

)
