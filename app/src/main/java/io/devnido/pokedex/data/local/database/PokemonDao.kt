package io.devnido.pokedex.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.devnido.pokedex.data.local.models.PokemonEntity

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemons WHERE id = :id")
    suspend fun getPokemon(id:Int):PokemonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemonEntity: PokemonEntity)


}