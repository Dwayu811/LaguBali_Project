package com.example.kataloggending

// Data class ini berfungsi sebagai wadah untuk satu lagu
data class Gending(
    val judul: String,
    val deskripsi: String,
    val audioResId: Int, // Menyimpan ID suara dari folder res/raw
    val gambarResId: Int // Menyimpan ID gambar dari folder res/drawable
)