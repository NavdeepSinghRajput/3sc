package com.task.a3sctask.home

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.a3sctask.BaseActivity
import com.task.a3sctask.R
import com.task.a3sctask.home.adapter.PokemonAdapter
import com.task.a3sctask.home.model.PokemonModel
import com.task.a3sctask.home.model.ResultsModel
import com.task.a3sctask.home.viewModel.HomeActivityViewModel
import com.task.a3sctask.pokemonDetail.PokemonDetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

/**
 * @author Navdeep Singh
 * @since 09.09.2021
 */

class HomeActivity : BaseActivity() {

    private lateinit var pokemonRV: RecyclerView
    private lateinit var progressBarPagination: ProgressBar
    private lateinit var progressBar: ProgressBar
    private lateinit var searchIcon: ImageView
    private lateinit var search_bar: EditText
    private lateinit var clearSearch: TextView
    private lateinit var searchTitle: TextView
    private lateinit var pokemonAdapter: PokemonAdapter
    private lateinit var viewModel: HomeActivityViewModel
    private var pokemonCurrentList = ArrayList<ResultsModel>()
    private var nextOffset = 0
    private var isLoading = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        inIt()
        initViewModel()
    }

    //fetching Id of the View
    private fun inIt() {
        pokemonRV = findViewById(R.id.pokemonRV)
        progressBarPagination = findViewById(R.id.progressBarPagination)
        progressBar = findViewById(R.id.progressBar)
        searchIcon = findViewById(R.id.searchIcon)
        search_bar = findViewById(R.id.search_bar)
        clearSearch = findViewById(R.id.clearSearch)
        searchTitle = findViewById(R.id.searchTitle)

        //setting adapter to recyclerview
        pokemonAdapter = PokemonAdapter(pokemonAdapterClickHandler = object :
            PokemonAdapter.PokemonAdapterClickHandler {
            override fun pokemonItemClick(resultsModel: ResultsModel) {
                var pokemonUrl = resultsModel.url
                val pokemonId = pokemonUrl.split("pokemon/").toTypedArray().get(1).split("/").get(0)
                PokemonDetailActivity.launchPokemonDetail(
                    activityContext = baseContext,
                    pokemonId = pokemonId, backGroundColor = resultsModel.backgroundColor
                )
            }

            override fun refresh() {
                isLoading = false
            }

        })
        pokemonRV.adapter = pokemonAdapter

        pokemonRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isLoading) {
                    var linearLayoutManager = pokemonRV.layoutManager as LinearLayoutManager
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == nextOffset - 1) {
                        progressBarPagination.visibility = View.VISIBLE
                        callingApi()
                        isLoading = true
                    }
                }
            }
        })

        handlingClick()

    }

    private fun handlingClick() {
        searchIcon.setOnClickListener {
            if (search_bar.text.isNotEmpty()) {
                progressBar.visibility = View.VISIBLE
                GlobalScope.async(Dispatchers.Default) {
                    var pokemonSearchList = searchFromCurrentList(search_bar.text.toString())
                    if (pokemonSearchList.size != 0) {
                        settingSearchListToAdapter(pokemonSearchList)
                        clearSearch.visibility = View.VISIBLE
                        searchTitle.visibility = View.VISIBLE
                        searchTitle.text = "Search Result for:  " + search_bar.text.toString()
                    } else {
                        runOnUiThread {
                            showToast("No Result found")
                        }
                    }

                }
            } else {
                showToast("Nothing to search")
            }

        }

        clearSearch.setOnClickListener {
            pokemonAdapter.setPokemonData(pokemonCurrentList)
            clearSearch.visibility = View.GONE
            searchTitle.visibility = View.GONE
            search_bar.text.clear()
        }
    }

    // search list setting to adapter
    private fun settingSearchListToAdapter(pokemonSearchList: ArrayList<ResultsModel>) {
        pokemonAdapter.settingSearchData(pokemonSearchList)
        progressBar.visibility = View.GONE
    }

    // checking search key in current list
    private fun searchFromCurrentList(searchText: String): ArrayList<ResultsModel> {
        var pokemonSearchList = ArrayList<ResultsModel>()
        for (pokemon in pokemonCurrentList) {
            if (pokemon.name.contains(searchText, ignoreCase = true)) {
                pokemonSearchList.add(pokemon)
            }
        }
        return pokemonSearchList
    }

    // initialViewModel
    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[HomeActivityViewModel::class.java]
        viewModel.getPokemonListObserver().observe(this, Observer<PokemonModel> {
            if (it != null ){
                pokemonAdapter.setPokemonData(it.results)
                nextOffset += it.results.size
            }
            pokemonCurrentList.addAll(it.results)
            progressBarPagination.visibility = View.GONE
            progressBar.visibility = View.GONE
        })
        callingApi()

    }

    private fun callingApi() {
        viewModel.gettingPokemonData(nextOffset)
    }
}