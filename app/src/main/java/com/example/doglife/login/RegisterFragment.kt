package com.example.doglife

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.doglife.adopt.DogAdoptionFragment
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class RegisterFragment : Fragment() {

    companion object {
        private val TAG = RegisterFragment::class.java.name
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val usernameView = view.findViewById<Button>(R.id.registerButton)
        usernameView.setOnClickListener {
            register(view)
        }
    }

    private fun register(v: View) {
        val usernameView = v.findViewById<EditText>(R.id.username)
        val firstNameView = v.findViewById<EditText>(R.id.firstName)
        val lastNameView = v.findViewById<EditText>(R.id.lastName)
        val passwordView = v.findViewById<EditText>(R.id.password)
        val username = usernameView.text.toString().trim { it <= ' ' }
        val firstName = firstNameView.text.toString().trim { it <= ' ' }
        val lastName = lastNameView.text.toString().trim { it <= ' ' }
        val password = passwordView.text.toString().trim { it <= ' ' }
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Something is wrong. Please check your inputs.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            val registrationForm = JSONObject()
            try {
                registrationForm.put("subject", "register")
                registrationForm.put("firstname", firstName)
                registrationForm.put("lastname", lastName)
                registrationForm.put("username", username)
                registrationForm.put("password", password)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            val body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                registrationForm.toString()
            )
            postRequest(SERVER_POST_URL, body, v)
        }
    }

    private fun postRequest(postUrl: String, postBody: RequestBody, v: View) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(postUrl)
            .post(postBody)
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                call.cancel()
                requireActivity().runOnUiThread {
                    val responseText =
                        v.findViewById<TextView>(R.id.responseTextRegister)
                    responseText.text = "Failed to Connect to Server. Please Try Again."
                }
            }

            override fun onResponse(
                call: Call,
                response: Response
            ) {
                val responseTextRegister =
                    v.findViewById<TextView>(R.id.responseTextRegister)
                try {
                    val responseString = response.body()!!.string().trim { it <= ' ' }
                    requireActivity().runOnUiThread {
                        when (responseString) {
                            "success" -> {
                                responseTextRegister.text = "Registration completed successfully."
                            }
                            "username" -> {
                                responseTextRegister.text =
                                    "Username already exists. Please chose another username."
                            }
                            else -> {
                                responseTextRegister.text =
                                    "Something went wrong. Please try again later."
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }
}