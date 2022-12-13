package com.amnpa.tbd

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_game.view.*

class BetAdapter(
    private val games: MutableList<NewBet>,
    private val show: (String, String) -> Unit
) : RecyclerView.Adapter<BetAdapter.ResultViewHolder>() {

    class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_game,
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val curResult = games[position]

//        holder.itemView.apply {
//            gamesText.text = curResult. + " vs " + curResult.team2
//            when(curResult.wasCorrect){
//                true -> resultImg.setImageResource(R.drawable.ic_baseline_check_circle_24)
//                false -> resultImg.setImageResource(R.drawable.ic_baseline_cancel_24)
//            }
//            if(curResult.isUpcoming) {
//                resultImg.visibility = View.GONE
//                setOnClickListener {
//                    show(curResult.team1, curResult.team2)
//                }
//            }
//            else{
//                resultImg.visibility = View.VISIBLE
//            }
//
//        }
    }

    override fun getItemCount(): Int {
        return games.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun reloadData(newData: List<NewBet>){
        games.clear()
        games.addAll(newData)
        notifyDataSetChanged()
    }
}
