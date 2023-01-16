package com.amnpa.sbb.viewmodel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.findNavController
import com.amnpa.sbb.R
import com.amnpa.sbb.model.ParseJSON
import com.amnpa.sbb.model.Register

class RegisterFragment : Fragment() {

    private lateinit var textUsername: EditText
    private lateinit var textPassword: EditText
    private lateinit var textPasswordRepeat: EditText
    private lateinit var buttonSubmit: Button
    private lateinit var buttonLogin: Button
    private lateinit var loading: ImageView
    private lateinit var fragmentContainerView: FragmentContainerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_register, container, false)

        textUsername = view.findViewById(R.id.editTextUsername)
        textPassword = view.findViewById(R.id.editTextPassword)
        textPasswordRepeat = view.findViewById(R.id.editTextPasswordRepeat)
        buttonSubmit = view.findViewById(R.id.buttonSubmit)
        buttonLogin = view.findViewById(R.id.buttonLogin)

        buttonSubmit.setOnClickListener{
            if (textUsername.text.toString() == ""
                || textPassword.text.toString() == ""
                || (textPassword.text != textPasswordRepeat.text))
                Toast.makeText(context, getString(R.string.not_created), Toast.LENGTH_SHORT).show()
            else
                ParseJSON.fetchRegistration(
                    textUsername.text.toString(),
                    textPassword.text.toString(),
                    ::triggerLoadingScreen,
                    ::dissolveLoadingScreen,
                    ::handleRegistration,
                    context
                )
        }

        buttonLogin.setOnClickListener{
            findNavController().navigate(
                RegisterFragmentDirections
                    .actionRegisterFragmentToLoginFragment()
            )
        }

        return view
    }

//    override fun onStart() {
//        fragmentContainerView = requireActivity().findViewById(R.id.fragmentContainerView)
//        loading = requireActivity().findViewById(R.id.loadingScreen)
//        super.onStart()
//    }


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

    private fun handleRegistration(data: Register?){
        requireActivity().runOnUiThread {
            if(data != null && data.created){
                Toast.makeText(context, getString(R.string.created), Toast.LENGTH_SHORT).show()
                findNavController().navigate(
                    RegisterFragmentDirections
                        .actionRegisterFragmentToLoginFragment()
                )
            }
            else {
                Toast.makeText(context, R.string.not_created, Toast.LENGTH_SHORT).show()
            }
        }
    }

}