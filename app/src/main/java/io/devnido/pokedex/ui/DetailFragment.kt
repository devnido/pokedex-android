package io.devnido.pokedex.ui

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import io.devnido.pokedex.R
import io.devnido.pokedex.data.PokemonRepositoryImpl
import io.devnido.pokedex.databinding.FragmentDetailBinding
import io.devnido.pokedex.domain.entities.Pokemon
import io.devnido.pokedex.domain.usecases.GetPokemon
import io.devnido.pokedex.domain.usecases.GetPokemons
import io.devnido.pokedex.ui.viewmodels.PokemonViewModel
import io.devnido.pokedex.ui.viewmodels.ViewModelFactory
import io.devnido.pokedex.core.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var pokemon: Pokemon

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemon = DetailFragmentArgs.fromBundle(requireArguments()).pokemon
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInitialUI()
        getPokemonDetail()
    }

    private fun setupInitialUI(){
        with(binding){

            Glide.with(requireContext()).load(pokemon.images.large).centerInside().into(imgPokemon)

            txtNamePokemon.text = pokemon.name
            txtNumberPokemon.text = "# ${pokemon.number}"

        }
    }

    private fun setupDetailUI(){
        with(binding){

            pokemon.types?.first?.let {
                containerPokemonTypes.txtFirstType.text = it
                //containerPokemonTypes.txtFirstType.setBackgroundColor(R.color.${it})
                containerPokemonTypes.txtFirstType.background.colorFilter = BlendModeColorFilter(
                    R.color.pokemonTypeFire, BlendMode.SRC_ATOP)

            }

        }


    }

    private fun getPokemonDetail(){
        Log.d("TAG_POKEMON_FD",pokemon.name)
        pokemonViewModel.getPokemonDetail(pokemon.number).observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.Success -> {
                    @Suppress("UNCHECKED_CAST")
                    val pokemonDetail:Pokemon = result.data as Pokemon


                    Log.d("TAG_POKEMON",pokemonDetail.name)
                    Log.d("TAG_POKEMON",pokemonDetail.types?.first  ?: "No first type")
                    Log.d("TAG_POKEMON",pokemonDetail.types?.second ?: "No second type")




                }
                is Result.Error -> {
                    val exception: Exception = result.exception
                    Toast.makeText(requireContext(),exception.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}