package io.devnido.pokedex.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.devnido.pokedex.data.local.models.PokemonEntity

@Database(entities = [PokemonEntity::class],version = 1 )
abstract class AppDatabase:RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            INSTANCE = INSTANCE ?: Room.databaseBuilder(context.applicationContext,AppDatabase::class.java,"pokemon_db").build()
            return INSTANCE!!
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }

}