package com.example.mypsychologist.ui.diagnostics

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mypsychologist.databinding.FragmentPassingTestBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.presentation.di.MultiViewModelFactory
import com.example.mypsychologist.presentation.diagnostics.PassingTestViewModel
import javax.inject.Inject


class PassingTestFragment : Fragment() {
    private var _binding: FragmentPassingTestBinding? = null
    private val binding get() = requireNotNull(_binding)

    @Inject
    lateinit var vmFactory: MultiViewModelFactory
    private val viewModel: PassingTestViewModel by lazy {
        ViewModelProvider(this, vmFactory)[PassingTestViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().diagnosticComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPassingTestBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        Log.e("PASSTEST", requireArguments().getString(TITLE).toString())
    }

    companion object {
        const val TEST_ID = "test_id"
        const val TITLE = "title"
        const val DESCRIPTION = "description"

        @JvmStatic
        fun newInstance(testId: String, title: String, description: String) =
            PassingTestFragment().apply {
                arguments = bundleOf(
                    TEST_ID to testId,
                    TITLE to title,
                    DESCRIPTION to description,
                )
            }
    }
}