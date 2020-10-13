package io.devnido.pokedex.data

import android.util.Log
import io.devnido.pokedex.data.remote.PokeApiService
import io.devnido.pokedex.data.remote.RestClient
import io.devnido.pokedex.data.remote.api.PokeApiRequests
import io.devnido.pokedex.data.remote.mappers.ApiToDomainMapper
import io.devnido.pokedex.domain.repository.PokemonRepository
import io.devnido.pokedex.domain.entities.Pokemon

class PokemonRepositoryImpl: PokemonRepository {

    private val pokeApiService: PokeApiService = RestClient.build()
    private val pokeApiRequests: PokeApiRequests = PokeApiRequests(pokeApiService)
    private val mapper:ApiToDomainMapper = ApiToDomainMapper()

    val ITEMS_PER_PAGE = 251

    override suspend fun getPokemonList(): List<Pokemon> {

        val pokemonListResponse = pokeApiRequests.getPokemonList(ITEMS_PER_PAGE,0)

        return  mapper.mapPokemonListResponseToDomain(pokemonListResponse).toList()
    }

    override suspend fun getPokemonDetail(number:Int): Pokemon {

        val pokemonDetailResponse = pokeApiRequests.getPokemon(number)

        return mapper.mapPokemonDetailResponseToDomain(pokemonDetailResponse)

    }
}