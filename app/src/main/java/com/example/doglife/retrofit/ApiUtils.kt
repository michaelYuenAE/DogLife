package com.example.doglife.retrofit

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class ApiUtils {
    companion object {
        const val API_BASE_URL = "http://192.168.1.15:5000"
//        const val API_BASE_URL = "http://localhost:5000"
        fun getApiService(): ApiService {
            return RetrofitClient.getClient(API_BASE_URL)!!.create(ApiService::class.java)
        }

        fun createPartFromString(param: String): RequestBody {
            return RequestBody.create(MediaType.parse("multipart/register-data"), param)
        }

        fun prepareImageFilePart(partName: String, file: File): MultipartBody.Part {
            val requestFile = RequestBody.create(
                MediaType.parse("image/jpg"),
                file
            )
            return MultipartBody.Part.createFormData(partName, file.name, requestFile)
        }
    }

}