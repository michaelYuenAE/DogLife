package com.example.doglife.User

import com.example.doglife.Model.CountryCode
import com.example.doglife.Model.Dog

class User (
    val id: String,
    val firstName: String,
    val lastName: String,
    val country: CountryCode,
    val postalCode: String,
    val address: String,
    val displayName: String,
    val iconUrl: String?,
    val tel: String,
    val email: String,
    val password: String,
    val likedDog: List<Dog>
)