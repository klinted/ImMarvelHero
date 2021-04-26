package com.friple.immarvelhero.ui.screens

import android.graphics.Bitmap
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.friple.immarvelhero.R
import com.friple.immarvelhero.databinding.ScreenHeroBinding
import com.friple.immarvelhero.ui.viewmodels.HeroScreenViewModel
import com.friple.immarvelhero.utilits.APP_ACTIVITY
import com.friple.immarvelhero.utilits.HERO_BITMAP
import com.friple.immarvelhero.utilits.ID_FOR_BITMAP

class HeroScreen : Fragment() {

    private lateinit var mBinding: ScreenHeroBinding

    private lateinit var mHeroViewModel: HeroScreenViewModel

    // Views
    private lateinit var mIvPhoto: ImageView
    private lateinit var mTvName: TextView
    private lateinit var mTvBio: TextView
    private lateinit var mRatingBar: RatingBar
    private lateinit var mRatingNum: TextView

    private lateinit var mNsvContainerMainHero: NestedScrollView
    private lateinit var mCvMainBackCard: CardView



    // REFACTOR: 4/25/2021 I MUST REFACTOR THIS CHAOS!!!

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

    lateinit var bitmapImage: Bitmap



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Make transition anim and set it
        val anim = TransitionInflater.from(context)
            .inflateTransition(android.R.transition.move)

        sharedElementEnterTransition = anim
        sharedElementReturnTransition = anim
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.screen_hero, container, false)

        initToolbar()

        mHeroViewModel = ViewModelProvider(this).get(HeroScreenViewModel::class.java)

        bitmapDeals()

        return mBinding.root
    }

    // Set name and home button
    private fun initToolbar() {
        APP_ACTIVITY.title = name

        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        APP_ACTIVITY.mToolbar.setNavigationOnClickListener {
            APP_ACTIVITY.supportFragmentManager.popBackStack()
        }
    }

    // If bitmap is not null we save it and set in to IV
    private fun bitmapDeals() {
        if (mHeroViewModel.getBitmap().value == null) {
            // Here bitmap we get from last screen
            mHeroViewModel.setBitmap(bitmapImage)
        }

        // Here we save it cause after change state of screen, bitmap sets null
        HERO_BITMAP = mHeroViewModel.getBitmap().value
        ID_FOR_BITMAP = idHero.toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFields()

        setTransitionNames()

        workWithData()
    }

    private fun initFields() {
        // Views
        mIvPhoto = mBinding.ivHeroPhoto
        mTvName = mBinding.tvHeroName
        mTvBio = mBinding.tvBio
        mRatingBar = mBinding.ratingBarOfHero
        mRatingNum = mBinding.tvRatingInNumbHero

        mNsvContainerMainHero = mBinding.nsvContainerMainHero
        mCvMainBackCard = mBinding.cvMainBackCard
    }

    private fun setTransitionNames() {
        mNsvContainerMainHero.transitionName = transitionName[0] // From cvMainBackCard
        mIvPhoto.transitionName = transitionName[1] // From ivPhotoOfHero

        // Set anim for cardView
        mCvMainBackCard.animation =
            AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
    }

    private fun workWithData() {
        // Save data from ViewModel
        val savedHeroData: Map<String, Any>? = mHeroViewModel.getHeroDate().value

        // Check it. If not null, we init views form ViewModel, else from arguments
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

            // And save it to ViewModel
            mHeroViewModel.setHeroDate(heroDate)
            // Init views
            initViews(mHeroViewModel.getHeroDate().value!!)
        }
    }

    private fun initViews(data: Map<String, Any>) {
        // Set bitmap image, which contains in ViewModel
        mIvPhoto.setImageBitmap(mHeroViewModel.getBitmap().value)

        // Set name of hero
        mTvName.text = data[NAME].toString()

        // Set rating text
        mRatingNum.text = getRatingText(data)

        // Set text to bio
        mTvBio.text = (if (data[BIO].toString().isEmpty()) {
            getString(R.string.standard_bio)
        } else {
            data[BIO]
        }).toString()

        // Set rating to RatingBar
        mRatingBar.rating = data[RATING].toString().toFloat()
    }

    // Make rating string
    private fun getRatingText(data: Map<String, Any>): CharSequence {
        return data[RATING].toString() + getString(R.string.rating)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Disable home button
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}