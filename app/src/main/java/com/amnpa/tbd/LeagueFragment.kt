package com.amnpa.tbd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LeagueFragment : Fragment() {

    private lateinit var textEnterCode:EditText
    private lateinit var buttonJoin:Button
    private lateinit var buttonCreate:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_league, container, false)

        textEnterCode = view.findViewById(R.id.editTextEnterCode)
        buttonJoin = view.findViewById(R.id.buttonJoinLeague)
        buttonCreate = view.findViewById(R.id.buttonCreateLeague)

        buttonJoin.setOnClickListener{
            if (textEnterCode.text.toString() == "")
                Toast.makeText(context, "Invalid league code", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context, "TODO: joined a league of code: "
                        +textEnterCode.text.toString(), Toast.LENGTH_SHORT).show()
        }

        return view
    }
}