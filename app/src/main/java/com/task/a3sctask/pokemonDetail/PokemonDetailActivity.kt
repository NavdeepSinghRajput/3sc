package com.task.a3sctask.pokemonDetail

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.task.a3sctask.BaseActivity
import com.task.a3sctask.R
import com.task.a3sctask.home.model.PokemonDetailModel
import com.task.a3sctask.home.model.PokemonModel
import com.task.a3sctask.home.viewModel.HomeActivityViewModel
import com.task.a3sctask.home.viewModel.PokemonDetailActivityViewModel

class PokemonDetailActivity : BaseActivity() {

    lateinit var pokemonId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)
        if (intent.hasExtra(POKEMON_ID)) {
            pokemonId = intent.getStringExtra(POKEMON_ID).toString()
        }
        initViewModel()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.hasExtra(POKEMON_ID)) {
            pokemonId = intent.getStringExtra(POKEMON_ID).toString()
        }
    }
    private fun initViewModel() {
        val viewModel = ViewModelProvider(this)[PokemonDetailActivityViewModel::class.java]
        viewModel.getPokemonListObserver().observe(this, Observer<PokemonDetailModel> {
            if (it != null) {
                //here you get api response
            }
        })
        viewModel.gettingPokemonDetailData(id = pokemonId)
    }
    companion object{
        private const val POKEMON_ID = "pokemonId"

        fun launchPokemonDetail(activityContext: Context,pokemonId :String){
            activityContext.startActivity(Intent(activityContext, PokemonDetailActivity::class.java).setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK).putExtra(POKEMON_ID,pokemonId))

        }
    }
}