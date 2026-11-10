package com.example.popcornapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import android.widget.ImageView
import com.bumptech.glide.Glide

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var tvDetallePelicula: TextView
    private lateinit var imgDetallePelicula: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movie_detail)
        imgDetallePelicula = findViewById(R.id.imgDetallePelicula)
        tvDetallePelicula = findViewById(R.id.tvDetallePelicula)
        tvDetallePelicula = findViewById(R.id.tvDetallePelicula)

        mostrarInformacionPelicula()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    private fun mostrarInformacionPelicula() {

        val idPelicula = intent.getIntExtra("ID_PELICULA", 0)
        val nombrePelicula = intent.getStringExtra("NOMBRE_PELICULA")
        val sinopsis = intent.getStringExtra("SINOPSIS_PELICULA")
        val fecha = intent.getStringExtra("FECHA_PELICULA")
        val calificacion = intent.getDoubleExtra("CALIFICACION_PELICULA", 0.0)
        val imagen = intent.getStringExtra("IMAGEN_PELICULA")

        // Cargar la imagen de la pelicula
        Glide.with(this)
            .load(imagen)
            .into(imgDetallePelicula)

        // Mostrar toda la informacion
        tvDetallePelicula.text = """
        ${nombrePelicula}
        
        ⭐ Calificación: $calificacion
        
        Fecha de estreno: $fecha
        
        Sinopsis:
        $sinopsis
        
        ID: $idPelicula
    """.trimIndent()//Nos permite colocar varios datos en un solo textView
    }


}