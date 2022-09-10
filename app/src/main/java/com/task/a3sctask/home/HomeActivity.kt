package com.task.a3sctask.home

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.task.a3sctask.BaseActivity
import com.task.a3sctask.R
import com.task.a3sctask.home.adapter.PokemonAdapter
import com.task.a3sctask.home.model.PokemonModel
import com.task.a3sctask.home.model.ResultsModel
import com.task.a3sctask.home.viewModel.HomeActivityViewModel
import com.task.a3sctask.home.viewModel.PokemonDetailActivityViewModel
import com.task.a3sctask.pokemonDetail.PokemonDetailActivity


class HomeActivity : BaseActivity() {

    private lateinit var pokemonRV: RecyclerView
    private lateinit var pokemonAdapter: PokemonAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        inIt()
        initViewModel()
    }

    private fun inIt() {
        pokemonRV = findViewById(R.id.pokemonRV)

        pokemonAdapter = PokemonAdapter(pokemonAdapterClickHandler = object :
            PokemonAdapter.PokemonAdapterClickHandler {
            override fun pokemonItemClick(resultsModel: ResultsModel) {
                var pokemonUrl = resultsModel.url
                val pokemonId = pokemonUrl.split("pokemon/").toTypedArray().get(1).split("/").get(0)
                PokemonDetailActivity.launchPokemonDetail(activityContext = baseContext , pokemonId = pokemonId)
            }

        })

        pokemonRV.adapter = pokemonAdapter
    }

    private fun initViewModel() {
        val viewModel = ViewModelProvider(this)[HomeActivityViewModel::class.java]
        viewModel.getPokemonListObserver().observe(this, Observer<PokemonModel> {
            if (it != null) {
                pokemonAdapter.setPokemonData(it.results)
            }
        })
        viewModel.gettingPokemonData()
    }
}