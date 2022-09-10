package com.task.a3sctask.home.model

import com.google.gson.annotations.SerializedName

data class PokemonModel(
    @SerializedName("results")
    var results: ArrayList<ResultsModel>

)

data class ResultsModel(
    @SerializedName("name")
    var name: String, @SerializedName("url") var url: String
)


