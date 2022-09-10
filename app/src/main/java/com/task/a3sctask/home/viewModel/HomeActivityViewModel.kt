package com.task.a3sctask.home.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.task.a3sctask.home.model.PokemonModel
import com.task.a3sctask.network.IRestApi
import com.task.a3sctask.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeActivityViewModel: ViewModel() {
    private var pokemonModelLiveData: MutableLiveData<PokemonModel> = MutableLiveData()

    fun getPokemonListObserver():MutableLiveData<PokemonModel>{
        return pokemonModelLiveData
    }

    fun gettingPokemonData(){
        GlobalScope.launch(Dispatchers.IO) {
        val retrofitInstance = RetrofitInstance.getClient()?.create(IRestApi::class.java)
           val pokemonList =  retrofitInstance?.gettingPokemonList("10","10")
            pokemonModelLiveData.postValue(pokemonList)
        }
    }
}