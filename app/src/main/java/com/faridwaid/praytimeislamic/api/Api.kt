package com.faridwaid.praytimeislamic.api

import com.faridwaid.praytimeislamic.model.DoaResponse
import retrofit2.Call
import retrofit2.http.GET

interface Api {

    @GET("https://doa-doa-api-ahmadramadhan.fly.dev/api")
    fun getPosts(): Call<ArrayList<DoaResponse>>

}