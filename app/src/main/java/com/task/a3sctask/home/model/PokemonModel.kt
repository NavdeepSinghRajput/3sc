package com.task.a3sctask.home.model

import com.google.gson.annotations.SerializedName
/**
 * @author Navdeep Singh
 * @since 09.09.2021
 */
/*
*   Model for getting List of Pokemon
*/
data class PokemonModel(
    @SerializedName("results")
    var results: ArrayList<ResultsModel>

)

data class ResultsModel(
    @SerializedName("name")
    var name: String,
    @SerializedName("url") var url: String,
    var sprites: String,
    var backgroundColor: Int
)


