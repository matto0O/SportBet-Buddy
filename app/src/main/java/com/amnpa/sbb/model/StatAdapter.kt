package com.amnpa.sbb.model

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amnpa.sbb.R
import kotlinx.android.synthetic.main.bet_card.view.*
import kotlinx.android.synthetic.main.bet_card.view.textStatDescription
import kotlinx.android.synthetic.main.stat_card.view.*

class StatAdapter(private val stats: List<Pair<String,Any>>)
    : RecyclerView.Adapter<StatAdapter.ResultViewHolder>() {

    class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.stat_card,
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val stat = stats[position]

        holder.itemView.apply {
            textStatDescription.text = stat.first
            textStatValue.text = stat.second.toString()
        }
    }

    override fun getItemCount(): Int {
        return stats.size
    }

}
