package com.task.a3sctask.home.viewModel

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.task.a3sctask.home.model.PokemonModel
import com.task.a3sctask.network.IRestApi
import com.task.a3sctask.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.ThreadLocalRandom
/**
 * @author Navdeep Singh
 * @since 09.09.2021
 */
class HomeActivityViewModel : ViewModel() {
    private var pokemonModelLiveData: MutableLiveData<PokemonModel> = MutableLiveData()

    fun getPokemonListObserver(): MutableLiveData<PokemonModel> {
        return pokemonModelLiveData
    }

    // caliing api
    fun gettingPokemonData(offset: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            val retrofitInstance = RetrofitInstance.getClient()?.create(IRestApi::class.java)
            val pokemonList = retrofitInstance?.gettingPokemonList(offset, "20")
            pokemonList!!.results.map {
                val pokemonId = it.url.split("pokemon/").toTypedArray().get(1).split("/").get(0)
                val pokemonDetail = retrofitInstance.gettingPokemonDetail(pokemonId)
                it.sprites = pokemonDetail.sprites.back_default
                it.backgroundColor = randomColorSelector()
            }
            pokemonModelLiveData.postValue(pokemonList)
        }
    }

    //designing different background
    private fun randomColorSelector(): Int {
        var color= when (ThreadLocalRandom.current().nextInt(0, 4)) {
            0 -> Color.parseColor("#4ad1b1")
            1 -> Color.parseColor("#fc6d6d")
            2 -> Color.parseColor("#ffd86e")
            else -> {
                Color.parseColor("#77bffd")
            }
        }
        return color
    }

}