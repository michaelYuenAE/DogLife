package com.example.doglife.adopt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.doglife.model.Dog

class AdoptViewModel: ViewModel() {
    val adoptPetData = MutableLiveData<List<Dog>>()
}