package com.friple.immarvelhero.ui.screens

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.friple.immarvelhero.R
import com.friple.immarvelhero.databinding.ScreenHeroBinding
import com.friple.immarvelhero.ui.viewmodels.HeroScreenViewModel
import com.friple.immarvelhero.utilits.APP_ACTIVITY
import com.friple.immarvelhero.utilits.downloadAndSetImage
import kotlinx.android.synthetic.main.screen_hero.*

class HeroScreen : Fragment() {

    private lateinit var mBinding: ScreenHeroBinding

    private lateinit var mHeroViewModel: HeroScreenViewModel

    // Views
    private lateinit var mIvPhoto: ImageView
    private lateinit var mTvName: TextView
    private lateinit var mTvBio: TextView
    private lateinit var mRatingBar: RatingBar
    private lateinit var mRatingNum: TextView

    private val ID = "id"
    private val NAME = "name"
    private val PHOTO_URL = "photoUrl"
    private val EXTENSION = "extension"
    private val BIO = "bio"
    private val RATING = "rating"

    private val TRANSITION_NAME = "transitionName"

    private val idHero: Int
    get() = arguments?.getInt(ID)!!

    private val name: String
    get() = arguments?.getString(NAME)!!

    private val photoUrl: String
    get() = arguments?.getString(PHOTO_URL)!!

    private val extension: String
    get() = arguments?.getString(EXTENSION)!!

    private val bio: String
    get() = arguments?.getString(BIO)!!

    private val rating: Float
    get() = arguments?.getFloat(RATING)!!

    private val transitionName: ArrayList<String>
        get() = arguments?.getStringArrayList(TRANSITION_NAME)!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val anim = TransitionInflater.from(context)
            .inflateTransition(android.R.transition.move)


        sharedElementEnterTransition = anim
        sharedElementReturnTransition = anim

        APP_ACTIVITY.title = name

        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        APP_ACTIVITY.mToolbar.setNavigationOnClickListener {
            APP_ACTIVITY.supportFragmentManager.popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.screen_hero, container, false)

        mHeroViewModel = ViewModelProvider(this).get(HeroScreenViewModel::class.java)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: 4/25/2021 Check the transition alpha

        mBinding.nsvContainerMainHero.transitionName = transitionName[0]
        mBinding.ivHeroPhoto.transitionName = transitionName[1]

        initFields()

        cv_main_back_card.animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)

        val savedHeroData: Map<String, Any>? = mHeroViewModel.getHeroDate().value

        if (savedHeroData != null) {
            initViews(savedHeroData)
        } else {
            val heroDate = mutableMapOf<String, Any>()
            heroDate[ID] = idHero.toString()
            heroDate[NAME] = name
            heroDate[PHOTO_URL] = photoUrl
            heroDate[EXTENSION] = extension
            heroDate[BIO] = bio
            heroDate[RATING] = rating

            mHeroViewModel.setHeroDate(heroDate)
            initViews(mHeroViewModel.getHeroDate().value!!)
        }
    }

    private fun initFields() {
        mIvPhoto = mBinding.ivHeroPhoto
        mTvName = mBinding.tvHeroName
        mTvBio = mBinding.tvBio
        mRatingBar = mBinding.ratingBarOfHero
        mRatingNum = mBinding.tvRatingInNumbHero
    }

    private fun initViews(data: Map<String, Any>) {
        mIvPhoto.downloadAndSetImage("${data[PHOTO_URL]}/landscape_amazing.${data[EXTENSION]}")
        mTvName.text = data[NAME].toString()
        mRatingNum.text = getRatingText(data)

        mTvBio.text = (if (data[BIO].toString().isEmpty()) {
            "The avenger is busy with heroic deeds and has not yet written anything"
        } else {
            data[BIO]
        }).toString()

        mRatingBar.rating = data[RATING].toString().toFloat()
    }

    private fun getRatingText(data: Map<String, Any>): CharSequence {
        return data[RATING].toString() + " Rating"
    }

    override fun onDestroy() {
        super.onDestroy()
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}