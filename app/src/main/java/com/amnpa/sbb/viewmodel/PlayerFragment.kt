package com.amnpa.sbb.viewmodel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amnpa.sbb.R
import com.amnpa.sbb.model.StatAdapter
import com.amnpa.sbb.model.Stats

class PlayerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Stats.fetchStats()
        val view = inflater.inflate(R.layout.fragment_player, container, false)
        val statAdapter = StatAdapter(Stats.randomStats())
        val recyclerView = view.findViewById<RecyclerView>(R.id.statRecView)
        recyclerView.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = statAdapter
        return view
    }
}