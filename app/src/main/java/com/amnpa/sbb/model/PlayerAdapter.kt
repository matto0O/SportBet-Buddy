package com.amnpa.sbb.model

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amnpa.sbb.R
import kotlinx.android.synthetic.main.game_card.view.*
import kotlinx.android.synthetic.main.player_card.view.*

class PlayerAdapter(
    private val players: MutableList<Player>
) : RecyclerView.Adapter<PlayerAdapter.ResultViewHolder>() {

    class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.player_card,
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val curResult = players[position]

        holder.itemView.apply {
            textPlayerPlace.text = "${position+1}."
            textNickname.text = curResult.name
            textPoints.text = position.toString()  //TODO points
        }
    }

    override fun getItemCount(): Int {
        return players.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reloadData(newData: List<Player>){
        players.clear()
        players.addAll(newData)
        notifyDataSetChanged()
    }
}
