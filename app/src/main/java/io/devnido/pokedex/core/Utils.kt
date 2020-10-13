package io.devnido.pokedex.core

import android.content.Context
import io.devnido.pokedex.R

class Utils{

    companion object{
        fun getColorByPokemonType(context: Context?,type:String):Int?{

            val color = when(type){
                "normal" -> R.color.pokemonTypeNormal
                "fighting" -> R.color.pokemonTypeFighting
                "flying" -> R.color.pokemonTypeFlying
                "poison" -> R.color.pokemonTypePoison
                "ground" -> R.color.pokemonTypeGround
                "rock" -> R.color.pokemonTypeRock
                "bug" -> R.color.pokemonTypeBug
                "ghost" -> R.color.pokemonTypeGhost
                "steel" -> R.color.pokemonTypeSteel
                "fire" -> R.color.pokemonTypeFire
                "water" -> R.color.pokemonTypeWater
                "grass" -> R.color.pokemonTypeGrass
                "electric" -> R.color.pokemonTypeElectric
                "psychic" -> R.color.pokemonTypePsychic
                "ice" -> R.color.pokemonTypeIce
                "dragon" -> R.color.pokemonTypeDragon
                "dark" -> R.color.pokemonTypeDark
                "fairy" -> R.color.pokemonTypeFairy
                "unknown" -> R.color.pokemonTypeNormal
                "shadow" -> R.color.pokemonTypeDark
                else -> R.color.pokemonTypeNormal
            }

            return context?.getColor(color)
        }
    }
}

