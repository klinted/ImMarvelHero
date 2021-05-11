package com.friple.immarvelhero.ui.screens

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.NestedScrollView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.friple.domain.entities.heroes.MarvelCharacter
import com.friple.immarvelhero.R
import com.friple.immarvelhero.databinding.ScreenMainBinding
import com.friple.immarvelhero.ui.adapters.MainScreenAdapter
import com.friple.immarvelhero.ui.recyclerview.views.AppViewFactory
import com.friple.immarvelhero.ui.recyclerview.views.BaseView
import com.friple.immarvelhero.ui.viewmodels.BaseViewModel.State
import com.friple.immarvelhero.ui.viewmodels.MainScreenViewModel
import com.friple.immarvelhero.utilits.*
import com.friple.immarvelhero.utilits.adapter.AppHeroClickListener
import com.friple.immarvelhero.utilits.states.AppStatesController
import com.friple.immarvelhero.utilits.states.AppStatesMainControllerImpl
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: 5/11/2021 Make DI
class MainScreen : BaseFragment<ScreenMainBinding, MainScreenViewModel>(
    ScreenMainBinding::inflate,
    MainScreenViewModel::class.java
), AppHeroClickListener {

    private lateinit var mAppStatesMainController: AppStatesController

    // For RecyclerView
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: MainScreenAdapter
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mNestedScrollView: AppNestedScrollView

    // Views
    private lateinit var mFabScrollToTop: FloatingActionButton
    private lateinit var mProgressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mAdapter = MainScreenAdapter(this)
        binding.viewModel = viewModel

        initFields()
        setObservers()
        initRecyclerView()
        initBackButton()
    }

    private fun setObservers() {

        viewModel.state.observe(viewLifecycleOwner, { state ->
            when (state) {
                State.CREATED -> {
                    viewModel.typeOfScreen.value = TYPE_SCREEN_HEROES
                    mAppStatesMainController.onCreated()
                }
                State.LOADING -> {
                    mAppStatesMainController.onLoading()
                }
                State.SUCCESS -> {
                    when (viewModel.typeOfScreen.value) {
                        TYPE_SCREEN_HEROES -> {
                            viewModel.marvelListCharacters.value?.let { characterList ->
                                mAppStatesMainController.onSuccess(characterList)
                            }
                        }
                        TYPE_SCREEN_HERO_DETAIL -> {
                            openDetailScreen(viewModel.recentDataOfDetailScreen.value!!)
                        }
                    }
                }
                State.ERROR -> {
                    mAppStatesMainController.onError()
                }
                else -> {
                    mAppStatesMainController.onError()
                }
            }
        })
    }

    private fun initFields() {
        mProgressBar = binding.pbHeroesLoading

        // Init for recycle
        mNestedScrollView = binding.nsvContainer
        mRecyclerView = binding.rvMarvelHeroes

        mLayoutManager = LinearSmoothScroller(APP_ACTIVITY, RecyclerView.VERTICAL, false)

        // For states
        mAppStatesMainController =
            AppStatesMainControllerImpl(mProgressBar, mNestedScrollView, mAdapter, viewModel)

        // Init for views
        mFabScrollToTop = binding.fabScrollToTop

        // UP!!!
        mFabScrollToTop.setOnClickListener { mNestedScrollView.goToTop() }
    }

    private fun initRecyclerView() {

        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = mLayoutManager

        mNestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->

            // Check when load data
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                if (viewModel.state.value != State.LOADING &&
                    viewModel.typeOfScreen.value == TYPE_SCREEN_HEROES
                ) {
                    viewModel.updateData()
                }
            }

            // Visibility of FAB
            mFabScrollToTop.visibility = if (scrollY > 0) View.VISIBLE else View.GONE
        })
    }

    private fun initBackButton() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (viewModel.typeOfScreen.value == TYPE_SCREEN_HERO_DETAIL) {
                        onClickFromItem()
                    } else {
                        APP_ACTIVITY.finish()
                    }
                }
            }
        APP_ACTIVITY.onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    // Callback from adapter
    override fun onClickFromItem() {
        APP_ACTIVITY.title = "Home"

        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(false)

        APP_ACTIVITY.mLayerBackground.visibility = View.VISIBLE

        viewModel.updateData()
    }

    // Callback from adapter
    override fun onClickFromItem(character: MarvelCharacter) {
        openDetailScreen(character)
    }

    private fun openDetailScreen(character: MarvelCharacter) {

        APP_ACTIVITY.title = character.name

        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        APP_ACTIVITY.mLayerBackground.visibility = View.INVISIBLE

        val listCharacterViews = mutableListOf<BaseView>()

        listCharacterViews.add(AppViewFactory.getViewType(TYPE_SCREEN_HERO_DETAIL, character))

        viewModel.typeOfScreen.value = TYPE_SCREEN_HERO_DETAIL
        viewModel.recentDataOfDetailScreen.value = character

        mAdapter.setData(listCharacterViews)

    }

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Home"
        APP_ACTIVITY.supportActionBar?.setHomeButtonEnabled(false)
    }
}
