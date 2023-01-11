package com.amnpa.sbb.model

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amnpa.sbb.R
import com.nex3z.togglebuttongroup.button.LabelToggle
import kotlinx.android.synthetic.main.bet_card.view.*
import kotlinx.android.synthetic.main.game_card.view.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap

class BetAdapter(
    private val games: MutableList<Bet>,
    private var competitions: HashMap<Int, Competition>
) : RecyclerView.Adapter<BetAdapter.ResultViewHolder>() {

    class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.bet_card,
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val curResult = games[position]

        holder.itemView.apply {
            textGame.text = curResult.game.team1 + " - " + curResult.game.team2
            val competition = competitions[curResult.game.competitionId]!!
            textCompetition.text = competition.name
            textOdds.text = curResult.odds.toString()
            textOption.text = when (curResult.option){
                0 -> "Draw"
                1 -> "Host"
                else -> "Visitor"
            }
            val date = curResult.game.date
            val format = DateTimeFormatter.ofPattern("E, dd MMM yyyy HH:mm:ss z")
            val newFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
            val reprDate = ZonedDateTime
                .of(LocalDateTime.parse(date, format), ZoneId.of("Poland"))
            textBetDate.text = reprDate.format(newFormat)
            resultImg.setImageDrawable(
                when(curResult.game.result){
                    curResult.option -> resources.getDrawable(R.drawable.ic_win)
                    -1 -> resources.getDrawable(R.drawable.ic_unresolved)
                    else -> resources.getDrawable(R.drawable.ic_loss)
                }
            )

            try {
                imageViewFlagBet.setImageDrawable(
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
        }
    }

    override fun getItemCount(): Int {
        return games.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reloadData(newData: List<Bet>, competitionsData: HashMap<Int, Competition>){
        competitions = competitionsData
        games.clear()
        games.addAll(newData)
        notifyDataSetChanged()
    }
}
