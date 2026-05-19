package com.example.kataloggending

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lagubali.R

class GendingAdapter(
    private val listGending: Array<Gending>,
    private val onItemClick: (Gending) -> Unit
) : RecyclerView.Adapter<GendingAdapter.GendingViewHolder>() {

    // 1. Menghubungkan file item_gending.xml ke RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GendingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gending, parent, false)
        return GendingViewHolder(view)
    }

    // 2. Memasukkan data dari Array lagu ke-X ke dalam komponen UI (Foto & Teks)
    override fun onBindViewHolder(holder: GendingViewHolder, position: Int) {
        val gending = listGending[position]

        holder.tvJudul.text = gending.judul
        holder.tvDeskripsi.text = gending.deskripsi
        holder.imgLagu.setImageResource(gending.gambarResId) // Mengatur foto otomatis

        // Ketika baris lagu diklik, picu fungsi click (Persiapan Intent Minggu 2)
        holder.itemView.setOnClickListener {
            onItemClick(gending)
        }
    }

    // 3. Memberitahu RecyclerView bahwa total data ada 25 lagu
    override fun getItemCount(): Int = listGending.size

    // Kelas internal untuk mengenali ID yang ada di item_gending.xml
    class GendingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgLagu: ImageView = itemView.findViewById(R.id.imgItemLagu)
        val tvJudul: TextView = itemView.findViewById(R.id.tvItemJudul)
        val tvDeskripsi: TextView = itemView.findViewById(R.id.tvItemDeskripsi)
    }
}