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
            textCompetition.text = competitions[curResult.game.competitionId]!!.name
            textOdds.text = curResult.odds.toString()
            textOption.text = curResult.option.toString()
            textBetDate.text = curResult.game.date
            resultImg.setImageDrawable(
                when(curResult.game.result){
                    curResult.option -> resources.getDrawable(R.drawable.ic_win)
                    -1 -> resources.getDrawable(R.drawable.ic_unresolved)
                    else -> resources.getDrawable(R.drawable.ic_loss)
                }
            )
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
