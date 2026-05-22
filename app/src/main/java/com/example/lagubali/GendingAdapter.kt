package com.example.lagubali

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GendingAdapter(
    private var listGending: Array<Gending>,
    var playingAudioId: Int = -1,
    private val onToggleAudioClick: (Gending, Button) -> Unit
) : RecyclerView.Adapter<GendingAdapter.GendingViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: Array<Gending>) {
        this.listGending = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GendingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gending, parent, false)
        return GendingViewHolder(view)
    }

    override fun onBindViewHolder(holder: GendingViewHolder, position: Int) {
        val gending = listGending[position]
        holder.tvJudul.text = gending.judul
        holder.tvDeskripsi.text = gending.deskripsi
        holder.imgLagu.setImageResource(gending.gambarResId)

        if (gending.audioResId == playingAudioId) {
            holder.btnTogglePlay.text = "⏸"
        } else {
            holder.btnTogglePlay.text = "▶"
        }

        holder.btnTogglePlay.setOnClickListener {
            onToggleAudioClick(gending, holder.btnTogglePlay)
        }
    }

    override fun getItemCount(): Int = listGending.size

    class GendingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgLagu: ImageView = itemView.findViewById(R.id.imgItemLagu)
        val tvJudul: TextView = itemView.findViewById(R.id.tvItemJudul)
        val tvDeskripsi: TextView = itemView.findViewById(R.id.tvItemDeskripsi)
        val btnTogglePlay: Button = itemView.findViewById(R.id.btnTogglePlay)
    }
}