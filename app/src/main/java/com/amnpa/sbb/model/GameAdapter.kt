package com.amnpa.sbb.model

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.amnpa.sbb.R
import kotlinx.android.synthetic.main.game_card.view.*

class GameAdapter(
    private val games: MutableList<Game>,
    private var competitions: HashMap<Int, Competition>,
    private val userId: Int
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

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val curResult = games[position]

        holder.itemView.apply {
            textViewGame.text = curResult.team1 + " - " + curResult.team2
            val competition = competitions[curResult.competitionId]
            textViewGameCompetition.text = competition!!.name
            textViewGameDatetime.text = curResult.date
            betDrawButton.text = "X\n${curResult.drawOdds}"
            betHostButton.text = "1\n${curResult.team1Odds}"
            betVisitorButton.text = "2\n${curResult.team2Odds}"
            try {
                imageViewFlag.setImageDrawable(
                    when (competition.country.lowercase()) {
                        "spain" -> resources.getDrawable(R.drawable.spain)
                        "netherlands" -> resources.getDrawable(R.drawable.netherlands)
                        "poland" -> resources.getDrawable(R.drawable.poland)
                        "germany" -> resources.getDrawable(R.drawable.germany)
                        "france" -> resources.getDrawable(R.drawable.france)
                        "italy" -> resources.getDrawable(R.drawable.italy)
                        "england" -> resources.getDrawable(R.drawable.england)
                        "denmark" -> resources.getDrawable(R.drawable.denmark)
                        "portugal" -> resources.getDrawable(R.drawable.portugal)
                        "nationalteams" -> resources.getDrawable(R.drawable.nationalteams)
                        else -> resources.getDrawable(R.drawable.international)
                    }
                )
            } catch(_:java.lang.NullPointerException)
            {imageViewFlag.setImageDrawable(resources.getDrawable(R.drawable.international))}

            betDrawButton.setOnClickListener {
                postBet(curResult, 0)
            }
            betHostButton.setOnClickListener {
                postBet(curResult, 1)
            }
            betVisitorButton.setOnClickListener {
                postBet(curResult, 2)
            }
        }
    }

    override fun getItemCount(): Int {
        return games.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reloadData(newData: List<Game>, competitionsData: HashMap<Int, Competition>){
        competitions = competitionsData
        games.clear()
        games.addAll(newData)
        notifyDataSetChanged()
    }

    private fun postBet(game: Game, option: Int){
        ParseJSON.fetchBetPost(
            userId, game, option
        )
    }
}
