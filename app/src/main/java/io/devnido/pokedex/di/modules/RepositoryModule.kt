package io.devnido.pokedex.di.modules

import dagger.Binds
import dagger.Module
import io.devnido.pokedex.data.PokemonRepositoryImpl
import io.devnido.pokedex.domain.repository.PokemonRepository

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun providePokemonRepository(pokemonRepository: PokemonRepositoryImpl):PokemonRepository
}