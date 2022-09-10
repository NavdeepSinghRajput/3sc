package com.task.a3sctask.network

import com.task.a3sctask.home.model.PokemonDetailModel
import com.task.a3sctask.home.model.PokemonModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IRestApi {

    // getting list of pokemon
    @GET("pokemon/")
    suspend fun gettingPokemonList(
        @Query("offset") offset: Int?,
        @Query("limit") limit: String?,
    ): PokemonModel

    // getting pokemon detail
    @GET("pokemon/{id}")
    suspend fun gettingPokemonDetail(
        @Path("id") id: String?
    ): PokemonDetailModel

}