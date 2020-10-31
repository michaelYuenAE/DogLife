package com.example.doglife.retrofit

class ApiUtils {
    companion object {
        private const val BASE_URL = "http://192.168.1.15:5000"
        fun getApiService(): ApiService {
            return RetrofitClient.getClient(BASE_URL)!!.create(ApiService::class.java)
        }
    }


}