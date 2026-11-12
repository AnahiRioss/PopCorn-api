
package com.example.popcornapp

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import android.content.Intent

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var imgDetallePelicula: ImageView
    private lateinit var tvNombrePelicula: TextView
    private lateinit var tvCalificacionPelicula: TextView
    private lateinit var tvFechaPelicula: TextView
    private lateinit var tvSinopsisPelicula: TextView
    private lateinit var tvIdPelicula: TextView
    private lateinit var btnFavorito: ImageButton

    private var peliculaFavorita = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movie_detail)

        imgDetallePelicula = findViewById(R.id.imgDetallePelicula)
        tvNombrePelicula = findViewById(R.id.tvNombrePelicula)
        tvCalificacionPelicula = findViewById(R.id.tvCalificacionPelicula)
        tvFechaPelicula = findViewById(R.id.tvFechaPelicula)
        tvSinopsisPelicula = findViewById(R.id.tvSinopsisPelicula)
        tvIdPelicula = findViewById(R.id.tvIdPelicula)
        btnFavorito = findViewById(R.id.btnFavorito)

        mostrarInformacionPelicula()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Metodo para obtener toda la informacion de la pelicula
    private fun mostrarInformacionPelicula() {

        val idPelicula = intent.getIntExtra("ID_PELICULA", 0)
        val nombrePelicula = intent.getStringExtra("NOMBRE_PELICULA")
        val sinopsis = intent.getStringExtra("SINOPSIS_PELICULA")
        val fecha = intent.getStringExtra("FECHA_PELICULA")
        val calificacion = intent.getDoubleExtra("CALIFICACION_PELICULA", 0.0)
        val imagen = intent.getStringExtra("IMAGEN_PELICULA")

        // Asignar informacion a los TextView
        tvNombrePelicula.text = nombrePelicula
        tvCalificacionPelicula.text = "⭐ Calificación: $calificacion"
        tvFechaPelicula.text = "Fecha de estreno: $fecha"
        tvSinopsisPelicula.text = sinopsis
        tvIdPelicula.text = "ID: $idPelicula"

        // Cargar imagen con Glide
        Glide.with(this)
            .load(imagen)
            .centerCrop()
            .into(imgDetallePelicula)

        // Boton para agregar o eliminar de favoritos
        btnFavorito.setOnClickListener {

            peliculaFavorita = !peliculaFavorita

            if (peliculaFavorita) {
                btnFavorito.setImageResource(R.drawable.favoritos)

                Toast.makeText(
                    this,
                    "Pelicula $nombrePelicula agregada a favoritos",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this, FavoritosActivity::class.java)

                intent.putExtra("NOMBRE_PELICULA", nombrePelicula)
                intent.putExtra("FECHA_PELICULA", fecha)
                intent.putExtra("IMAGEN_PELICULA", imagen)

                startActivity(intent)

            } else {
                btnFavorito.setImageResource(R.drawable.no_favorito)

                Toast.makeText(
                    this,
                    "Pelicula $nombrePelicula eliminada de favoritos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
