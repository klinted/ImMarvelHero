package com.friple.immarvelhero.ui.recyclerview.viewholders

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.friple.immarvelhero.R
import com.friple.immarvelhero.ui.recyclerview.views.BaseView
import com.friple.immarvelhero.utilits.APP_ACTIVITY
import com.friple.immarvelhero.utilits.ID_OF_HERO
import com.friple.immarvelhero.utilits.adapter.AppHeroClickListener
import com.friple.immarvelhero.utilits.adapter.makeRatingString
import com.friple.immarvelhero.utilits.adapter.makeRef
import com.friple.immarvelhero.utilits.adapter.randFloat
import com.friple.immarvelhero.utilits.downloadAndSetImage
import com.friple.immarvelhero.utilits.showToast

class HeroDetailViewHolder(view: View) : RecyclerView.ViewHolder(view), BaseViewHolder {

    private val mIvHeroPhoto: ImageView = view.findViewById(R.id.iv_hero_photo)
    private val mTvHeroName: TextView = view.findViewById(R.id.tv_hero_name)
    private val mTvBio: TextView = view.findViewById(R.id.tv_bio)
    private val mTvRating: TextView = view.findViewById(R.id.tv_rating_in_numb_hero)
    private val mRbOfHeroDetail: RatingBar = view.findViewById(R.id.rb_of_hero_detail)
    private val mCvMainBackCard: CardView = view.findViewById(R.id.cv_main_back_card)

    override fun drawMarvelHero(view: BaseView) {

        mIvHeroPhoto.downloadAndSetImage(makeRef(view))
        mTvHeroName.text = view.name

        mTvBio.text = if (view.description.isEmpty()) {
            APP_ACTIVITY.getString(R.string.standard_bio)
        } else {
            view.description
        }

        mRbOfHeroDetail.rating = randFloat(0f, 5f)
        mTvRating.text = makeRatingString(mRbOfHeroDetail.rating)

        // Set anim for cardView
        mCvMainBackCard.animation =
            AnimationUtils.loadAnimation(APP_ACTIVITY, android.R.anim.slide_in_left)
    }

    override fun onAttach(view: BaseView, listener: AppHeroClickListener) {
        APP_ACTIVITY.mToolbar.setNavigationOnClickListener {
            listener.onClickFromItem()
        }
    }

    override fun onDetach() {
    }
}