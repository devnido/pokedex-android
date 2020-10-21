package io.devnido.pokedex.data

import android.util.Log
import io.devnido.pokedex.data.local.database.PokemonDao
import io.devnido.pokedex.data.local.database.PokemonDatabase
import io.devnido.pokedex.data.local.mappers.DbToDomainMapper
import io.devnido.pokedex.data.local.models.PokemonEntity
import io.devnido.pokedex.data.remote.PokeApiService
import io.devnido.pokedex.data.remote.RestClient
import io.devnido.pokedex.data.remote.api.PokeApiRequests
import io.devnido.pokedex.data.remote.mappers.ApiToDomainMapper
import io.devnido.pokedex.domain.repository.PokemonRepository
import io.devnido.pokedex.domain.entities.Pokemon
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepositoryImpl @Inject constructor(
    private val pokeApiRequests: PokeApiRequests,
    private val pokemonDao: PokemonDao
) : PokemonRepository {

    private val itemsPerPage = 700

    override suspend fun getPokemonList(): List<Pokemon> {

        val pokemonListResponse = pokeApiRequests.getPokemonList(itemsPerPage, 0)

        return ApiToDomainMapper.mapPokemonListResponseToDomain(pokemonListResponse).toList()
    }

    override suspend fun getPokemonDetail(id: Int): Pokemon {

        var pokemon: Pokemon? = getPokemonFromDb(id)

        return if (pokemon != null) {
            pokemon
        } else {
            val pokemonDetailResponse = pokeApiRequests.getPokemon(id)

            pokemon = ApiToDomainMapper.mapPokemonDetailResponseToDomain(pokemonDetailResponse)
            savePokemonInDb(pokemon)

            return pokemon
        }
    }

    override suspend fun saveDetailPokemon(pokemon: Pokemon) {

        val pokemonEntity: PokemonEntity = DbToDomainMapper.mapDomainToPokemonDb(pokemon)

        pokemonDao.insertPokemon(pokemonEntity)
    }

    private suspend fun getPokemonFromDb(id: Int): Pokemon? {

        val pokemonEntity = pokemonDao.getPokemon(id)
        return if (pokemonEntity != null) {
            val pokemon = DbToDomainMapper.mapPokemonDbToDomain(pokemonEntity)
            pokemon
        } else {
            null
        }
    }

    private suspend fun savePokemonInDb(pokemon: Pokemon) {

        val pokemonEntity = DbToDomainMapper.mapDomainToPokemonDb(pokemon)
        pokemonDao.insertPokemon(pokemonEntity)

    }
}