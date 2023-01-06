package com.amnpa.sbb.model

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amnpa.sbb.R
import kotlinx.android.synthetic.main.game_card.view.*

class GameAdapter(
    private val games: MutableList<Game>
) : RecyclerView.Adapter<GameAdapter.ResultViewHolder>() {

    class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.game_card,
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val curResult = games[position]

        holder.itemView.apply {
            textViewGame.text = curResult.team1 + " - " + curResult.team2
            textViewGameCompetition.text = curResult.competitionId.toString()
            textViewGameDatetime.text = curResult.date
            betDrawButton.text = "X\n${curResult.drawOdds}"
            betHostButton.text = "1\n${curResult.team1Odds}"
            betVisitorButton.text = "2\n${curResult.team2Odds}"
        }
    }

    override fun getItemCount(): Int {
        return games.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reloadData(newData: List<Game>){
        games.clear()
        games.addAll(newData)
        notifyDataSetChanged()
    }
}
