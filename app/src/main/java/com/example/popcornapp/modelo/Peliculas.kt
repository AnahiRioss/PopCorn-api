package com.example.popcornapp.modelo
//Archivo para obtener la información del API
data class Peliculas(

    val id: Int?,
    val title: String?,
    val original_title: String?,
    val original_language: String?,
    val overview: String?,
    val release_date: String?,
    val genre_ids: List<Int>?,
    val popularity: Double?,
    val vote_average: Double?,
    val vote_count: Int?,
    val poster_path: String?,
    val backdrop_path: String?,
    val adult: Boolean?,
    val video: Boolean?
) {
    //METODO PAA OBTENER LA IMAGEN EN GRANDE
    fun obtenerImagenGrande(): String {
        return if (!poster_path.isNullOrEmpty()) {
            "https://image.tmdb.org/t/p/w500$poster_path"
        } else {
            ""
        }
    }
}
