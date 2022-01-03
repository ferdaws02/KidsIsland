package com.example.kidsislandv1.database

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("/signup")
    fun seConnecter(@Body map :HashMap<String,String>): Call<User>

    companion object {

        var BASE_URL = "http://10.0.2.2:3000/"

        fun create(): ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}