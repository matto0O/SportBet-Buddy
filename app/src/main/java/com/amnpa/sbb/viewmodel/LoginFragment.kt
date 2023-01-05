package com.amnpa.sbb.viewmodel

import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.findNavController
import com.amnpa.sbb.MainActivity
import com.amnpa.sbb.R
import com.amnpa.sbb.model.League
import com.amnpa.sbb.model.Login
import com.amnpa.sbb.model.ParseJSON

class LoginFragment : Fragment() {

    private lateinit var textUsername: EditText
    private lateinit var textPassword: EditText
    private lateinit var buttonSubmit: Button
    private lateinit var buttonRegister: Button
    private lateinit var loading: ImageView
    private lateinit var fragmentContainerView: FragmentContainerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_login, container, false)

        textUsername = view.findViewById(R.id.editTextUsername)
        textPassword = view.findViewById(R.id.editTextPassword)
        buttonSubmit = view.findViewById(R.id.buttonSubmit)
        buttonRegister = view.findViewById(R.id.buttonRegister)

        buttonSubmit.setOnClickListener{
            if (textUsername.text.toString() == "" || textPassword.text.toString() == "")
                Toast.makeText(context, "Login and password can not be empty", Toast.LENGTH_SHORT).show()
            else
                ParseJSON.fetchAuthData(
                    textUsername.text.toString(),
                    textPassword.text.toString(),
                    ::triggerLoadingScreen,
                    ::dissolveLoadingScreen,
                    ::testAuthData)
        }

        buttonRegister.setOnClickListener{
            findNavController().navigate(
                LoginFragmentDirections
                    .actionLoginFragmentToRegisterFragment()
            )
        }

        return view
    }

//    override fun onStart() {
//        fragmentContainerView = requireActivity().findViewById(R.id.fragmentContainerView)
//        loading = requireActivity().findViewById(R.id.loadingScreen)
//        super.onStart()
//    }
//
//
//    override fun onStop() {
//        (loading.drawable as AnimationDrawable).stop()
//        fragmentContainerView.alpha = 1F
//        loading.alpha=0F
//        super.onStop()
//    }

    private fun triggerLoadingScreen(){
//        fragmentContainerView.alpha = 0.2F
//        loading.alpha=1F
//        (loading.drawable as AnimationDrawable).start()
    }

    private fun dissolveLoadingScreen(){
//        (loading.drawable as AnimationDrawable).stop()
//        fragmentContainerView.alpha = 1F
//        loading.alpha=0F
    }

    private fun testAuthData(data: Login?){
        requireActivity().runOnUiThread {
            println(data)
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            sharedPref?.edit()?.apply {
                if (data != null && data.userId != 0) {
                    putInt("user_id", data.userId)
                    putString("token", data.token)
                    putString("username", data.username)
                } else {
                    Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
                apply()
            }
            if (sharedPref != null) {
                if(sharedPref.getInt("user_id", -1) != -1)
                    requireActivity().finish()
            }
        }
    }

}