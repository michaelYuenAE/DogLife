package com.example.doglife.registerPet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doglife.R
import com.example.doglife.databinding.FragmentRegisterPetBinding
import com.example.doglife.filepreview.FilePreviewAdapter
import com.example.doglife.retrofit.ApiService
import com.example.doglife.retrofit.ApiUtils
import com.example.doglife.retrofit.ApiUtils.Companion.API_BASE_URL
import com.example.doglife.utils.GlideImageLoader
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.bean.ImageItem
import com.lzy.imagepicker.ui.ImageGridActivity
import com.lzy.imagepicker.view.CropImageView
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class RegisterPetFragment: Fragment() {
    private lateinit var mBinding: FragmentRegisterPetBinding
    private lateinit var mContext: Context
    private lateinit var mApiService: ApiService
    private lateinit var mImagePicker: ImagePicker
    private var mImagePreviewAdapter: FilePreviewAdapter? = null
    private var imagesList: MutableLiveData<List<ImageItem>?> = MutableLiveData(null)
    private val TAG: String = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentRegisterPetBinding.inflate(inflater, container, false)
        mContext = mBinding.root.context
        mApiService = ApiUtils.getApiService()

        mImagePicker = ImagePicker.getInstance()
        mImagePicker.imageLoader = GlideImageLoader(mContext) //设置图片加载器
        mImagePicker.isShowCamera = true //显示拍照按钮
        mImagePicker.isCrop = true //允许裁剪（单选才有效）
        mImagePicker.isSaveRectangle = true //是否按矩形区域保存
        mImagePicker.selectLimit = 9 //选中数量限制
        mImagePicker.style = CropImageView.Style.RECTANGLE //裁剪框的形状
        mImagePicker.focusWidth = 800 //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        mImagePicker.focusHeight = 800 //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        mImagePicker.outPutX = 1000 //保存文件的宽度。单位像素
        mImagePicker.outPutY = 1000 //保存文件的高度。单位像素


        mBinding.addImageField.ivAdd.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, ImageGridActivity::class.java)
            startActivityForResult(intent, 1)
        })

        mBinding.buttonSubmit.setOnClickListener(View.OnClickListener {
            Toast.makeText(mContext, "Uploading", Toast.LENGTH_SHORT).show()
            uploadFiles()
        })
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRegisterView()
        initClickListener()
        initImageListObserver()
    }

    private fun initImageListObserver() {
        imagesList.observe(viewLifecycleOwner, Observer { data ->
            if (data != null) {
                mImagePreviewAdapter?.setData(data)
            }
        })
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

    //call api to register a new pet
    private fun sendPost(name: String, type: String, address: String) {
        mApiService.registerAnimal(name, type, address, "register_pet").enqueue(object :
            Callback<ApiService.RegisterPetResult> {

            override fun onResponse(
                call: Call<ApiService.RegisterPetResult>?,
                response: Response<ApiService.RegisterPetResult>?
            ) {
                Log.d(
                    TAG,
                    "onResponse status: ${response?.body()?.status} lastRowId: ${response?.body()?.lastId}"
                )
                response?.body()?.lastId.let { lastRowId ->


                }
            }

            override fun onFailure(call: Call<ApiService.RegisterPetResult>?, t: Throwable?) {
                Log.d(TAG, "onFailure ${t.toString()}")
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                imagesList.postValue(data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) as List<ImageItem>?)
                imagesList.value.let { data ->
                    if (data.isNullOrEmpty()) {
                        return
                    }
                    mImagePreviewAdapter = FilePreviewAdapter(mContext, data) { view, path ->
                        val newImageList = imagesList.value?.filter { it.path != path} ?: mutableListOf()
                        imagesList.postValue(newImageList)
                    }
                    mBinding.addImageField.imagePreview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    mBinding.addImageField.imagePreview.adapter = mImagePreviewAdapter
                    Toast.makeText(
                        requireContext(),
                        "Selected: ${data.size} images",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }
    }

    //upload files for new pets
    private fun uploadFiles() {
        if(imagesList.value.isNullOrEmpty()) {
            Toast.makeText(mContext, "Can't choose pictures", Toast.LENGTH_SHORT).show()
            return;
        }
        val files = mutableMapOf<String, RequestBody>()
        imagesList.value?.forEachIndexed { index, image ->
            val file = File(image.path)
            files["file" + index + "\" filename=\"" + file.name] = RequestBody.create(
                MediaType.parse(
                    imagesList.value?.get(index)?.mimeType?: ""
                ), file
            )
        }
        mApiService.uploadMultipleFiles(files).enqueue(object :
            Callback<ApiService.UploadResult> {
            override fun onResponse(
                call: Call<ApiService.UploadResult>?,
                response: Response<ApiService.UploadResult>
            ) {
                if (response.isSuccessful && response.body().code == 1) {
                    Toast.makeText(mContext, "Upload success", Toast.LENGTH_SHORT).show();
                    Log.i(
                        TAG,
                        "---------------------Upload success -----------------------"
                    );
                    Log.i(TAG, "The base address is:$API_BASE_URL");
                    Log.i(
                        TAG,
                        "The relative address of the picture is:" + listToString(
                            response.body().image_urls,
                            ','
                        )
                    );
                    Log.i(TAG, "---------------------END-----------------------");
                }
            }

            override fun onFailure(call: Call<ApiService.UploadResult>?, t: Throwable?) {
                Toast.makeText(mContext, "upload failed", Toast.LENGTH_SHORT).show();

            }
        });
    }
    fun listToString(list: List<String>?, separator: Char): String? {
        if (list == null) { return "" }
        val sb = StringBuilder()
        for (i in list.indices) {
            sb.append(list[i]).append(separator)
        }
        return sb.toString().substring(0, sb.toString().length - 1)
    }

}
