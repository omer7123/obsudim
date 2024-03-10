package com.example.mypsychologist.ui.feed

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypsychologist.*
import com.example.mypsychologist.databinding.FragmentFeedBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.isNetworkConnect
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.FeedViewModel
import com.example.mypsychologist.presentation.ListScreenState
import com.example.mypsychologist.ui.MainAdapter
import com.example.mypsychologist.ui.autoCleared
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FeedFragment : Fragment() {

    private var binding: FragmentFeedBinding by autoCleared()

    @Inject
    lateinit var vmFactory: FeedViewModel.Factory
    private val viewModel: FeedViewModel by viewModels { vmFactory }

    private lateinit var mainAdapter: MainAdapter

    private var navbarHider: NavbarHider? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().feedComponent().create().inject(this)

        if (context is NavbarHider) {
            navbarHider = context
            navbarHider!!.setNavbarVisibility(false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(inflater, container, false)

        setupListeners()

        setupAdapter()

        viewModel.screenState
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        return binding.root
    }

    private fun setupAdapter() {
        binding.feedRw.apply {
            layoutManager = LinearLayoutManager(requireContext())
            mainAdapter = MainAdapter().apply {
                addDelegate(
                    FeedDelegate(viewModel::onLikeClick)
                )
            }
            adapter = mainAdapter
            setHasFixedSize(true)
        }
    }

    private fun render(state: ListScreenState) {
        when(state) {
            is ListScreenState.Loading -> {
                if (isNetworkConnect())
                    binding.swipeLayout.isRefreshing = true
                else
                    requireContext().showToast(getString(R.string.network_error))
            }
            is ListScreenState.Data -> {
                binding.swipeLayout.isRefreshing = false
                Log.d("aaaa", state.items.toString())
                mainAdapter.submitList(state.items)
            }
            is ListScreenState.Error -> {
                binding.swipeLayout.isRefreshing = false
                requireContext().showToast(getString(R.string.db_error))
            }
            is ListScreenState.Init -> Unit
        }
    }

    private fun setupListeners() {
        binding.includeToolbar.toolbar.apply {
            title = getString(R.string.feed_of_proud)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        binding.sendButton.setOnClickListener {
            binding.messageEditText.text.toString().let { text ->
                if (text.isNotEmpty()) {
                    viewModel.sendMessage(text)
                    binding.messageEditText.setText("")
                }
                else
                    requireContext().showToast(getString(R.string.necessary_to_fill))
            }
        }

        binding.swipeLayout.setOnRefreshListener {
            viewModel.loadItems()
        }
    }

    override fun onDetach() {
        navbarHider?.setNavbarVisibility(true)
        navbarHider = null
        super.onDetach()
    }
}