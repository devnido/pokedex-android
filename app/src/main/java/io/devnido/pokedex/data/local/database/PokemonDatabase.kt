package io.devnido.pokedex.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.devnido.pokedex.data.local.models.PokemonEntity

@Database(entities = [PokemonEntity::class],version = 1, exportSchema = false )
abstract class PokemonDatabase:RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    companion object {
        val DB_NAME = "pokemon_db"
    }
}