package io.devnido.pokedex.data.remote.models

import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
    @SerializedName("name") var name:String,
    @SerializedName("order") var order:Int,
    @SerializedName("sprites") var sprites: Sprites,
    @SerializedName("types") var types:List<Type>
)

data class Sprites(
    @SerializedName("front_default") var frontDefault: String,
    @SerializedName("back_default") var backDefault: String,
    @SerializedName("front_shiny") var frontShiny: String,
    @SerializedName("back_shiny") var backShiny: String
)

data class Type(
     @SerializedName("type") var type: TypeItem
)

data class TypeItem(
    @SerializedName("name") var name:String
)


