package com.example.popcornapp

import android.widget.ImageView
import android.widget.TextView
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import androidx.activity.enableEdgeToEdge
class FavoritosActivity : AppCompatActivity() {
    private lateinit var  imgFavorito: ImageView
    private lateinit var  tvNombreFavorito: TextView
    private lateinit var  tvFechaFavorito: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favoritos)

        imgFavorito = findViewById(R.id.imgFavorito)
        tvNombreFavorito = findViewById(R.id.tvNombreFavorito)
        tvFechaFavorito = findViewById(R.id.tvFechaFavorito)

        val nombre = intent.getStringExtra("NOMBRE_PELICULA")
        val fecha = intent.getStringExtra("FECHA_PELICULA")
        val imagen = intent.getStringExtra("IMAGEN_PELICULA")

        tvNombreFavorito.text = nombre
        tvFechaFavorito.text = fecha

        Glide.with(this)
            .load(imagen)
            .into(imgFavorito)

    }
}