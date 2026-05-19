package com.example.kataloggending

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.lagubali.R

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imgDetailLagu = findViewById<ImageView>(R.id.imgDetailLagu)
        val tvDetailJudul = findViewById<TextView>(R.id.tvDetailJudul)
        val tvDetailDeskripsi = findViewById<TextView>(R.id.tvDetailDeskripsi)
        val btnBack = findViewById<Button>(R.id.btnBack)

        // Menerima data dari Intent
        val judul = intent.getStringExtra("EXTRA_JUDUL")
        val deskripsi = intent.getStringExtra("EXTRA_DESKRIPSI")
        val gambar = intent.getIntExtra("EXTRA_GAMBAR", 0)

        // Set data ke komponen UI
        tvDetailJudul.text = judul
        tvDetailDeskripsi.text = deskripsi
        imgDetailLagu.setImageResource(gambar)

        // Tombol kembali
        btnBack.setOnClickListener {
            finish()
        }
    }
}