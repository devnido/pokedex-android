package io.devnido.pokedex.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.devnido.pokedex.R
import io.devnido.pokedex.domain.entities.Pokemon

class MainActivity : AppCompatActivity(),PokemonListAdapter.OnPokemonClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }



    override fun onPokemonClick(pokemon: Pokemon, position: Int) {
        Log.d("TAG_POKEMON",pokemon.name)
    }
}