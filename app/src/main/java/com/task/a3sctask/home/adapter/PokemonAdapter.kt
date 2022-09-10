package com.task.a3sctask.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.task.a3sctask.R
import com.task.a3sctask.home.model.ResultsModel

class PokemonAdapter( private val pokemonAdapterClickHandler:PokemonAdapterClickHandler ) : RecyclerView.Adapter<PokemonAdapter.MyViewHolder>() {
    private var pokemonList = ArrayList<ResultsModel>()


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val pokemonName = view.findViewById<TextView>(R.id.pokemonTitleName)
        val pokemonCard: CardView = view.findViewById(R.id.pokemonCard)

        fun bind(pokemon: ResultsModel) {
            pokemonName.text = pokemon.name
        }

    }

    interface PokemonAdapterClickHandler {

        fun pokemonItemClick(resultsModel: ResultsModel)
    }

    fun setPokemonData(pokemonList: ArrayList<ResultsModel>) {
        this.pokemonList.addAll(pokemonList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.pokemon_list_item, parent,false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(pokemon = pokemonList.get(position))
        holder.pokemonCard.setOnClickListener {
            pokemonAdapterClickHandler.pokemonItemClick(pokemonList[position])
        }
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }
}