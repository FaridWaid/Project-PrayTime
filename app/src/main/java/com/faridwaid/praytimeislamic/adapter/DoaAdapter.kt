package com.faridwaid.praytimeislamic.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.faridwaid.praytimeislamic.DoaFragment
import com.faridwaid.praytimeislamic.MainActivity
import com.faridwaid.praytimeislamic.R
import com.faridwaid.praytimeislamic.activity.DetailDoaActivity
import com.faridwaid.praytimeislamic.model.DoaResponse

class DoaAdapter(private val list: ArrayList<DoaResponse>): RecyclerView.Adapter<DoaAdapter.PostViewHolder>() {
    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvResponse: TextView = itemView.findViewById(R.id.titleDoa)
        fun bind(doa: DoaResponse){
            with(itemView){
                val text = "${doa.doa}"
                tvResponse.text = text
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_doa, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(list[position])

        val dataDoa = list[position]

        val mContext = holder.itemView.context

        holder.itemView.setOnClickListener{
            val moveDetail = Intent(mContext, DetailDoaActivity::class.java)
            moveDetail.putExtra(DetailDoaActivity.EXTRA_ID, dataDoa.id)
            moveDetail.putExtra(DetailDoaActivity.EXTRA_DOA, dataDoa.doa)
            moveDetail.putExtra(DetailDoaActivity.EXTRA_AYAT, dataDoa.ayat)
            moveDetail.putExtra(DetailDoaActivity.EXTRA_LATIN, dataDoa.latin)
            moveDetail.putExtra(DetailDoaActivity.EXTRA_ARTINYA, dataDoa.artinya)
            mContext.startActivity(moveDetail)
        }

    }

    override fun getItemCount(): Int = list.size

}