package com.example.doglife

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.doglife.databinding.ActivityMainBinding
import com.example.doglife.databinding.FragmentLoginBinding
import com.example.doglife.login.LoginView
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


const val SERVER_POST_URL = "http://192.168.1.15:3036"
class LoginFragment : Fragment() , LoginView {
    private lateinit var mContext: Context
    private lateinit var mBinding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
        setUpListener()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    private fun setUpListener() {
        mBinding.tvLoginButton.setOnClickListener {
            login()
        }
        mBinding.tvRegisterButton.setOnClickListener {
//            val intent = Intent(this, FragmentRegister::class.java)
//            startActivity(intent)
        }
    }

    private fun validateLoginData() {

    }
    private fun requestPermission() {
        activity?.let { ActivityCompat.requestPermissions(it, arrayOf(Manifest.permission.INTERNET), 0) }
    }

    override fun login() {
        validateLoginData()
        val loginForm = JSONObject()
        try {
            loginForm.put("subject", "login")
            loginForm.put("username", mBinding.etLoginUserName.text)
            loginForm.put("password", mBinding.etLoginUserPassword.text)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val body = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            loginForm.toString()
        )

        postRequest(SERVER_POST_URL, body)
    }

    private fun postRequest(postUrl: String, postBody: RequestBody) {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(postUrl)
            .post(postBody)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build();

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Cancel the post on failure.
                call.cancel();
                Log.d("FAIL", e.message);

                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                activity?.runOnUiThread {
                    Toast.makeText(mContext, "Failed to Connect to Server. Please Try Again", Toast.LENGTH_LONG ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                activity?.runOnUiThread {
                    val loginResponse = response.body()?.string()?.trim()
                    Toast.makeText(mContext, "Response from the server $loginResponse", Toast.LENGTH_LONG ).show()
                }
            }
        })
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 2) { // Login
            Toast.makeText(
                requireContext(),
                "Successful Login.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(
                requireContext(),
                "Invalid or no data entered. Please try again.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun register() {
        TODO("Not yet implemented")
    }


}