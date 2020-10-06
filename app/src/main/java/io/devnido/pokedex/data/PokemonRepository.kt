package io.devnido.pokedex.data

import io.devnido.pokedex.data.remote.PokeApiService
import io.devnido.pokedex.data.remote.RestClient
import io.devnido.pokedex.data.remote.api.PokeApiRequests
import io.devnido.pokedex.data.remote.models.PokemonListResponse
import io.devnido.pokedex.domain.entities.Images
import io.devnido.pokedex.domain.entities.Pokemon
import io.devnido.pokedex.domain.entities.Types
import kotlinx.coroutines.flow.Flow

class PokemonRepository {

    private val pokeApiService: PokeApiService = RestClient.build()
    private val pokeApiRequests: PokeApiRequests = PokeApiRequests(pokeApiService)

    val ITEMS_PER_PAGE = 10

    suspend fun getPokemonList():PokemonListResponse?{
       val result = pokeApiRequests.getPokemons(10,0);

        return result
    }

    fun getPokemonListFake(): List<Pokemon> = listOf(
        Pokemon(
            1,
            "bulbasaur",
            Images("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"),
            Types("grass","poison")
        ),
        Pokemon(
            2,
            "ivysaur",
            Images("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png"),
            Types("grass","poison")
        ),
        Pokemon(
            3,
            "venusaur",
            Images("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png"),
            Types("grass","poison")
        ),
        Pokemon(
            4,
            "charmander",
            Images("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png"),
            Types("fire")
        ),
        Pokemon(
            5,
            "charmeleon",
            Images("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/5.png"),
            Types("fire")
        ),
        Pokemon(
            6,
            "charizard",
            Images("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png"),
            Types("fire","flying")
        ),
        Pokemon(
            7,
            "squirtle",
            Images("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/7.png"),
            Types("water")
        ),
        Pokemon(
            8,
            "wartortle",
            Images("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/8.png"),
            Types("water")
        ),
        Pokemon(
            9,
            "blastoise",
            Images("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/9.png"),
            Types("water")
        ),
        Pokemon(
            10,
            "caterpie",
            Images("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/10.png"),
            Types("bug")
        ),
        Pokemon(
            11,
            "metapod",
            Images("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/11.png"),
            Types("bug")
        ),
        Pokemon(
            12,
            "butterfree",
            Images("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/12.png"),
            Types("bug","flying")
        ),
        Pokemon(
            13,
            "weedle",
            Images("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/13.png"),
            Types("bug","poison")
        ),
        Pokemon(
            14,
            "kakuna",
            Images("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/14.png"),
            Types("bug","poison")
        ),
        Pokemon(
            15,
            "beedrill",
            Images("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/15.png"),
            Types("bug","poison")
        )
    )



}