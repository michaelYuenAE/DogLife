package com.example.doglife.retrofit

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("/register_pet")
    fun registerAnimal(
        @Query("name") name: String,
        @Query("type") type: String,
        @Query("address") address: String,
        @Query("subject") subject: String
    ): Call<Unit>
}