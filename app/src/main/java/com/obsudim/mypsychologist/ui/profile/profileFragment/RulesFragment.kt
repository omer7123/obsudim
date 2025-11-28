package com.obsudim.mypsychologist.ui.profile.profileFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.obsudim.mypsychologist.databinding.FragmentRulesBinding
import com.obsudim.mypsychologist.extensions.getAppComponent
import com.obsudim.mypsychologist.ui.core.autoCleared

class RulesFragment : Fragment() {

    private var binding: FragmentRulesBinding by autoCleared()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().profileComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRulesBinding.inflate(inflater, container, false)

        binding.include.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }
}