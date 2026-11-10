
package com.example.popcornapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.popcornapp.modelo.Peliculas
import com.example.popcornapp.modelo.TheMovieResponse
import com.example.popcornapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var tvEstado: TextView
    private lateinit var contenedorTerror: LinearLayout
    private lateinit var contenedorRomance: LinearLayout
    private lateinit var contenedorComedia: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        tvEstado = findViewById(R.id.tvEstado)
        contenedorTerror = findViewById(R.id.contenedorTerror)
        contenedorRomance = findViewById(R.id.contenedorRomance)
        contenedorComedia = findViewById(R.id.contenedorComedia)

        tvEstado.text = "Cargando peliculas..."

        // Obtener peliculas de cada genero
        obtenerPeliculas("27", contenedorTerror)
        obtenerPeliculas("10749", contenedorRomance)
        obtenerPeliculas("35", contenedorComedia)
    }

    // Metodo para obtener las peliculas del API
    private fun obtenerPeliculas(genero: String, contenedor: LinearLayout) {

        // Token de acceso de TMDB
        val miToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxNjY2MmI1ZGFjNWY1MTM1NWQ5NjliMTBjYTg2OWY1MyIsIm5iZiI6MTc5MzExNTI4NC4zMTYsInN1YiI6IjZhZTBjNDk0YjY3ZDZhNmU0MDEyMTI0MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.f_ufGfrOPNuGImuathA3349cDNZY3HN4WICtPQOGyxk"

        RetrofitClient.api.obtenerPeliculas(
            token = miToken,
            generos = genero
        ).enqueue(object : Callback<TheMovieResponse> {

            // Metodo para verificar que la respuesta sea correcta
            override fun onResponse(call: Call<TheMovieResponse>, response: Response<TheMovieResponse>) {

                val peliculas = response.body()?.results ?: emptyList()

                //agregamos response.isSuccessfun para que se compruebe que el servidor haya respondido correctamente
                if (response.isSuccessful && peliculas.isNotEmpty()) {

                    mostrarPeliculas(peliculas, contenedor)
                    tvEstado.text = "Peliculas cargadas"

                } else {

                    Toast.makeText(
                        this@HomeActivity,
                        //response.code nos ayuda si TMDB no nos devuelve nuestra pelicula saber que error ocurrio
                        "Error al obtener peliculas: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            // Metodo para identificar si no hay respuesta
            override fun onFailure(
                call: Call<TheMovieResponse>,
                t: Throwable
            ) {

                tvEstado.text = "Error al conectar con TMDB"

                Toast.makeText(
                    this@HomeActivity,
                    "ERROR EN EL API: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    // Metodo para mostrar un listado de peliculas
    private fun mostrarPeliculas(peliculas: List<Peliculas>, contenedor: LinearLayout) {

        // Evitar resultados duplicados
        contenedor.removeAllViews()

        // Ciclo que recorre la informacion de las peliculas
        for (pelicula in peliculas) {

            // Indicar la vista en la que se va a mostrar la informacion
            val vistaPelicula = layoutInflater.inflate(
                R.layout.item_pelicula,
                contenedor,
                false
            )

            val imgPoster = vistaPelicula.findViewById<ImageView>(R.id.imgPosterPelicula)
            val tvNombrePelicula = vistaPelicula.findViewById<TextView>(R.id.tvNombrePelicula)
            val tvCalificacion = vistaPelicula.findViewById<TextView>(R.id.tvCalificacion)

            // Asignar informacion de la pelicula
            tvNombrePelicula.text = pelicula.title ?: "Pelicula sin titulo"
            tvCalificacion.text = "⭐ ${pelicula.vote_average ?: 0.0}"

            // Cargar imagen con Glide
            Glide.with(this)
                .load(pelicula.obtenerImagenGrande())
                .centerCrop()
                .into(imgPoster)

            // Evento para seleccionar una pelicula
            vistaPelicula.setOnClickListener {

                val idPelicula = pelicula.id

                if (idPelicula != null) {

                    val intent = Intent(this@HomeActivity, MovieDetailActivity::class.java)

                    intent.putExtra("ID_PELICULA", idPelicula)
                    intent.putExtra("NOMBRE_PELICULA", pelicula.title)
                    intent.putExtra("SINOPSIS_PELICULA", pelicula.overview)
                    intent.putExtra("FECHA_PELICULA", pelicula.release_date)
                    intent.putExtra("CALIFICACION_PELICULA", pelicula.vote_average)
                    intent.putExtra("IMAGEN_PELICULA", pelicula.obtenerImagenGrande())

                    startActivity(intent)
                }
            }

            // Mostrar toda la informacion
            contenedor.addView(vistaPelicula)
        }
    }
}
