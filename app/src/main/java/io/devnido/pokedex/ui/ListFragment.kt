package io.devnido.pokedex.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import io.devnido.pokedex.databinding.FragmentListBinding
import io.devnido.pokedex.domain.entities.Pokemon
import io.devnido.pokedex.ui.viewmodels.PokemonViewModel
import io.devnido.pokedex.core.Result
import io.devnido.pokedex.core.hide
import io.devnido.pokedex.core.show
import javax.inject.Inject
import javax.inject.Provider

class ListFragment : Fragment(),PokemonListAdapter.OnPokemonClickListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var pokemonViewModel: PokemonViewModel

    @Inject
    lateinit var viewModelProvider: Provider<PokemonViewModel>

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as MainActivity).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pokemonViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                viewModelProvider.get() as T
        }).get(PokemonViewModel::class.java)
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
        initObservers()
    }

    private fun initObservers(){

        pokemonViewModel.pokemonList.observe(viewLifecycleOwner, Observer {pokemonList->
            setAdapter(pokemonList)
            binding.recyclerviewPokemon.show()
        })

        pokemonViewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            if(isLoading) binding.progressList.show() else binding.progressList.hide()
        })

        pokemonViewModel.errorMessage.observe(viewLifecycleOwner, Observer { error ->
            if (error != "") {
                Toast.makeText(requireContext(), "Error: $error", Toast.LENGTH_SHORT).show()
            }
        })

    }


    private fun setupRecyclerView() {
        binding.recyclerviewPokemon.layoutManager = GridLayoutManager(requireContext(),3)
    }

    private fun setAdapter(pokemonList:List<Pokemon>){
        binding.recyclerviewPokemon.adapter = PokemonListAdapter(requireContext(), pokemonList, this)
        binding.recyclerviewPokemon.adapter?.notifyDataSetChanged()
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