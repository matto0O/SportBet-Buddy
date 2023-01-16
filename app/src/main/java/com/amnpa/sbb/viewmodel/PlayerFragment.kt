package com.amnpa.sbb.viewmodel

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amnpa.sbb.MainActivity
import com.amnpa.sbb.R
import com.amnpa.sbb.model.StatAdapter
import com.amnpa.sbb.model.Stats

class PlayerFragment : Fragment() {
    private var editing: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val prefs = requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE)!!
        val userId = prefs.getInt("user_id", -1)

        Stats.fetchStats(userId)

        val view = inflater.inflate(R.layout.fragment_player, container, false)
        val statAdapter = StatAdapter(Stats.randomStats())
        val recyclerView = view.findViewById<RecyclerView>(R.id.statRecView)
        recyclerView.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = statAdapter

        val editNickname = view.findViewById<EditText>(R.id.editTextPlayerName)
        val textNickname = view.findViewById<TextView>(R.id.textViewPlayerName)
        val editButton = view.findViewById<ImageView>(R.id.imageViewEdit)
        val userLogin = view.findViewById<TextView>(R.id.textUserLogin)

        val logout = view.findViewById<Button>(R.id.buttonLogout)
        userLogin.text = userId.toString()

        textNickname.text = prefs
                            .getString("username", "name")
        editNickname.text = textNickname.editableText

        editButton.setOnClickListener {
            if (editing){
                textNickname.visibility = View.VISIBLE
                textNickname.isClickable = true
                textNickname.text = editNickname.text
                prefs.edit().apply {
                    putString("username",editNickname.text.toString())
                    apply()
                }
                editNickname.visibility = View.INVISIBLE
                editNickname.isClickable = false
            }
            else{
                editNickname.visibility = View.VISIBLE
                editNickname.isClickable = true
                textNickname.visibility = View.INVISIBLE
                textNickname.isClickable = false
            }
            editing = !editing
        }

        val logoutListener = DialogInterface.OnClickListener { dialog, which ->
            if(which == DialogInterface.BUTTON_POSITIVE){
                prefs.edit()?.apply {
                    putInt("user_id", -1)
                    putString("token", "")
                    putString("username", "")
                    apply()
                    // TODO post
                }
                val newApp = Intent(context, MainActivity::class.java)
                requireActivity().finish()
                startActivity(newApp)
            }
            else dialog.dismiss()
        }

        logout.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setMessage(getString(R.string.u_sure))
                .setPositiveButton(getString(R.string.yes), logoutListener)
                .setNegativeButton(getString(R.string.no), logoutListener)
                .show()
        }

        return view
    }
}