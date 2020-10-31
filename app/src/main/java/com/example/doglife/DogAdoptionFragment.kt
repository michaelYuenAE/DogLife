package com.example.doglife

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doglife.model.*
import com.example.doglife.databinding.FragmentAdoptOverviewBinding
import java.util.*

class DogAdoptionFragment: Fragment() {
    private lateinit var mBinding: FragmentAdoptOverviewBinding
    private lateinit var mContext: Context

    val dummyDog = Dog("0","0","DUMMY", "Aladdin", Gender.Male, AnimalType.Dog, DogAge.Young, Date(0), CountryCode.HK,"dummy", "00000", true, DogColor.Brown, null, null, DogSize.Large, null, null, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAdoptionPage()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAdoptOverviewBinding.inflate(inflater, container, false)
        mContext = mBinding.root.context
        return mBinding.root
    }

    private fun initAdoptionPage() {
        val gridLayoutManager = GridLayoutManager(mContext, 3)
        gridLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mBinding.rvPetOverview.layoutManager = gridLayoutManager


    }
}