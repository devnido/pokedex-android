package io.devnido.pokedex.data

import android.util.Log
import io.devnido.pokedex.data.local.database.AppDatabase
import io.devnido.pokedex.data.local.mappers.DbToDomainMapper
import io.devnido.pokedex.data.local.models.PokemonEntity
import io.devnido.pokedex.data.remote.PokeApiService
import io.devnido.pokedex.data.remote.RestClient
import io.devnido.pokedex.data.remote.api.PokeApiRequests
import io.devnido.pokedex.data.remote.mappers.ApiToDomainMapper
import io.devnido.pokedex.domain.repository.PokemonRepository
import io.devnido.pokedex.domain.entities.Pokemon

class PokemonRepositoryImpl(private val appDatabase: AppDatabase): PokemonRepository {

    private val pokeApiService: PokeApiService = RestClient.build()
    private val pokeApiRequests: PokeApiRequests = PokeApiRequests(pokeApiService)
    private val apiMapper:ApiToDomainMapper = ApiToDomainMapper()
    private val dbMapper:DbToDomainMapper = DbToDomainMapper()


    private val itemsPerPage = 251

    override suspend fun getPokemonList(): List<Pokemon> {

        val pokemonListResponse = pokeApiRequests.getPokemonList(itemsPerPage,0)

        return  apiMapper.mapPokemonListResponseToDomain(pokemonListResponse).toList()
    }

    override suspend fun getPokemonDetail(id:Int): Pokemon {

        var pokemon: Pokemon? = getPokemonFromDb(id)

        return if (pokemon != null){
            pokemon
        }else{
            val pokemonDetailResponse = pokeApiRequests.getPokemon(id)

            pokemon = apiMapper.mapPokemonDetailResponseToDomain(pokemonDetailResponse)

            savePokemonInDb(pokemon)

            return  pokemon
        }
    }

    override suspend fun saveDetailPokemon(pokemon: Pokemon) {

        val pokemonEntity: PokemonEntity = dbMapper.mapDomainToPokemonDb(pokemon)

        appDatabase.pokemonDao().insertPokemon(pokemonEntity)
    }

    private suspend fun getPokemonFromDb(id:Int):Pokemon?{

        val pokemonEntity = appDatabase.pokemonDao().getPokemon(id)
        Log.d("TAG_POKEMON_DB_GET",pokemonEntity.toString())
        return if(pokemonEntity != null) dbMapper.mapPokemonDbToDomain(pokemonEntity) else null
    }

    private suspend fun savePokemonInDb(pokemon:Pokemon){

        val pokemonEntity = dbMapper.mapDomainToPokemonDb(pokemon)
        Log.d("TAG_POKEMON_DB_SAVE",pokemonEntity.toString())
       appDatabase.pokemonDao().insertPokemon(pokemonEntity)

    }
}