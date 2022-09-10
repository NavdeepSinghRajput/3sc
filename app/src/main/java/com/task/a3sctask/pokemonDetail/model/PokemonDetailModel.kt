package com.task.a3sctask.home.model

import com.google.gson.annotations.SerializedName

data class PokemonDetailModel(
    @SerializedName("abilities")
    var abilities: ArrayList<AbilitiesModel>,
    @SerializedName("base_experience") var base_experience: Int,
    @SerializedName("height") var height: Int,
    @SerializedName("weight") var weight: Int,
    @SerializedName("moves") var moves: List<MovesModel>,
    @SerializedName("name") var name: String,
    @SerializedName("sprites") var sprites: SpritesModel

)

data class AbilitiesModel(
    @SerializedName("ability")
    var ability: AbilityModel
)

data class AbilityModel(
    @SerializedName("name")
    var name: String, @SerializedName("url") var url: String
)

data class MovesModel(
    @SerializedName("move")
    var move: MoveModel
)

data class MoveModel(
    @SerializedName("name")
    var name: String, @SerializedName("url") var url: String
)

data class SpritesModel(
    @SerializedName("back_default")
    var back_default: String
)


