package com.obsudim.mypsychologist.extensions

import androidx.fragment.app.Fragment
import com.obsudim.mypsychologist.ConnectionChecker
import com.obsudim.mypsychologist.databinding.LayoutCardPracticeBinding


fun Fragment.isNetworkConnect(): Boolean =
    (requireActivity() as ConnectionChecker).isConnection()


fun Fragment.setupCardPractice(
    card: LayoutCardPracticeBinding,
    titleRes: Int,
    imageRes: Int,
){
    card.apply {
        titleTv.text = getString(titleRes)
        imageRes?.let { imageIv.setImageResource(it) }
    }
}