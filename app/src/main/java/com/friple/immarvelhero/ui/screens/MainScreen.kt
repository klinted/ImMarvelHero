package com.friple.immarvelhero.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.friple.immarvelhero.R
import com.friple.immarvelhero.databinding.ScreenMainBinding
import com.friple.immarvelhero.network.entities.MarvelCharacter
import com.friple.immarvelhero.ui.viewmodels.MainScreenViewModel
import com.friple.immarvelhero.utilits.APP_ACTIVITY
import com.friple.immarvelhero.utilits.AppHeroClickListener
import com.friple.immarvelhero.utilits.isOnline
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainScreen : Fragment(), AppHeroClickListener {

    private lateinit var mBinding: ScreenMainBinding

    private lateinit var mNestedScrollView: NestedScrollView

    // For RecyclerView
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: MainScreenAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    private lateinit var mScreenViewModel: MainScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.screen_main, container, false)

        mScreenViewModel = ViewModelProvider(this).get(MainScreenViewModel::class.java)

        mBinding.viewModel = mScreenViewModel

        mAdapter = MainScreenAdapter(this)

        if (activity?.applicationContext?.let { isOnline(it) } == true) {
            val listCharacters = mScreenViewModel.getMarvelListCharacters().value
            if (listCharacters != null) {
                mAdapter.setData(listCharacters as MutableList<MarvelCharacter>)
            } else {
                mScreenViewModel.updateData{}
            }
        } else {
            findNavController().navigate(
                R.id.action_mainScreen_to_fragmentWithoutInternet,
                null,
                null
            )
            onDestroy()
        }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initFields()
        initRecyclerView()
    }

    private fun initFields() {
        mNestedScrollView = mBinding.nsvContainer
        mRecyclerView = mBinding.rvMarvelHeroes

        mLayoutManager = LinearLayoutManager(activity)

        mScreenViewModel.getMarvelListCharacters().observe(viewLifecycleOwner, { list ->

            CoroutineScope(Dispatchers.Unconfined).launch {
                mAdapter.setData(list as MutableList<MarvelCharacter>)
            }
        })
    }

    private fun initRecyclerView() {

        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = mLayoutManager

        mRecyclerView.setItemViewCacheSize(40)

        mNestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->

            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                mScreenViewModel.updateData {
                    if (!isOnline(APP_ACTIVITY.applicationContext)) {
                        findNavController().navigate(
                            R.id.action_mainScreen_to_fragmentWithoutInternet,
                            null,
                            null
                        )
                        onDestroy()
                    }
                }
            }
        })
    }

    override fun onHeroClick(character: MarvelCharacter, listOfViews: List<View>) {

        val heroScreen = HeroScreen()

        val p1 = listOfViews[0] // cvMainBackCard
        val p2 = listOfViews[1] // ivPhotoOfHero

        val transitionNameArrayList = arrayListOf<String>()

        transitionNameArrayList.add(0, listOfViews[0].transitionName) // cvMainBackCard
        transitionNameArrayList.add(1, listOfViews[1].transitionName) // ivPhotoOfHero

        val arguments = Bundle().apply {
            putInt("id", character.id)
            putString("name", character.name)
            putString("photoUrl", character.thumbnail.path)
            putString("extension", character.thumbnail.extension)
            putString("bio", character.description)
            putInt("stories", character.stories.available)
            putFloat("rating", (listOfViews[2] as RatingBar).rating)

            putStringArrayList("transitionName", transitionNameArrayList)
        }

        heroScreen.arguments = arguments

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

    override fun onDestroy() {
        super.onDestroy()
        mAdapter.onDestroy()
    }
}