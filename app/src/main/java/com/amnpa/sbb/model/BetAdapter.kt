package com.amnpa.sbb.model

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amnpa.sbb.R
import kotlinx.android.synthetic.main.bet_card.view.*

class BetAdapter(
    private val games: MutableList<Bet>,
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val curResult = games[position]

        holder.itemView.apply {
            textStatDescription.text = curResult.game.team1 + " vs " + curResult.game.team2
//            setOnClickListener {
//                show(curResult.gameId.toString(), curResult.gameId.toString())
//            }
        }
    }

    override fun getItemCount(): Int {
        return games.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reloadData(newData: List<Bet>){
        games.clear()
        games.addAll(newData)
        notifyDataSetChanged()
    }
}
