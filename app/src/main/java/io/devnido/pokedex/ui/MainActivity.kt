package io.devnido.pokedex.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.devnido.pokedex.BaseApplication
import io.devnido.pokedex.R
import io.devnido.pokedex.di.components.AppComponent
import io.devnido.pokedex.domain.entities.Pokemon

class MainActivity : AppCompatActivity(),PokemonListAdapter.OnPokemonClickListener {


    lateinit var appComponent: AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {

        // Creates an instance of Registration component by grabbing the factory from the app graph
        appComponent = (application as BaseApplication).appComponent
        // Injects this activity to the just created registration component
        appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onPokemonClick(pokemon: Pokemon, position: Int) {
        Log.d("TAG_POKEMON",pokemon.name)
    }
}