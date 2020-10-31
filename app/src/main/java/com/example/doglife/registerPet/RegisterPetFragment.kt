package com.example.doglife.registerPet

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.example.doglife.R
import com.example.doglife.databinding.FragmentRegisterPetBinding
import com.example.doglife.retrofit.ApiService
import com.example.doglife.retrofit.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterPetFragment: Fragment() {
    private lateinit var mBinding: FragmentRegisterPetBinding
    private lateinit var mContext: Context
    private lateinit var mApiService: ApiService
    private val TAG: String = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentRegisterPetBinding.inflate(inflater, container, false)
        mContext = mBinding.root.context
        mApiService = ApiUtils.getApiService()
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRegisterView()
        initClickListener()
    }

    private fun initRegisterView() {
        mBinding.nameField.tvTitle.text = getString(R.string.register_title_name)
        mBinding.typeField.tvTitle.text = getString(R.string.register_title_type)
        mBinding.addressField.tvTitle.text = getString(R.string.register_title_address)
        mBinding.addImageField.tvTitle.text = getString(R.string.register_title_image)
    }

    private fun initClickListener() {
        mBinding.buttonSubmit.setOnClickListener {
            val name = mBinding.nameField.tvDescription.text.toString().trim()
            val checkedRadioButtonId = mBinding.typeField.radioGroupType.checkedRadioButtonId
            val type = mBinding.typeField.radioGroupType.findViewById<RadioButton>(
                checkedRadioButtonId
            ).text.toString()
            val address = mBinding.addressField.tvDescription.text.toString().trim()

            //image later
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(address)) {
                sendPost(name, type, address)
            }
        }
    }

    private fun sendPost(name: String, type: String, address: String) {
        mApiService.registerAnimal(name, type, address, "register_pet").enqueue(object : Callback<Unit> {

            override fun onResponse(call: Call<Unit>?, response: Response<Unit>?) {
                Log.d(TAG, "onResponse ${response?.body()}")
            }

            override fun onFailure(call: Call<Unit>?, t: Throwable?) {
                Log.d(TAG, "onFailure ${t.toString()}")
            }

        })
    }

}