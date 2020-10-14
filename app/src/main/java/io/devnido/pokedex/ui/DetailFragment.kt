package io.devnido.pokedex.ui

import android.annotation.SuppressLint
import android.graphics.*
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
import io.devnido.pokedex.data.PokemonRepositoryImpl
import io.devnido.pokedex.databinding.FragmentDetailBinding
import io.devnido.pokedex.domain.entities.Pokemon
import io.devnido.pokedex.domain.usecases.GetPokemon
import io.devnido.pokedex.domain.usecases.GetPokemons
import io.devnido.pokedex.ui.viewmodels.PokemonViewModel
import io.devnido.pokedex.ui.viewmodels.ViewModelFactory
import io.devnido.pokedex.core.Result
import io.devnido.pokedex.core.Utils
import io.devnido.pokedex.core.hide
import io.devnido.pokedex.core.show
import io.devnido.pokedex.data.local.database.AppDatabase


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var pokemon: Pokemon

    private val pokemonViewModel by viewModels<PokemonViewModel> {
        ViewModelFactory(
            GetPokemons(
                PokemonRepositoryImpl(AppDatabase.getDatabase(requireActivity().applicationContext))
            ),
            GetPokemon(
                PokemonRepositoryImpl(AppDatabase.getDatabase(requireActivity().applicationContext))
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

    @SuppressLint("SetTextI18n")
    private fun setupInitialUI(){
        with(binding){

            Glide.with(requireContext()).load(pokemon.images.large).centerInside().into(imgPokemon)

            txtNamePokemon.text = pokemon.name
            txtIdPokemon.text = "# ${pokemon.id}"

        }
    }

    @SuppressLint("SetTextI18n", "LongLogTag")
    private fun setupDetailUI(){
        with(binding){

            containerDetail.show()

            Log.d("TAG_POKEMON_FRAGMENT",pokemon.toString())
            pokemon.types?.first?.let {type ->
                containerPokemonTypes.txtFirstType.text = type
                val color = Utils.getColorByPokemonType(context,type)
                color?.let {
                    containerPokemonTypes.txtFirstType.background.colorFilter = PorterDuffColorFilter(color,PorterDuff.Mode.SRC)
                }
            }

            pokemon.types?.second?.let {type ->
                Log.d("TAG_POKEMON_FRAGMENT_SECOND_TYPE",type)
                containerPokemonTypes.txtSecondType.visibility = View.VISIBLE
                containerPokemonTypes.txtSecondType.text = type
                val color = Utils.getColorByPokemonType(context,type)
                color?.let {
                    containerPokemonTypes.txtSecondType.background.colorFilter = PorterDuffColorFilter(color,PorterDuff.Mode.SRC)
                }
            }

            containerPokemonInfo.infoOrder.text = pokemon.order.toString()
            containerPokemonInfo.infoExpBase.text = pokemon.baseExperience.toString()

            pokemon.height?.let {
                containerPokemonInfo.infoHeight.text = "$it m"
            }

            pokemon.weight?.let {
                containerPokemonInfo.infoWeight.text = "$it kg"
            }

            pokemon.abilities?.let {
               containerPokemonInfo.infoAbilities.text = it.joinToString(separator = ", ",transform = {ability->ability.name})
            }
        }


    }

    private fun getPokemonDetail(){
        Log.d("TAG_POKEMON_FD",pokemon.name)
        pokemonViewModel.getPokemonDetail(pokemon.id).observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.Loading -> {
                    binding.progressDetailInfo.show()
                }
                is Result.Success -> {
                    @Suppress("UNCHECKED_CAST")
                    pokemon = result.data as Pokemon


                    Log.d("TAG_POKEMON",pokemon.name)
                    Log.d("TAG_POKEMON",pokemon.types?.first  ?: "No first type")
                    Log.d("TAG_POKEMON",pokemon.types?.second ?: "No second type")

                    binding.progressDetailInfo.hide()
                    setupDetailUI()

                }
                is Result.Error -> {
                    val exception: Exception = result.exception
                    binding.progressDetailInfo.hide()
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