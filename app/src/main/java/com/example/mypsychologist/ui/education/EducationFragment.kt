package com.example.mypsychologist.ui.education

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentEducationBinding
import com.example.mypsychologist.domain.entity.educationEntity.EducationsEntity
import com.example.mypsychologist.domain.entity.educationEntity.ItemMaterialEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.education.EducationScreenState
import com.example.mypsychologist.presentation.education.EducationViewModel
import com.example.mypsychologist.presentation.education.MarsAsCompleteStatus
import com.example.mypsychologist.ui.autoCleared
import com.example.mypsychologist.ui.core.PrimaryTextButton
import com.example.mypsychologist.ui.theme.AppTheme
import javax.inject.Inject

class EducationFragment : Fragment() {

    private var binding: FragmentEducationBinding by autoCleared()

    @Inject
    lateinit var vmFactory: EducationViewModel.Factory
    private val viewModel: EducationViewModel by viewModels { vmFactory }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().educationComponent().create().inject(this)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEducationBinding.inflate(inflater, container, false)

        binding.includeToolbar.toolbar.apply {
            setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            render(state)
        }
        viewModel.marsAsCompleteStatus.observe(viewLifecycleOwner) { status ->
            renderStatus(status)
        }

        return binding.root
    }

    private fun renderStatus(status: MarsAsCompleteStatus) {
        when (status) {
            is MarsAsCompleteStatus.Error -> requireContext().showToast(status.msg)
            MarsAsCompleteStatus.Success -> findNavController().popBackStack()
        }
    }

    private fun render(state: EducationScreenState) {
        when (state) {
            is EducationScreenState.Content -> setupContent(state.data)
            is EducationScreenState.Error -> {
                requireContext().showToast(state.msg)
            }

            EducationScreenState.Initial -> Unit
            EducationScreenState.Loading -> Unit
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMaterial(requireArguments().getString(TOPIC_TAG).toString())
    }


    private fun setupContent(data: EducationsEntity) {

        binding.title.text = data.theme

        val resultList = ArrayList<ItemMaterialEntity>()

        for (subTopic in data.materials) {
            for (card in subTopic.cards) {
                resultList.add(card)
            }
        }

        binding.cardsRw.setContent {
            AppTheme {
                EducationContent(
                    resultList,
                    onBtnClick = {
                        markTaskAsCompleted()
                    }
                )
            }
        }
    }

    @Composable
    private fun EducationContent(data: List<ItemMaterialEntity>, onBtnClick: () -> Unit) {
        Column {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                items(data) {
                    EducationItem(it)
                }
                item {
                    PrimaryTextButton(
                        modifier = Modifier.padding(top = 20.dp),
                        textString = stringResource(R.string.all_right),
                        onClick = {
                            onBtnClick()
                        }
                    )
                }
            }
        }
    }

    @Composable
    private fun EducationItem(item: ItemMaterialEntity) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = AppTheme.colors.fourthBackground,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = item.text,
                style = AppTheme.typography.bodyL,
                color = AppTheme.colors.primaryText
            )
        }
    }

    private fun markTaskAsCompleted() {
        requireArguments().getString(TASK_ID)?.let { taskId ->
            viewModel.markAsCompleteTask(taskId)
        }
    }

    @Composable
    @Preview(showBackground = true)
    fun TestsContentPreview() {
        val data = listOf(
            ItemMaterialEntity(
                id = "",
                text = "Предлагаем узнать побольше о методике \n" +
                        "когнитивно-поведенческой терапии - КПТ. \n" +
                        "\n" +
                        "Здесь собрана краткая, но ёмкая информация, которая поможет освоить метод самостоятельно или с психотерапевтом."
            ), ItemMaterialEntity(
                id = "",
                text = "Метод позволяет достаточно быстро достичь эффекта и сохранить его после завершения терапии. За счет работы с проблемами, которые волнуют сейчас, без подробного погружения в прошлое."
            ), ItemMaterialEntity(
                id = "",
                text = "Предлагаем узнать побольше о методике \n" +
                        "когнитивно-поведенческой терапии - КПТ. \n" +
                        "\n" +
                        "Здесь собрана краткая, но ёмкая информация, которая поможет освоить метод самостоятельно или с психотерапевтом."
            )

        )
        AppTheme {
            EducationContent(data, {})
        }
    }

    companion object {
        const val TOPIC_TAG = "tag"
        const val TASK_ID = "task_id"
    }
}