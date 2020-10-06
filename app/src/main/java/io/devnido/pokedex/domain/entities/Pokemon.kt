package io.devnido.pokedex.domain.entities

data class Pokemon(
    val number: Int,
    val name: String,
    val images: Images,
    val types: Types
)

data class Images(
    val defaultFront:String,
    val defaultBack:String = "",
    val shinyFront:String = "",
    val shinyBack:String = ""
)

data class Types(
    val first:String,
    val second:String = ""
)



