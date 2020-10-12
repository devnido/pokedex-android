package io.devnido.pokedex.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import io.devnido.pokedex.data.PokemonRepositoryImpl
import io.devnido.pokedex.databinding.FragmentListBinding
import io.devnido.pokedex.domain.entities.Pokemon
import io.devnido.pokedex.domain.usecases.GetPokemon
import io.devnido.pokedex.domain.usecases.GetPokemons
import io.devnido.pokedex.ui.viewmodels.PokemonViewModel
import io.devnido.pokedex.ui.viewmodels.ViewModelFactory
import io.devnido.pokedex.core.Result

class ListFragment : Fragment(),PokemonListAdapter.OnPokemonClickListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val pokemonViewModel by viewModels<PokemonViewModel> {
        ViewModelFactory(
            GetPokemons(
                PokemonRepositoryImpl()
            ),
            GetPokemon(
                PokemonRepositoryImpl()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        getPokemonList()
    }


    private fun getPokemonList(){
        pokemonViewModel.getPokemonList().observe(viewLifecycleOwner,Observer{result->
            when(result){
                is Result.Success -> {
                    @Suppress("UNCHECKED_CAST")
                    val pokemonList:List<Pokemon> = result.data as List<Pokemon>

                    pokemonList.map {
                        Log.d("TAG_POKEMON",it.name)
                    }

                    binding.recyclerviewPokemon.adapter = PokemonListAdapter(requireContext(), pokemonList, this)
                }
                is Result.Error -> {
                    val exception: Exception = result.exception
                    Toast.makeText(requireContext(),exception.message,Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setupRecyclerView() {
        binding.recyclerviewPokemon.layoutManager = GridLayoutManager(requireContext(),3)
    }

    override fun onPokemonClick(pokemon: Pokemon, position: Int) {

        val action = ListFragmentDirections.actionListFragmentToDetailFragment(pokemon)

        findNavController().navigate(action)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}