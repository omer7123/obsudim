package com.example.mypsychologist.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentFeedbackBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.main.FeedbackScreenState
import com.example.mypsychologist.presentation.main.FeedbackViewModel
import com.example.mypsychologist.ui.autoCleared
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FeedbackFragment : Fragment() {

    private var binding: FragmentFeedbackBinding by autoCleared()

    @Inject
    lateinit var vmFactory: FeedbackViewModel.Factory
    private val viewModel: FeedbackViewModel by viewModels { vmFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().profileComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFeedbackBinding.inflate(inflater, container, false)

        binding.includeToolbar.toolbar.apply {
            title = getString(R.string.feedback)
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        viewModel.state
            .flowWithLifecycle(lifecycle)
            .onEach { render(it) }
            .launchIn(lifecycleScope)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sendMessageTg.setOnClickListener {
            val telegramUrl = "https://t.me/desman4"

            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(telegramUrl))
                intent.setPackage("org.telegram.messenger") // Устанавливаем пакет Telegram
                startActivity(intent)
            } catch (e: Exception) {
                // Если Telegram не установлен, открываем ссылку через браузер
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(telegramUrl))
                startActivity(intent)
            }
        }

        binding.sendMessage.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:ilyafomin125@gmail.com"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Тема письма")  // Можно задать тему
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Текст письма...")  // Можно задать текст письма
            startActivity(Intent.createChooser(emailIntent, "Отправить письмо"))
        }
    }

    private fun render(state: FeedbackScreenState) {
        when (state) {
            is FeedbackScreenState.Loading -> Unit
            is FeedbackScreenState.Response -> {
//                binding.progressBar.isVisible = false
                if (state.result) {
                    requireContext().showToast(getString(R.string.success))
                    findNavController().popBackStack()
                } else {
                    requireContext().showToast(getString(R.string.db_error))
                }
            }

            is FeedbackScreenState.UserNameSaved -> {

            }

            is FeedbackScreenState.ValidationError -> {
            }

            is FeedbackScreenState.Init -> Unit
        }
    }


}

