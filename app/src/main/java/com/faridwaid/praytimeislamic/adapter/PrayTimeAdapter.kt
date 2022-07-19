package com.faridwaid.praytimeislamic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.faridwaid.praytimeislamic.R
import com.faridwaid.praytimeislamic.model.PrayTime

class PrayTimeAdapter(private val viewPager: List<PrayTime>): RecyclerView.Adapter<PrayTimeAdapter.PrayViewHolder>() {

    // Membuat class PostViewHolder yang digunakan untuk set view yang akan ditampilkan,
    inner class PrayViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textTitle: TextView = itemView.findViewById(R.id.timePray)
        val textNextPray: TextView = itemView.findViewById(R.id.nextPray)
        fun bind(prayTime: PrayTime){
            with(itemView){
                textNextPray.text = "Akan datang: ${prayTime.sholat}"
                textTitle.text = prayTime.waktu
            }
        }
    }

    // Menentukan layout yang akan ditampilkan dalam recyclerview
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_container_praytime, parent, false)
        return PrayViewHolder(view)
    }

    // Memasukkan data ke dalam list recyclerview seasui dengan posisi/position,
    override fun onBindViewHolder(holder: PrayViewHolder, position: Int) {
        holder.bind(viewPager[position])
    }

    // Mendapatkan jumlah data dari list
    override fun getItemCount(): Int = viewPager.size

}