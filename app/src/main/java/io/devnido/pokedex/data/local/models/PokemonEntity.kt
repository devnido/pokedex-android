package io.devnido.pokedex.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class PokemonEntity(
    @PrimaryKey
    val id:Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "order")
    val order: Int,
    @ColumnInfo(name = "base_experience")
    val baseExperience: Int?,
    @ColumnInfo(name = "weight")
    val weight: Float?,
    @ColumnInfo(name = "height")
    val height: Float?,
    @ColumnInfo(name = "img_sprite_front")
    val imgSpriteFront: String,
    @ColumnInfo(name = "img_sprite_back")
    val imgSpriteBack: String,
    @ColumnInfo(name = "img_sprite_shiny_front")
    val imgSpriteShinyFront: String,
    @ColumnInfo(name = "img_sprite_shiny_back")
    val imgSpriteShinyBack: String,
    @ColumnInfo(name = "img_large")
    val imgLarge: String,
    @ColumnInfo(name = "first_type")
    val firstType: String,
    @ColumnInfo(name = "second_type")
    val secondType: String?,
    @ColumnInfo(name = "abilities")
    val abilities: String?,
    @ColumnInfo(name = "stats")
    val stats: String?

)