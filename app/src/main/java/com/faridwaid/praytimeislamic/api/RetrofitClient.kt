package com.faridwaid.praytimeislamic.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    //mendefinisikan BASE_URL
    private const val BASE_URL = "https://muslimsalat.com/"

    //menggunakan library retrofit untuk digunakan pada API
    val instance: Api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(Api::class.java)
    }
}