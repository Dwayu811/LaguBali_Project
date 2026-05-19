package com.example.kataloggending

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lagubali.R

class MainActivity : AppCompatActivity() {

    // 1. Deklarasi Array Data (25 Lagu)
    private val daftarGending = arrayOf(
        Gending("Meong-Meong", "Lagu tentang kucing.", R.drawable.meong_meong, R.raw.meongmeong),
        Gending("Ratu Anom", "Nasihat bangun pagi.", R.drawable.ratu_anom, R.raw.ratuanom),
        Gending("Adi Sayang", "Nasihat bangun pagi.", R.drawable.adi_sayang, R.raw.adisayang),
        Gending("Curik Curik", "Nasihat bangun pagi.", R.drawable.curik_curik, R.raw.curikcurik),
        Gending("Dadong Dauh ", "Nasihat bangun pagi.", R.drawable.dadong_dauh, R.raw.dadongdauh),
        Gending("Goak Maling ", "Nasihat bangun pagi.", R.drawable.goak_maling, R.raw.goakmaling),
        Gending("Jenggot Uban ", "Nasihat bangun pagi.", R.drawable.jenggot_uban, R.raw.jenggotuban),
        Gending("Juru Pencar ", "Nasihat bangun pagi.", R.drawable.juru_pencar, R.raw.jurupencar),
        Gending("Kaki Sayang ", "Nasihat bangun pagi.", R.drawable.kaki_sayang, R.raw.kakisayang),
        Gending("lutung Ngerem Ikut ", "Nasihat bangun pagi.", R.drawable.lutung_ngerem_ikut, R.raw.lutungngeremikut),
        Gending("Macepet Cepetan ", "Nasihat bangun pagi.", R.drawable.macepet_cepetan, R.raw.macepetcepetan),
        Gending("Made Cenik ", "Nasihat bangun pagi.", R.drawable.made_cenik, R.raw.madecenik),
        Gending("Majangeran ", "Nasihat bangun pagi.", R.drawable.majangeran, R.raw.majangeran),
        Gending("Melali Sambilang Melajah ", "Nasihat bangun pagi.", R.drawable.melali_sambilang_melajah, R.raw.melalisambilangmelajah),
        Gending("Melali Ka Nusa Dua ", "Nasihat bangun pagi.", R.drawable.melalika_nusadua, R.raw.melalikanusadua),
        Gending("Pangelong ", "Nasihat bangun pagi.", R.drawable.pangelong, R.raw.pangelong),
        Gending("Peteng Bulan ", "Nasihat bangun pagi.", R.drawable.peteng_bulan, R.raw.petengbulan),
        Gending("Pitutur Guru ", "Nasihat bangun pagi.", R.drawable.pitutur_guru, R.raw.pituturguru),
        Gending("Putri Cening Ayu ", "Nasihat bangun pagi.", R.drawable.putri_cening_ayu, R.raw.putriceningayu),
        Gending("Sekar Emas ", "Nasihat bangun pagi.", R.drawable.sekar_emas, R.raw.sekaremas),
        Gending("Semut Api ", "Nasihat bangun pagi.", R.drawable.semut_api, R.raw.semutapi),
        Gending("Merah Putih ", "Nasihat bangun pagi.", R.drawable.merah_putih, R.raw.merahputih),
        Gending("Yening Kelih ", "Nasihat bangun pagi.", R.drawable.yening_kelih, R.raw.yeningkelih),
        Gending("Kupu-kupu Cenik", "Kupu-kupu kecil.", R.drawable.kupu_cenik, R.raw.kupukupucenik)
    )

    private var dataTampil = ArrayList<Gending>()
    private lateinit var adapter: GendingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ubah warna status bar biar estetik Bali Sage (Opsional)
        window.statusBarColor = resources.getColor(R.color.bali_sage, theme)

        // Inisialisasi Komponen UI dari XML
        val rvGending = findViewById<RecyclerView>(R.id.rvGending)
        val etSearch = findViewById<EditText>(R.id.etSearch)
        val btnSort = findViewById<Button>(R.id.btnSort)

        rvGending.layoutManager = LinearLayoutManager(this)

        // Masukkan semua data dari Array statis ke daftar tampilan awal
        dataTampil.clear()
        dataTampil.addAll(daftarGending)

        // Set adapter awal untuk menampilkan data
        updateAdapterData()

        // PEMICU SEARCHING: Validasi Input + Memanggil Fungsi Linear Search
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val inputUser = s.toString().trim()

                if (inputUser.isEmpty()) {
                    etSearch.error = "Ketik judul gending yang mau dicari!"
                    // Jika kolom kosong, kembalikan list ke 25 lagu awal
                    dataTampil.clear()
                    dataTampil.addAll(daftarGending)
                    updateAdapterData()
                } else if (inputUser.length < 3) {
                    etSearch.error = "Minimal ketik 3 huruf ya!"
                } else {
                    etSearch.error = null
                    // Memanggil fungsi Linear Search (Target Minggu 3)
                    kodinganLinearSearch(inputUser)
                }
            }
        })

        // PEMICU SORTING: Saat tombol di klik, jalankan Bubble Sort
        btnSort.setOnClickListener {
            kodinganBubbleSortAtoZ()
        }

    } // <--- Batas Akhir onCreate (Tutup Kurung onCreate)


    // 3. FUNGSI LINEAR SEARCH MANUAL (Ditaruh di luar onCreate)
    private fun kodinganLinearSearch(keyword: String) {
        dataTampil.clear() // Bersihkan list lama sebelum menampung hasil baru

        // Algoritma Linear Search: Cek satu per satu dari awal sampai akhir Array
        for (i in 0 until daftarGending.size) {
            val lagu = daftarGending[i]
            if (lagu.judul.lowercase().contains(keyword.lowercase())) {
                dataTampil.add(lagu) // Masukkan ke list jika cocok
            }
        }
        updateAdapterData() // Perbarui layar
    }

    // 4. FUNGSI BUBBLE SORT MANUAL (Ditaruh di luar onCreate)
    private fun kodinganBubbleSortAtoZ() {
        val n = dataTampil.size
        // Algoritma urut gelembung (Bubble Sort)
        for (i in 0 until n - 1) {
            for (j in 0 until n - i - 1) {
                // Bandingkan huruf judul lagu ke-j dengan sebelahnya
                if (dataTampil[j].judul.lowercase() > dataTampil[j + 1].judul.lowercase()) {
                    // Proses Tukar Data (Swap)
                    val temp = dataTampil[j]
                    dataTampil[j] = dataTampil[j + 1]
                    dataTampil[j + 1] = temp
                }
            }
        }
        updateAdapterData() // Perbarui layar
    }

    // 5. FUNGSI HELPER: Untuk mereset ulang adapter ke RecyclerView setiap ada perubahan data
    private fun updateAdapterData() {
        adapter = GendingAdapter(dataTampil.toTypedArray()) { gending ->
            // Logika Intent (Target Minggu 2)
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("EXTRA_JUDUL", gending.judul)
                putExtra("EXTRA_DESKRIPSI", gending.deskripsi)
                putExtra("EXTRA_GAMBAR", gending.gambarResId)
                putExtra("EXTRA_AUDIO", gending.audioResId)
            }
            startActivity(intent)
        }
        val rvGending = findViewById<RecyclerView>(R.id.rvGending)
        rvGending.adapter = adapter
    }

}