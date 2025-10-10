package com.example.mypsychologist.ui.education.educationFragment

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentEducationBinding
import com.example.mypsychologist.domain.entity.educationEntity.EducationsEntity
import com.example.mypsychologist.domain.entity.educationEntity.ItemMaterialEntity
import com.example.mypsychologist.domain.entity.educationEntity.TopicEntity
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.education.educationFragment.EducationScreenState
import com.example.mypsychologist.presentation.education.educationFragment.EducationViewModel
import com.example.mypsychologist.presentation.education.educationFragment.MarsAsCompleteStatus
import com.example.mypsychologist.ui.core.autoCleared
import com.example.mypsychologist.ui.core.composeComponents.PlaceholderError
import com.example.mypsychologist.ui.core.composeComponents.PrimaryTextButton
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
        viewModel.getMaterial(requireArguments().getString(TOPIC_TAG).toString())
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

        viewModel.marsAsCompleteStatus.observe(viewLifecycleOwner) { status ->
            renderStatus(status)
        }

        binding.cardsRw.setContent {
            AppTheme {
                EducationContent(
                    viewModel,
                    onBtnClick = {
                        val taskId = requireArguments().getString(TASK_ID)
                        if (!taskId.isNullOrEmpty())
                            viewModel.markAsCompleteTask(taskId)
                        else
                            findNavController().popBackStack()
                    }
                )
            }
        }

        return binding.root
    }

    private fun renderStatus(status: MarsAsCompleteStatus) {
        when (status) {
            is MarsAsCompleteStatus.Error -> requireContext().showToast(status.msg)
            MarsAsCompleteStatus.Success -> findNavController().popBackStack()
        }
    }

    @Composable
    private fun EducationContent(viewModel: EducationViewModel, onBtnClick: () -> Unit) {
        val viewState = viewModel.screenState.collectAsState()
        when(val result = viewState.value){
            is EducationScreenState.Content -> EducationRenderContent(result.data, onBtnClick)
            is EducationScreenState.Error -> PlaceholderError()
            EducationScreenState.Initial -> Unit
            EducationScreenState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }

    }

    @Composable
    private fun EducationRenderContent(data: TopicEntity, onBtnClick: () -> Unit) {

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                item {
                    Text(
                        style = AppTheme.typography.titleCygreFont,
                        text = data.theme,
                        color = AppTheme.colors.primaryText
                    )
                }
                items (data.educationMaterials) { subTopic ->
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
    private fun EducationItem(item: EducationsEntity) {
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
                    placeholder = ColorPainter(color = AppTheme.colors.loading),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(12.dp))
                        .fillMaxWidth()
//                        .aspectRatio(1/0.66f),
                )
                Box(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
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

    @Composable
    @Preview(showBackground = true)
    fun TestsContentPreview() {
        val data =
            TopicEntity(
                id = "isuahfiue",
                theme = "Основы КПТ",
                link = "",
                tags = listOf(
                    "7 минут",
                    "Образование"),
                educationMaterials = listOf(EducationsEntity(
                    id = "fwfe",
                    type = 0,
                    number = 1,
                    title = "",
                    subtitle =  "Что такое?",
                    cards = listOf(ItemMaterialEntity(
                        id = "dawdw",
                        text = "Предлагаем узнать побольше о самой методике когнитивно-поведенческой терапии (КПТ). " +
                                "Здесь собрана краткая, но ёмкая информация, которая поможет освоить метод самостоятельно или с психотерапевтом.",
                        number = 1,
                        linkToPicture =  "/images/images_education_material/img_2.png"
                    )),
                    linkToPicture =  "/images/images_education_material/img_2.png",
                ))
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