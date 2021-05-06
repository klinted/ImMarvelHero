package com.friple.immarvelhero.utilits.states

import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.friple.domain.entities.heroes.MarvelCharacter
import com.friple.immarvelhero.ui.adapters.MainScreenAdapter
import com.friple.immarvelhero.ui.recyclerview.views.AppViewFactory
import com.friple.immarvelhero.ui.recyclerview.views.BaseView
import com.friple.immarvelhero.ui.viewmodels.MainScreenViewModel
import com.friple.immarvelhero.utilits.*

class AppStatesMainControllerImpl(
    private val mProgressBar: ProgressBar,
    private val mNestedScrollView: AppNestedScrollView,
    private val mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
    private val mViewModel: MainScreenViewModel
) : AppStatesController {

    // If we don't have connection let's go to another screen (Without internet)
    override fun onCreated() {

        mNestedScrollView.goToTop()

        if (isOnline(APP_ACTIVITY)) {
            val listCharacter = mViewModel.marvelListCharacters.value

            // Check data in viewModel. If null make request
            if (listCharacter != null) {
                onSuccess(listCharacter)
            } else {
                mViewModel.updateData()
            }
        } else {
            onError()
        }
    }

    override fun onLoading() {
        mProgressBar.visibility = View.VISIBLE
    }

    override fun onSuccess(listCharacter: List<MarvelCharacter>) {
        mProgressBar.visibility = View.INVISIBLE
        mNestedScrollView.setScrollingEnabled(true)

        val listCharacterViews = mutableListOf<BaseView>()

        listCharacter.forEach { character ->
            listCharacterViews.add(AppViewFactory.getViewType(TYPE_SCREEN_HEROES, character))
        }
        (mAdapter as MainScreenAdapter).setData(listCharacterViews)
    }

        override fun onError() {
            mProgressBar.visibility = View.INVISIBLE
            mNestedScrollView.setScrollingEnabled(false)

            mNestedScrollView.goToTop()

            val listCharacterViews = mutableListOf<BaseView>()
            listCharacterViews.add(AppViewFactory.getViewType(TYPE_SCREEN_ERROR))
            (mAdapter as MainScreenAdapter).setData(listCharacterViews)
        }
    }