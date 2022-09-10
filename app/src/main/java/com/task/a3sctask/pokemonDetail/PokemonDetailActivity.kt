package com.task.a3sctask.pokemonDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.task.a3sctask.BaseActivity
import com.task.a3sctask.R
import com.task.a3sctask.home.model.PokemonDetailModel
import com.task.a3sctask.home.viewModel.PokemonDetailActivityViewModel
import com.task.a3sctask.pokemonDetail.adapter.PokemonStatsAdapter
import kotlin.properties.Delegates

/**
 * @author Navdeep Singh
 * @since 09.09.2021
 */
class PokemonDetailActivity : BaseActivity() {

    private lateinit var pokemonId: String
    private var pokemonBgColor by Delegates.notNull<Int>()
    private lateinit var pokemonTitleName: TextView
    private lateinit var pokemonHeight: TextView
    private lateinit var pokemonWeight: TextView
    private lateinit var pokemonAbility: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var pokemonRV: RecyclerView
    private lateinit var pokemonIcon: ImageView
    private lateinit var pokemonStatsAdapter: PokemonStatsAdapter
    private lateinit var pokemonIdTextView: TextView
    private lateinit var detailsLayout: ConstraintLayout
    private lateinit var mainCard: ConstraintLayout
    private lateinit var backArrow: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)

        inIt()
        gettingValueIntent()
        initViewModel()
    }

    //fetching value from intent
    private fun gettingValueIntent() {
        if (intent.hasExtra(POKEMON_ID)) {
            pokemonId = intent.getStringExtra(POKEMON_ID).toString()
            pokemonBgColor = intent.getIntExtra(BACKGROUND_COLOR, 0)
            if (pokemonBgColor != 0) {
                mainCard.setBackgroundColor(pokemonBgColor)

            }
        }
    }

    //fetching id
    private fun inIt() {
        detailsLayout = findViewById<ConstraintLayout>(R.id.detailsLayout)
        mainCard = findViewById<ConstraintLayout>(R.id.mainCard)
        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        pokemonTitleName = findViewById<TextView>(R.id.pokemonTitleName)
        pokemonHeight = findViewById<TextView>(R.id.pokemonHeight)
        pokemonWeight = findViewById<TextView>(R.id.pokemonWeight)
        pokemonAbility = findViewById<TextView>(R.id.pokemonAbility)
        pokemonRV = findViewById<RecyclerView>(R.id.pokemonRV)
        pokemonIcon = findViewById<ImageView>(R.id.pokemonIcon)
        pokemonIdTextView = findViewById<TextView>(R.id.pokemonId)
        backArrow = findViewById<ImageView>(R.id.backArrow)

        backArrow.setOnClickListener { onBackPressed() }

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.hasExtra(POKEMON_ID)) {
            pokemonId = intent.getStringExtra(POKEMON_ID).toString()
        }
    }

    // setting viewmodel
    private fun initViewModel() {
        val viewModel = ViewModelProvider(this)[PokemonDetailActivityViewModel::class.java]
        viewModel.getPokemonListObserver().observe(this, Observer<PokemonDetailModel> {
            if (it != null) {
                settingViewWithData(it)
                detailsLayout.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        })
        viewModel.gettingPokemonDetailData(id = pokemonId)
    }

    private fun settingViewWithData(pokemonDetailModel: PokemonDetailModel) {
        pokemonTitleName.text = pokemonDetailModel.name
        pokemonHeight.text = pokemonDetailModel.height.toString()
        pokemonWeight.text = pokemonDetailModel.weight.toString()
        pokemonIdTextView.text = "#00" + pokemonId

        pokemonAbility.text = pokemonDetailModel.weight.toString()
        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher_round)
            .error(R.mipmap.ic_launcher_round)

        Glide.with(baseContext).load(pokemonDetailModel.sprites.back_default).apply(options)
            .into(pokemonIcon)

        pokemonStatsAdapter = PokemonStatsAdapter()
        pokemonRV.adapter = pokemonStatsAdapter
        pokemonStatsAdapter.setPokemonData(pokemonDetailModel.stats)

    }

    companion object {
        private const val POKEMON_ID = "pokemonId"
        private const val BACKGROUND_COLOR = "bgColor"

        fun launchPokemonDetail(activityContext: Context, pokemonId: String, backGroundColor: Int) {
            activityContext.startActivity(
                Intent(activityContext, PokemonDetailActivity::class.java).setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK
                ).putExtra(POKEMON_ID, pokemonId).putExtra(BACKGROUND_COLOR, backGroundColor)
            )

        }
    }
}