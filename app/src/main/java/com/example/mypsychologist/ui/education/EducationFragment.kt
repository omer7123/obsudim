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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentEducationBinding
import com.example.mypsychologist.domain.entity.educationEntity.EducationsEntity
import com.example.mypsychologist.domain.entity.educationEntity.ItemMaterialEntity
import com.example.mypsychologist.domain.entity.educationEntity.SubtopicEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.education.EducationScreenState
import com.example.mypsychologist.presentation.education.EducationViewModel
import com.example.mypsychologist.presentation.education.MarsAsCompleteStatus
import com.example.mypsychologist.ui.autoCleared
import com.example.mypsychologist.ui.core.PrimaryTextButton
import com.example.mypsychologist.ui.theme.AppTheme
import kotlinx.coroutines.launch
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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.screenState.collect { state ->
                    render(state)
                }
            }
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

        binding.cardsRw.setContent {
            AppTheme {
                EducationContent(
                    viewModel,
                    onBtnClick = {
                        markTaskAsCompleted()
                    }
                )
            }
        }
    }

    @Composable
    private fun EducationContent(viewModel: EducationViewModel, onBtnClick: () -> Unit) {
        val viewState = viewModel.screenState.collectAsState()
        when(val result = viewState.value){
            is EducationScreenState.Content -> EducationRenderContent(result.data, onBtnClick)
            is EducationScreenState.Error -> TODO()
            EducationScreenState.Initial -> TODO()
            EducationScreenState.Loading -> TODO()
        }

    }

    @Composable
    private fun EducationRenderContent(data: EducationsEntity, onBtnClick: () -> Unit) {

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                items(data.materials) { subTopic->
                    EducationItem(subTopic)
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

    @Composable
    private fun EducationItem(item: SubtopicEntity) {
        Column {

            Text(
                style = AppTheme.typography.titleXS,
                modifier = Modifier.padding(bottom = 20.dp),
                text = item.subtitle,
                color = AppTheme.colors.primaryText
            )

            for (card in item.cards){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(card.linkToPicture).build(),
                    contentDescription = card.text,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(12.dp))
                        .fillMaxWidth(),
                )
                Box(
                    modifier = Modifier.padding(vertical = 10.dp)
                        .fillMaxWidth()
                        .background(
                            color = AppTheme.colors.fourthBackground,
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = card.text,
                        style = AppTheme.typography.bodyL,
                        color = AppTheme.colors.primaryText
                    )
                }
            }
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
        val data =
            EducationsEntity(
                theme = "",
                maxScore = 0,
                materials = listOf(
                    SubtopicEntity(
                        "Подзаголовок",
                        cards = listOf(
                            ItemMaterialEntity(
                                id = "",
                                linkToPicture = "https://xn--b1afb6bcb.xn--c1ajjlbco7a.xn----gtbbcb4bjf2ak.xn--p1ai/education/images_education_material/сbt_base_41.png",
                                text = "Предлагаем узнать побольше о методике \n" +
                                        "когнитивно-поведенческой терапии - КПТ. \n" +
                                        "\n" +
                                        "Здесь собрана краткая, но ёмкая информация, которая поможет освоить метод самостоятельно или с психотерапевтом."
                            ), ItemMaterialEntity(
                                id = "",
                                linkToPicture = "https://xn--b1afb6bcb.xn--c1ajjlbco7a.xn----gtbbcb4bjf2ak.xn--p1ai/education/images_education_material/сbt_base_41.png",
                                text = "Метод позволяет достаточно быстро достичь эффекта и сохранить его после завершения терапии. За счет работы с проблемами, которые волнуют сейчас, без подробного погружения в прошлое."
                            ), ItemMaterialEntity(
                                id = "",
                                linkToPicture = "https://xn--b1afb6bcb.xn--c1ajjlbco7a.xn----gtbbcb4bjf2ak.xn--p1ai/education/images_education_material/сbt_base_41.png",
                                text = "Предлагаем узнать побольше о методике \n" +
                                        "когнитивно-поведенческой терапии - КПТ. \n" +
                                        "\n" +
                                        "Здесь собрана краткая, но ёмкая информация, которая поможет освоить метод самостоятельно или с психотерапевтом."
                            )
                        )
                    )
                )

        )
        AppTheme {
            EducationRenderContent(data, {})
        }
    }

    companion object {
        const val TOPIC_TAG = "tag"
        const val TASK_ID = "task_id"
    }
}