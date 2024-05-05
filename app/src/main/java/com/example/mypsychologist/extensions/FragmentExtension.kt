package com.example.mypsychologist.extensions

import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.example.mypsychologist.ConnectionChecker
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.CardViewGroupBinding
import com.example.mypsychologist.databinding.SmallCardViewGroupBinding
import com.example.mypsychologist.domain.entity.ProblemAnalysisEntity
import com.example.mypsychologist.ui.exercises.cbt.FragmentHint


fun Fragment.isNetworkConnect(): Boolean =
    (requireActivity() as ConnectionChecker).isConnection()

fun Fragment.renderRebtBeliefType(it: String) =
    getString(
        when (it) {
            ProblemAnalysisEntity::dogmaticRequirement.name -> R.string.dogmatic_requirement
            ProblemAnalysisEntity::dramatization.name -> R.string.dramatization
            ProblemAnalysisEntity::lft.name -> R.string.LFT
            ProblemAnalysisEntity::humiliatingRemarks.name -> R.string.humiliating_remarks
            ProblemAnalysisEntity::flexiblePreference.name -> R.string.flexible_preference
            ProblemAnalysisEntity::perspective.name -> R.string.perspective
            ProblemAnalysisEntity::hft.name -> R.string.HFT
            else -> R.string.unconditional_acceptance
        }
    )

fun Fragment.showHint(titleId: Int, hintId: Int) {
    FragmentHint.newInstance(titleId, hintId)
        .show(childFragmentManager, "tag")
}

fun Fragment.setupCard(
    card: CardViewGroupBinding,
    titleRes: Int,
    descriptionRes: Int,
    imageRes: Int? = null,
    backgroundRes: Int? = null
) {
    card.apply {
        cardTitle.text = getString(titleRes)
        cardDescription.text = getString(descriptionRes)
        imageRes?.let { cardImage.setImageResource(it) }
        backgroundRes?.let {
            this.card.background =
                AppCompatResources.getDrawable(requireContext(), it)
        }
    }
}

fun Fragment.setupSmallCard(
    card: SmallCardViewGroupBinding,
    titleRes: Int,
    imageRes: Int? = null
) {
    card.apply {
        cardTitle.text = getString(titleRes)
        imageRes?.let { cardImage.setImageResource(it) }
    }
}