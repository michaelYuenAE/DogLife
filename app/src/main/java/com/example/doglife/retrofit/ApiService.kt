package com.example.doglife.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @Multipart
    @POST("/register_pet")
    fun registerAnimal(
        @Query("name") name: String,
        @Query("type") type: String,
        @Query("address") address: String,
        @Query("subject") subject: String,
        @PartMap files: HashMap<String, RequestBody>
    ): Call<RegisterPetResult>

    class RegisterPetResult {
        @SerializedName("status")
        @Expose
        var status: String? = null

        @SerializedName("lastId")
        @Expose
        var lastId: Int? = null
    }

    @Multipart
    @POST("/upload_pet_file")
    fun uploadMultipleFiles(
        @PartMap files: Map<String, RequestBody>
    ): Call<UploadResult>


    @Multipart
    @POST("/upload_pet")
    fun uploadFileWithPartMap(
        @PartMap partMap: HashMap<String, RequestBody>,
        @Part file: List<MultipartBody.Part>?
    ): Call<RegisterPetResult>

    class UploadResult {
        var code = 0
        var image_urls: List<String>? = null
    }
}