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

            when(position){
                0 -> {
                    cardViewPlayer.setCardBackgroundColor(resources.getColor(R.color.gold))
                    textPlayerPlace.setTextColor(resources.getColor(R.color.black))
                    textNickname.setTextColor(resources.getColor(R.color.black))
                    textPoints.setTextColor(resources.getColor(R.color.black))
                }
                1 -> {
                    cardViewPlayer.setCardBackgroundColor(resources.getColor(R.color.silver))
                    textPlayerPlace.setTextColor(resources.getColor(R.color.black))
                    textNickname.setTextColor(resources.getColor(R.color.black))
                    textPoints.setTextColor(resources.getColor(R.color.black))
                }
                2 -> {
                    cardViewPlayer.setCardBackgroundColor(resources.getColor(R.color.bronze))
                    textPlayerPlace.setTextColor(resources.getColor(R.color.white))
                    textNickname.setTextColor(resources.getColor(R.color.white))
                    textPoints.setTextColor(resources.getColor(R.color.white))
                }
                else -> {
                    textPlayerPlace.setTextColor(resources.getColor(R.color.black))
                    textNickname.setTextColor(resources.getColor(R.color.black))
                    textPoints.setTextColor(resources.getColor(R.color.black))
                }
            }
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
