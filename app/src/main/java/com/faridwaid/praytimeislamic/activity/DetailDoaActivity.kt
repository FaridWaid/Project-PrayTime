package com.faridwaid.praytimeislamic.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.faridwaid.praytimeislamic.R

class DetailDoaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_doa)

        val tvSetTitle: TextView = findViewById(R.id.titleDoa)
        val tvSetAyat: TextView = findViewById(R.id.ayat_doa)
        val tvSetLatin: TextView = findViewById(R.id.latin_doa)
        val tvSetArti: TextView = findViewById(R.id.arti_doa)

        val tIdDoa = intent.getStringExtra(EXTRA_ID)
        val tJudulDoa = intent.getStringExtra(EXTRA_DOA)
        val tAyatDoa = intent.getStringExtra(EXTRA_AYAT)
        val tLatinDoa = intent.getStringExtra(EXTRA_LATIN)
        val tArtiDoa = intent.getStringExtra(EXTRA_ARTINYA)

        tvSetTitle.text = tJudulDoa
        tvSetAyat.text = tAyatDoa
        tvSetLatin.text = tLatinDoa
        tvSetArti.text = tArtiDoa

        // Ketika "backButton" di klik
        // overridePendingTransition digunakan untuk animasi dari intent
        val backButton: ImageView = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            // Jika berhasil maka akan pindah ke LoginActivity
            onBackPressed()
            overridePendingTransition(R.anim.slide_from_top, R.anim.slide_to_bottom)
        }

    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_DOA = "extra_doa"
        const val EXTRA_AYAT = "extra_ayat"
        const val EXTRA_LATIN = "extra_latin"
        const val EXTRA_ARTINYA = "extra_artinya"
    }

    //back button
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_top, R.anim.slide_to_bottom)
    }

}