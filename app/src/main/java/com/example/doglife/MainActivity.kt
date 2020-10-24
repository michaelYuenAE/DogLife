package com.example.doglife

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.example.doglife.databinding.ActivityMainBinding
import com.example.doglife.login.LoginView
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MainActivity : FragmentActivity () {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dogAdoptionFragment = DogAdoptionFragment();
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_main_placeholder, dogAdoptionFragment)
        fragmentTransaction.commit()
    }

}