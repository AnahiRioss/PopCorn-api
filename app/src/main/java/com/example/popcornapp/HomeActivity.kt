package com.example.popcornapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

// Importamos tus nuevas clases de modelo y red
import com.example.popcornapp.modelo.Peliculas
import com.example.popcornapp.modelo.TheMovieResponse
import com.example.popcornapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var tvResultado: TextView
    private lateinit var imgPelicula: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        tvResultado = findViewById(R.id.tvResultado)
        // Nota: Asegúrate de que el ID en tu XML siga siendo imgAlbum, o cámbialo a imgPelicula
        imgPelicula = findViewById(R.id.imgPelicula)

        obtenerPelicula()
    }

    private fun obtenerPelicula() {
        // Debes colocar aquí el token o clave que te dio TMDB
        val miToken = "Bearer AQUI_PONES_TU_TOKEN_DE_TMDB"

        // Llamamos al método que definimos en TheMovieApi.kt
        RetrofitClient.api.obtenerPeliculas(
            token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxZjlmMDJiODg2ZmU0NGQwZGRjODhlNjU0OTYyZGNjNyIsIm5iZiI6MTc5MzExNDAzMi43MDcsInN1YiI6IjZhZTBiZmIwMTIwYmY1N2JmZWUwNjg5MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.vAz7Hd1TnsDpHAc6LbpWr6UJhBE-eU7FoDy5AX5X0y8",
            generos = "28" // El ID 28 corresponde al género de Acción en TMDB
        ).enqueue(object : Callback<TheMovieResponse> {

            // MÉTODO PARA VERIFICAR QUE LA RESPUESTA SEA CORRECTA
            override fun onResponse(call: Call<TheMovieResponse?>, response: Response<TheMovieResponse?>) {

                // Variable que obtiene los resultados (ahora es una lista de películas)
                val peliculas = response.body()?.results

                if (!peliculas.isNullOrEmpty()) {

                    // Guardamos la información de la primera película
                    val pelicula = peliculas[0]

                    // Mostrar información en el TextView
                    tvResultado.text = "Título de la película: ${pelicula.title} \n\n" +
                            "Fecha de estreno: ${pelicula.release_date} \n" +
                            "Calificación: ${pelicula.vote_average}"

                    // Mostrar imagen (póster) usando Glide
                    Glide.with(this@HomeActivity)
                        .load(pelicula.obtenerImagenGrande())
                        .into(imgPelicula)

                } else {
                    Toast.makeText(this@HomeActivity, "No hay resultados", Toast.LENGTH_SHORT).show()
                }
            }

            // MÉTODO PARA IDENTIFICAR SI NO HAY RESPUESTA
            override fun onFailure(call: Call<TheMovieResponse?>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "ERROR EN EL API", Toast.LENGTH_SHORT).show()
            }
        })
    }
}