package com.example.doglife.adopt

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.doglife.R
import com.example.doglife.databinding.FragmentAdoptOverviewBinding
import com.example.doglife.model.*
import com.example.doglife.retrofit.ApiService
import com.example.doglife.retrofit.ApiUtils
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

private const val MAX_OVERVIEW_PER_QUERY = 10

class DogAdoptionFragment: Fragment(), PetOverviewAdapter.ItemListener {

    companion object {
        private val TAG = DogAdoptionFragment::class.java.name
    }

    private lateinit var mBinding: FragmentAdoptOverviewBinding
    private lateinit var mContext: Context
    private lateinit var mViewModel: AdoptViewModel
    private lateinit var mApiService: ApiService

    private val dummyDog = Dog(
        "0",
        "0",
        "DUMMY",
        "Aladdin",
        Gender.Male,
        AnimalType.Dog,
        DogAge.Young,
        Date(
            0
        ),
        CountryCode.HK,
        "dummy",
        "00000",
        true,
        DogColor.Brown,
        null,
        null,
        DogSize.Large,
        null,
        null,
        null
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(AdoptViewModel::class.java)
        mApiService = ApiUtils.getApiService()
    }

    private fun setAppBar() {
        mBinding.appBar.myToolbar.title = resources.getString(R.string.cd_home)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_dog_adopt_overview, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add) {
            view?.findNavController()?.navigate(R.id.action_overview_to_register)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAppBar()
        initAdoptionPage()
        getAdoptData()
    }

    private fun getAdoptData() {
        mApiService.getPetOverview("Dog", MAX_OVERVIEW_PER_QUERY, 0).enqueue(object :
            Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                Log.d(TAG, response.body().toString())
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d("error", t.message);
            }
        })
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