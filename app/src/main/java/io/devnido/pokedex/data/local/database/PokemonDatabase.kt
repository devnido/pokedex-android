package io.devnido.pokedex.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.devnido.pokedex.data.local.models.PokemonEntity

@Database(entities = [PokemonEntity::class],version = 1 )
abstract class PokemonDatabase:RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    companion object {
        private var INSTANCE: PokemonDatabase? = null

        fun getDatabase(context: Context): PokemonDatabase{
            INSTANCE = INSTANCE ?: Room.databaseBuilder(context.applicationContext,PokemonDatabase::class.java,"pokemon_db").build()
            return INSTANCE!!
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }

}