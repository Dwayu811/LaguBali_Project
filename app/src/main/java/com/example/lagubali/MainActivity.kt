package com.example.lagubali

import android.content.res.Configuration
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private val daftarGending = arrayOf(
        Gending("Meong-Meong", "Ciptaan: Anonim. ", R.drawable.meong_meong, R.raw.meongmeong),
        Gending("Made Cenik ", "Ciptaan: Anonim. ", R.drawable.made_cenik, R.raw.madecenik),
        Gending("Ratu Anom", "Ciptaan: Anonim. ", R.drawable.ratu_anom, R.raw.ratuanom),
        Gending("Sekar Emas ", "Ciptaan: Anonim. ", R.drawable.sekar_emas, R.raw.sekaremas),
        Gending("Juru Pencar ", "Ciptaan: Anonim. ", R.drawable.juru_pencar, R.raw.jurupencar),
        Gending("Adi Sayang", "Ciptaan: Okid.", R.drawable.adi_sayang, R.raw.adisayang),
        Gending("Curik Curik", "Ciptaan: Anonim .", R.drawable.curik_curik, R.raw.curikcurik),
        Gending("Semut Api ", "Ciptaan: Anonim.", R.drawable.semut_api, R.raw.semutapi),
        Gending("Dadong Dauh ", "Ciptaan: Anonim.", R.drawable.dadong_dauh, R.raw.dadongdauh),
        Gending("Goak Maling ", "Ciptaan: Made Taro.", R.drawable.goak_maling, R.raw.goakmaling),
        Gending("Jenggot Uban ", "Ciptaan: Anonim.", R.drawable.jenggot_uban, R.raw.jenggotuban),
        Gending("Kaki Sayang ", "Ciptaan: A.A Made Cakra.", R.drawable.kaki_sayang, R.raw.kakisayang),
        Gending("Majangeran ", "Ciptaan: Anonim.", R.drawable.majangeran, R.raw.majangeran),
        Gending("Pangelong ", "Ciptaan: Anonim.", R.drawable.pangelong, R.raw.pangelong),
        Gending("Peteng Bulan ", "Ciptaan: Anonim.", R.drawable.peteng_bulan, R.raw.petengbulan),
        Gending("Macepet Cepetan ", "Ciptaan: Anonim.", R.drawable.macepet_cepetan, R.raw.macepetcepetan),
        Gending("Pitutur Guru ", "Ciptaan: Anonim.", R.drawable.pitutur_guru, R.raw.pituturguru),
        Gending("Merah Putih ", "Ciptaan: I Gede Dharma.", R.drawable.merah_putih, R.raw.merahputih),
        Gending("Yening Kelih ", "Ciptaan: I Gusti Made Kartika.", R.drawable.yening_kelih, R.raw.yeningkelih),
        Gending("lutung Ngerem Ikut ", "Ciptaan: Made Taro.", R.drawable.lutung_ngerem_ikut, R.raw.lutungngeremikut),
        Gending("Melali Sambilang Melajah ", "Ciptaan: Ari Ariama.", R.drawable.melali_sambilang_melajah, R.raw.melalisambilangmelajah),
        Gending("Putri Cening Ayu ", "Ciptaan: Anonim.", R.drawable.putri_cening_ayu, R.raw.putriceningayu),
        Gending("Melali Ka Nusa Dua ", "Ciptaan: Ngakan Rai Lanus.", R.drawable.melalika_nusadua, R.raw.melalikanusadua),
        Gending("Kupu-kupu Cenik", "Ciptaan: Pande Sudana.", R.drawable.kupu_cenik, R.raw.kupukupucenik)
    )

    private var dataTampil = ArrayList<Gending>()
    private lateinit var adapter: GendingAdapter
    private var mediaPlayer: MediaPlayer? = null
    private var currentAudioId: Int = -1
    private var lastSearchQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (dataTampil.isEmpty()) {
            dataTampil.addAll(daftarGending)
        }
        initUI()
    }

    private fun initUI() {
        setContentView(R.layout.activity_main)
        // Gunakan ContextCompat untuk keamanan warna
        window.statusBarColor = ContextCompat.getColor(this, R.color.bali_sage)

        val rvGending = findViewById<RecyclerView>(R.id.rvGending)
        val etSearch = findViewById<EditText>(R.id.etSearch)
        val btnSort = findViewById<Button>(R.id.btnSort)

        rvGending.layoutManager = LinearLayoutManager(this)

        // Inisialisasi adapter sebelum digunakan
        adapter = GendingAdapter(
            listGending = dataTampil.toTypedArray(),
            onToggleAudioClick = { gending, _ -> toggleAudio(gending) }
        )
        rvGending.adapter = adapter
        
        // Kembalikan query pencarian jika ada (saat rotasi)
        if (lastSearchQuery.isNotEmpty()) {
            etSearch.setText(lastSearchQuery)
            etSearch.setSelection(lastSearchQuery.length)
        }

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val inputUser = s.toString().trim()
                lastSearchQuery = inputUser
                if (inputUser.isEmpty()) {
                    dataTampil.clear()
                    dataTampil.addAll(daftarGending)
                    updateAdapterData()
                } else if (inputUser.length >= 3) {
                    kodinganLinearSearch(inputUser)
                }
            }
        })

        btnSort.setOnClickListener { kodinganBubbleSortAtoZ() }
        syncUI()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Refresh UI tanpa menghancurkan Activity agar musik tetap jalan
        initUI() 
    }

    private fun kodinganLinearSearch(keyword: String) {
        dataTampil.clear()
        for (lagu in daftarGending) {
            if (lagu.judul.lowercase().contains(keyword.lowercase())) {
                dataTampil.add(lagu)
            }
        }
        updateAdapterData()
    }

    private fun kodinganBubbleSortAtoZ() {
        val n = dataTampil.size
        for (i in 0 until n - 1) {
            for (j in 0 until n - i - 1) {
                if (dataTampil[j].judul.lowercase() > dataTampil[j + 1].judul.lowercase()) {
                    val temp = dataTampil[j]
                    dataTampil[j] = dataTampil[j + 1]
                    dataTampil[j + 1] = temp
                }
            }
        }
        updateAdapterData()
    }

    private fun updateAdapterData() {
        if (::adapter.isInitialized) {
            adapter.updateData(dataTampil.toTypedArray())
            syncUI()
        }
    }

    private fun toggleAudio(gending: Gending) {
        if (currentAudioId == gending.audioResId) {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            } else {
                mediaPlayer?.start()
            }
        } else {
            jalankanAudioKatalog(gending.audioResId)
            Toast.makeText(this, "Memutar: ${gending.judul}", Toast.LENGTH_SHORT).show()
        }
        syncUI()
    }

    private fun syncUI() {
        if (::adapter.isInitialized) {
            adapter.playingAudioId = if (mediaPlayer?.isPlaying == true) currentAudioId else -1
            adapter.notifyDataSetChanged()
        }
    }

    private fun jalankanAudioKatalog(audioId: Int) {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(this, audioId)
            currentAudioId = audioId
            mediaPlayer?.setOnCompletionListener {
                currentAudioId = -1
                syncUI()
            }
            mediaPlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // HANYA matikan musik jika aplikasi benar-benar ditutup, bukan saat rotasi
        if (!isChangingConfigurations) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }
}
