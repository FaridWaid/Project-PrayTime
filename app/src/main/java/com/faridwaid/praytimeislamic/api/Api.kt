package com.faridwaid.praytimeislamic.api

import com.faridwaid.praytimeislamic.model.DoaResponse
import com.faridwaid.praytimeislamic.model.PraysTime
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    // fungsi untuk mendapatkan rest api doa
    @GET("https://doa-doa-api-ahmadramadhan.fly.dev/api")
    fun getPosts(): Call<ArrayList<DoaResponse>>

    // fungsi untuk mendapatkan rest api prays time
    @GET("{city}.json?key=f05f05efa08d5cf2e27044f58111fd32")
    fun getPrayTime(@Path("city") city: String): Call<PraysTime>

}