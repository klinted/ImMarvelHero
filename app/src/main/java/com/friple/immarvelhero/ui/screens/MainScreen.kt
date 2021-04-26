package com.friple.immarvelhero.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RatingBar
import android.widget.ScrollView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.drawToBitmap
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.friple.immarvelhero.R
import com.friple.immarvelhero.databinding.ScreenMainBinding
import com.friple.immarvelhero.network.entities.MarvelCharacter
import com.friple.immarvelhero.ui.viewmodels.MainScreenViewModel
import com.friple.immarvelhero.utilits.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainScreen : Fragment(), AppHeroClickListener {

    private lateinit var mBinding: ScreenMainBinding


    // For RecyclerView
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: MainScreenAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    private lateinit var mNestedScrollView: NestedScrollView

    // Views
    private lateinit var mFabScrollToTop: FloatingActionButton

    private lateinit var mScreenViewModel: MainScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Init adapter
        mAdapter = MainScreenAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.screen_main, container, false)

        mScreenViewModel = ViewModelProvider(this).get(MainScreenViewModel::class.java)

        mBinding.viewModel = mScreenViewModel

        internetCheck()

        return mBinding.root
    }

    // If we don't have connection let's go to another screen (Without internet)
    private fun internetCheck() {
        if (isOnline(APP_ACTIVITY)) {
            // Check data in viewModel. If null make request
            val listCharacters = mScreenViewModel.getMarvelListCharacters().value
            if (listCharacters != null) {
                mAdapter.setData(listCharacters as MutableList<MarvelCharacter>)
            } else {
                mScreenViewModel.updateData {}
            }
        } else {
            navTo("FRAGMENT_WITHOUT_INTERNET")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setObservers()

        initBackButton()
        initFields()
        initRecyclerView()
    }

    private fun setObservers() {
        // Here we check data for changing
        mScreenViewModel.getMarvelListCharacters().observe(viewLifecycleOwner, { list ->
            // Make it in background
            CoroutineScope(Dispatchers.Unconfined).launch {
                mAdapter.setData(list as MutableList<MarvelCharacter>)
            }
        })

        // If error we go to another screen (Without internet)
        mScreenViewModel.getIsError().observe(viewLifecycleOwner, {
            if (it) {
                navTo("FRAGMENT_WITHOUT_INTERNET")
            }
        })
    }

    private fun initBackButton() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    APP_ACTIVITY.findNavController(R.id.main_nav_container).navigateUp()
                }
            }
        APP_ACTIVITY.onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun initFields() {
        // Init for recycle
        mNestedScrollView = mBinding.nsvContainer
        mRecyclerView = mBinding.rvMarvelHeroes

        mLayoutManager = LinearLayoutManager(APP_ACTIVITY)

        // Init for views
        mFabScrollToTop = mBinding.fabScrollToTop

        // UP!!!
        mFabScrollToTop.setOnClickListener {
            mNestedScrollView.postDelayed({
                    mNestedScrollView.fullScroll(ScrollView.FOCUS_UP)},
                0
            )
        }

    }

    private fun initRecyclerView() {

        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.setItemViewCacheSize(10)

        mNestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->

            // Check when load data
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                mScreenViewModel.updateData {

                    // If we don't have internet, you know what
                    if (!isOnline(APP_ACTIVITY)) {
                        navTo("FRAGMENT_WITHOUT_INTERNET")
                    }
                }
            }

            // Visibility of FAB
            if (scrollY > 0) {
                mFabScrollToTop.visibility = View.VISIBLE
            } else {
                mFabScrollToTop.visibility = View.GONE
            }
        })
    }

    // Callback from adapter
    override fun onHeroClick(character: MarvelCharacter, hashMap: HashMap<String, View>) {

        val heroScreen = HeroScreen()

        // For anim
        val p1 = hashMap["cvMainBackCard"]!!
        val p2 = hashMap["ivPhotoOfHero"]!!

        val transitionNameArrayList = arrayListOf<String>()

        transitionNameArrayList.add(0, hashMap["cvMainBackCard"]!!.transitionName) // cvMainBackCard
        transitionNameArrayList.add(1, hashMap["ivPhotoOfHero"]!!.transitionName) // ivPhotoOfHero

        val arguments = Bundle().apply {
            putInt("id", character.id)
            putString("name", character.name)
            putString("photoUrl", character.thumbnail.path)
            putString("extension", character.thumbnail.extension)
            putString("bio", character.description)
            putInt("stories", character.stories.available)
            putFloat("rating", (hashMap["rbRatingBar"] as RatingBar).rating)
            putStringArrayList("transitionName", transitionNameArrayList)
        }

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

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Home"
        APP_ACTIVITY.supportActionBar?.setHomeButtonEnabled(false)
    }

    // TODO: 4/25/2021 Could be leak
    override fun onDestroy() {
        super.onDestroy()
        mAdapter.apply { onDestroy() }

    }
}