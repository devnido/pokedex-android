package io.devnido.pokedex.data.remote.models

import com.google.gson.annotations.SerializedName

data class PokemonListResponse(
    @SerializedName("results") var results:List<Item>)

data class Item(
    @SerializedName("name") var name:String,
    @SerializedName("url") var url:String)