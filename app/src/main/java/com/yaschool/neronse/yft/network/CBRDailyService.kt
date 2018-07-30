package com.yaschool.neronse.yft.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

//https://www.cbr-xml-daily.ru/
// daily_json.js

interface CBRDailyService{
    @GET("daily_json.js")
    fun request(): Call<CBRDaily>

    companion object Factory{
        fun create():CBRDailyService{
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://www.cbr-xml-daily.ru/")
                    .build()
            return retrofit.create(CBRDailyService::class.java)
        }
    }
}