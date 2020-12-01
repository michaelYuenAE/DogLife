package com.example.doglife.adopt

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.doglife.MainActivity
import com.example.doglife.model.*
import com.example.doglife.databinding.FragmentAdoptOverviewBinding
import com.example.doglife.databinding.SillycheckBinding
import java.util.*

class DogAdoptionFragment: Fragment(), PetOverviewAdapter.ItemListener {

    companion object {
        private val TAG = DogAdoptionFragment::class.java.name
    }

    private lateinit var mBinding: FragmentAdoptOverviewBinding

    private lateinit var mContext: Context
    private lateinit var mViewModel: AdoptViewModel

    private val dummyDog = Dog("0","0","DUMMY", "Aladdin", Gender.Male, AnimalType.Dog, DogAge.Young, Date(0), CountryCode.HK,"dummy", "00000", true, DogColor.Brown, null, null, DogSize.Large, null, null, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(AdoptViewModel::class.java)
        getAdoptData()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdoptionPage()
    }

    private fun getAdoptData() {

    }

    private fun initAdoptionPage() {
        val dummyDogList = mutableListOf<Dog>()
        for (x in 0..10) {
            dummyDogList.add(dummyDog)
        }
        mBinding.rvPetOverview.adapter = PetOverviewAdapter(dummyDogList, mContext, this)
        mBinding.rvPetOverview.layoutManager =
            GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false)
        mBinding.rvPetOverview.layoutManager = AutoFitGridLayoutManager(mContext, 500)
    }

    override fun onItemClick(item: Dog) {
        Toast.makeText(
            mContext,
            item.name + " is clicked",
            Toast.LENGTH_SHORT
        ).show()
    }
}