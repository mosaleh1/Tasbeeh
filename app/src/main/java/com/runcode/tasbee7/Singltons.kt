package com.runcode.tasbee7.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    fun buildRetrofit():HadithApi{
        return Retrofit.Builder()
            .baseUrl("https://ahadith-api.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HadithApi::class.java)
    }
}
