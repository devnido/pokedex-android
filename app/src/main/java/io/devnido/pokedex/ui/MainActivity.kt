package io.devnido.pokedex.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import io.devnido.pokedex.R
import io.devnido.pokedex.data.PokemonRepository
import io.devnido.pokedex.domain.entities.Pokemon
import io.devnido.pokedex.ui.viewmodels.MainViewModel
import io.devnido.pokedex.ui.viewmodels.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),MainAdapter.OnPokemonClickListener {

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory(
            PokemonRepository()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()

        getPokemonList()
    }




    private fun setupRecyclerView() {
        recyclerviewPokemon.layoutManager = GridLayoutManager(this,3)
    }

    fun getPokemonList(){

        viewModel.fetchTragosList.observe(this, Observer { result ->
            recyclerviewPokemon.adapter = MainAdapter(this, result, this)
        })

    }

    override fun onPokemonClick(pokemon: Pokemon, position: Int) {
        Log.d("TAG_POKEMON",pokemon.name)
    }
}