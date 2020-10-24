package com.example.doglife

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.doglife.databinding.ActivityMainBinding

class MainActivity : FragmentActivity () {
    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        val dogAdoptionFragment = DogAdoptionFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_main_placeholder, dogAdoptionFragment)
        fragmentTransaction.commit()
    }

}