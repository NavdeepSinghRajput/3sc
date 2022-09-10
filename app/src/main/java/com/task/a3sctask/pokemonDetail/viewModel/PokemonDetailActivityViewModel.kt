package com.task.a3sctask.home.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.task.a3sctask.home.model.PokemonDetailModel
import com.task.a3sctask.network.IRestApi
import com.task.a3sctask.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PokemonDetailActivityViewModel: ViewModel() {

    private var pokemonModelLiveData: MutableLiveData<PokemonDetailModel> = MutableLiveData()

    fun getPokemonListObserver():MutableLiveData<PokemonDetailModel>{
        return pokemonModelLiveData
    }

    fun gettingPokemonDetailData(id:String){
        GlobalScope.launch(Dispatchers.IO) {
        val retrofitInstance = RetrofitInstance.getClient()?.create(IRestApi::class.java)
           val pokemonDetail =  retrofitInstance?.gettingPokemonDetail(id)
            pokemonModelLiveData.postValue(pokemonDetail)
        }
    }
}