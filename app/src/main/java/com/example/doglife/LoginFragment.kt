package com.example.doglife

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.doglife.databinding.FragmentLoginBinding
import com.example.doglife.login.LoginView
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.Executors

const val SERVER_POST_URL = "http://192.168.1.15:5000"
class LoginFragment : Fragment() , LoginView {
    private lateinit var mContext: Context
    private lateinit var mBinding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = FragmentLoginBinding.inflate(inflater, container, false)
        mContext = mBinding.root.context
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestPermission()
        setUpListener()
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
//        activity?.let {  }
    }

    override fun login() {
        validateLoginData()
        val loginForm = JSONObject()
        try {
            loginForm.put("subject", "login")
            loginForm.put("email", mBinding.etLoginUserName.text)
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
        val service = Executors.newSingleThreadExecutor()
        service.submit {
            val client = OkHttpClient()

            try {
                val request = Request.Builder()
                    .url(postUrl)
                    .post(postBody)
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .build();
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        // Cancel the post on failure.
                        call.cancel();
                        // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                        runOnUiThread {
                            Toast.makeText(
                                mContext,
                                "Failed to Connect to Server. Please Try Again",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        runOnUiThread {
                            val loginResponse = response.body()?.string()?.trim()
                            Log.d("Success", loginResponse);
                            Toast.makeText(
                                mContext,
                                "Response from the server $loginResponse",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
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

    fun Fragment?.runOnUiThread(action: () -> Unit) {
        this ?: return
        if (!isAdded) return // Fragment not attached to an Activity
        activity?.runOnUiThread(action)
    }
}