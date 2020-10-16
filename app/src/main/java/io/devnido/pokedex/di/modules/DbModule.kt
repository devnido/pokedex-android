package io.devnido.pokedex.di.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.devnido.pokedex.data.local.database.PokemonDao
import io.devnido.pokedex.data.local.database.PokemonDatabase
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context):PokemonDatabase{
        return Room.databaseBuilder(context,PokemonDatabase::class.java,PokemonDatabase.DB_NAME).build()
    }

    @Provides
    @Singleton
    fun providePokemonDao(pokemonDatabase: PokemonDatabase):PokemonDao{
        return pokemonDatabase.pokemonDao()
    }

}