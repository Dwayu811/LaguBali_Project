package com.example.lagubali

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imgDetailLagu = findViewById<ImageView>(R.id.imgDetailLagu)
        val tvDetailJudul = findViewById<TextView>(R.id.tvDetailJudul)
        val tvDetailDeskripsi = findViewById<TextView>(R.id.tvDetailDeskripsi)
        val btnPlay = findViewById<Button>(R.id.btnPlay)
        val btnPause = findViewById<Button>(R.id.btnPause)
        val btnBack = findViewById<Button>(R.id.btnBack)

        val judul = intent.getStringExtra("EXTRA_JUDUL") ?: "Tidak Ada Judul"
        val deskripsi = intent.getStringExtra("EXTRA_DESKRIPSI") ?: "Tidak Ada Deskripsi"
        val gambar = intent.getIntExtra("EXTRA_GAMBAR", 0)
        val audioResId = intent.getIntExtra("EXTRA_AUDIO", 0)

        tvDetailJudul.text = judul
        tvDetailDeskripsi.text = deskripsi
        imgDetailLagu.setImageResource(gambar)

        // Mempersiapkan file audio tanpa langsung memutarnya
        inisialisasiAudio(audioResId)

        btnPlay.setOnClickListener {
            if (mediaPlayer != null && !mediaPlayer!!.isPlaying) {
                mediaPlayer?.start()
                Toast.makeText(this, "Memutar lagu", Toast.LENGTH_SHORT).show()
            }
        }

        btnPause.setOnClickListener {
            if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
                mediaPlayer?.pause()
                Toast.makeText(this, "Lagu di-pause", Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun inisialisasiAudio(audioId: Int) {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()

            // Hanya menyiapkan data audio ke memori
            mediaPlayer = MediaPlayer.create(this, audioId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer != null) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }
}