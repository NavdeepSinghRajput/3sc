package com.task.a3sctask.pokemonDetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.task.a3sctask.R
import com.task.a3sctask.home.model.StatsModel
/**
 * @author Navdeep Singh
 * @since 09.09.2021
 */
class PokemonStatsAdapter() :
    RecyclerView.Adapter<PokemonStatsAdapter.MyViewHolder>() {
    private var statsList = ArrayList<StatsModel>()

    //Viewholder of Card
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val statsTitle: TextView = view.findViewById(R.id.statsTitle)
        private val statsCount: TextView = view.findViewById(R.id.statsCount)
        val statsProgress: ProgressBar = view.findViewById(R.id.statsProgress)

        fun bind(stats: StatsModel) {
            statsTitle.text = stats.stat.name
            statsCount.text = stats.base_stat.toString()
            statsProgress.progress = stats.base_stat
        }

    }

    // setting data to adapter
    fun setPokemonData(statsList: ArrayList<StatsModel>) {
        this.statsList.addAll(statsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.stats_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(stats = statsList[position])
    }



    override fun getItemCount(): Int {
        return statsList.size
    }

}