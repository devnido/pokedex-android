package io.devnido.pokedex.data.remote.api

import io.devnido.pokedex.data.remote.PokeApiService
import io.devnido.pokedex.data.remote.models.PokemonDetailResponse
import io.devnido.pokedex.data.remote.models.PokemonListResponse

class PokeApiRequests(private val pokeApiService: PokeApiService) {

    suspend fun getPokemons(limit:Int,offset:Int): PokemonListResponse? {

        val response = pokeApiService.getPokemonList(limit,offset)

        return response.body()
    }

    suspend fun getPokemon(number:Int): PokemonDetailResponse? {

        val response = pokeApiService.getPokemonDetail(number)

        return response.body()
    }

}