package com.task.a3sctask.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.task.a3sctask.R
import com.task.a3sctask.home.model.ResultsModel

/**
 * @author Navdeep Singh
 * @since 09.09.2021
 */
class PokemonAdapter(private val pokemonAdapterClickHandler: PokemonAdapterClickHandler) :
    RecyclerView.Adapter<PokemonAdapter.MyViewHolder>() {
    private var pokemonList = ArrayList<ResultsModel>()
    private var isScrollingEnable = true;

    //Viewholder of Card
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val pokemonName = view.findViewById<TextView>(R.id.pokemonTitleName)
        val pokemonCard: ConstraintLayout = view.findViewById(R.id.pokemonCard)
        val arrowAhead: ImageView = view.findViewById(R.id.arrowAhead)
        val viewDetails: TextView = view.findViewById(R.id.viewDetails)

        fun bind(pokemon: ResultsModel) {
            pokemonName.text = pokemon.name
            pokemonCard.setBackgroundColor(pokemon.backgroundColor)
            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)

            Glide.with(itemView.context).load(pokemon.sprites).apply(options).into(arrowAhead)

        }

    }

    //Interface for handling click
    interface PokemonAdapterClickHandler {

        fun pokemonItemClick(resultsModel: ResultsModel)

        fun refresh()
    }

    // setting data to adapter
    fun setPokemonData(pokemonList: ArrayList<ResultsModel>) {
        this.pokemonList.clear()
        isScrollingEnable = true
        this.pokemonList.addAll(pokemonList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.pokemon_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(pokemon = pokemonList.get(position))
        holder.viewDetails.setOnClickListener {
            pokemonAdapterClickHandler.pokemonItemClick(pokemonList[position])
        }
        checkingCurrentItemLoading(position)
    }

    //Checking for getting next list
    private fun checkingCurrentItemLoading(position: Int) {
        if (position == pokemonList.size - 3) {
            if (isScrollingEnable) {
                pokemonAdapterClickHandler.refresh()
            }
        }
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    // setting search data to adapter
    fun settingSearchData(pokemonSearchList: ArrayList<ResultsModel>) {
        pokemonList.clear()
        pokemonList.addAll(pokemonSearchList)
        isScrollingEnable = false
        notifyDataSetChanged()
    }
}