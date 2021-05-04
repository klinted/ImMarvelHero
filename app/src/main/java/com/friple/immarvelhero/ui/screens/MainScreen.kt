package com.friple.immarvelhero.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RatingBar
import androidx.core.view.drawToBitmap
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.friple.immarvelhero.R
import com.friple.immarvelhero.databinding.ScreenMainBinding
import com.friple.immarvelhero.ui.adapters.MainScreenAdapter
import com.friple.immarvelhero.ui.recyclerview.views.BaseView
import com.friple.immarvelhero.ui.viewmodels.BaseViewModel.State
import com.friple.immarvelhero.ui.viewmodels.MainScreenViewModel
import com.friple.immarvelhero.utilits.APP_ACTIVITY
import com.friple.immarvelhero.utilits.AppHeroClickListener
import com.friple.immarvelhero.utilits.AppNestedScrollView
import com.friple.immarvelhero.utilits.goToTop
import com.friple.immarvelhero.utilits.states.AppStatesController
import com.friple.immarvelhero.utilits.states.AppStatesMainControllerImpl
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainScreen : Fragment(), AppHeroClickListener {

    private lateinit var mBinding: ScreenMainBinding
    private lateinit var mScreenViewModel: MainScreenViewModel

    private lateinit var mAppStatesMainController: AppStatesController

    // For RecyclerView
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: MainScreenAdapter
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mNestedScrollView: AppNestedScrollView

    // Views
    private lateinit var mFabScrollToTop: FloatingActionButton
    private lateinit var mProgressBar: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Init adapter
        mAdapter = MainScreenAdapter(this)

        mBinding = DataBindingUtil.inflate(inflater, R.layout.screen_main, container, false)

        mScreenViewModel = ViewModelProvider(this).get(MainScreenViewModel::class.java)

        mBinding.viewModel = mScreenViewModel

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setObservers()
        initFields()
        initRecyclerView()
    }

    private fun setObservers() {

        mScreenViewModel.getState().observe(viewLifecycleOwner, { state ->
            when (state) {
                State.CREATED -> {
                    mAppStatesMainController.onCreated()
                }
                State.LOADING -> {
                    mAppStatesMainController.onLoading()
                }
                State.SUCCESS -> {
                    mScreenViewModel.getMarvelListCharacters().value?.let { characterList ->
                        mAppStatesMainController.onSuccess(characterList)
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
        mProgressBar = mBinding.pbHeroesLoading

        // Init for recycle
        mNestedScrollView = mBinding.nsvContainer
        mRecyclerView = mBinding.rvMarvelHeroes

        mLayoutManager = LinearLayoutManager(APP_ACTIVITY)

        // For states
        mAppStatesMainController =
            AppStatesMainControllerImpl(mProgressBar, mNestedScrollView, mAdapter, mScreenViewModel)

        // Init for views
        mFabScrollToTop = mBinding.fabScrollToTop

        // UP!!!
        mFabScrollToTop.setOnClickListener { mNestedScrollView.goToTop() }
    }

    private fun initRecyclerView() {

        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.setItemViewCacheSize(10)

        mNestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->

            // Check when load data
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                mScreenViewModel.updateData()
            }

            // Visibility of FAB
            mFabScrollToTop.visibility = if (scrollY > 0) View.VISIBLE else View.GONE
        })
    }

    // Callback from adapter
    override fun onClickFromItem(view: BaseView, hashMap: HashMap<String, View>) {

        // For anim
        val p1 = hashMap["cvMainBackCard"]!!
        val p2 = hashMap["ivPhotoOfHero"]!!

        val transitionNameArrayList = arrayListOf<String>()

        transitionNameArrayList.add(0, hashMap["cvMainBackCard"]!!.transitionName) // cvMainBackCard
        transitionNameArrayList.add(1, hashMap["ivPhotoOfHero"]!!.transitionName) // ivPhotoOfHero

        val arguments = Bundle().apply {
            putInt("id", view.id)
            putString("name", view.name)
            putString("photoUrl", view.thumbnail.path)
            putString("extension", view.thumbnail.extension)
            putString("bio", view.description)
            putInt("stories", view.stories.available)
            putFloat("rating", (hashMap["rbRatingBar"] as RatingBar).rating)
            putStringArrayList("transitionName", transitionNameArrayList)
        }

        val heroScreen = HeroScreen()

        // Pass fields to hero screen
        heroScreen.arguments = arguments
        heroScreen.bitmapImage = hashMap["ivPhotoOfHero"]!!.drawToBitmap()

        // TODO: 4/25/2021  Make it by nav Component
        APP_ACTIVITY.supportFragmentManager.beginTransaction()
            .addSharedElement(p1, transitionNameArrayList[0])
            .addSharedElement(p2, transitionNameArrayList[1])
            .replace(R.id.main_container_fragment, heroScreen)
            .addToBackStack(null)
            .commit()
    }

    // Callback from adapter
    override fun onClickFromItem() {
        mScreenViewModel.updateData()
    }

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Home"
        APP_ACTIVITY.supportActionBar?.setHomeButtonEnabled(false)
    }
}
