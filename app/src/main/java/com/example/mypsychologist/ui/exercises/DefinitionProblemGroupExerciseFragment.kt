package com.example.mypsychologist.ui.exercises

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.databinding.FragmentDefinitionProblemGroupExerciseBinding
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.di.MultiViewModelFactory
import com.example.mypsychologist.presentation.exercises.definitionProblemGroupExerciseFragment.DefinitionProblemGroupExerciseContent
import com.example.mypsychologist.presentation.exercises.definitionProblemGroupExerciseFragment.DefinitionProblemGroupExerciseViewModel
import com.example.mypsychologist.presentation.exercises.exercisesFragment.SaveStatus
import com.example.mypsychologist.ui.core.PrimaryTextButton
import com.example.mypsychologist.ui.core.PrimaryTextField
import com.example.mypsychologist.ui.theme.AppTheme
import javax.inject.Inject

class DefinitionProblemGroupExerciseFragment : Fragment() {

    private var _binding: FragmentDefinitionProblemGroupExerciseBinding? = null
    private val binding: FragmentDefinitionProblemGroupExerciseBinding
        get() = requireNotNull(
            _binding
        )

    @Inject
    lateinit var vmFactory: MultiViewModelFactory
    private val viewModel: DefinitionProblemGroupExerciseViewModel by lazy {
        ViewModelProvider(this, vmFactory)[DefinitionProblemGroupExerciseViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().exercisesComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDefinitionProblemGroupExerciseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    private fun initView() {
        binding.toolbar.toolbar.title = getString(R.string.group_problem)
        binding.content.setContent {
            AppTheme {
                Scaffold {
                    ExerciseScreen(
                        viewModel = viewModel,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = it.calculateBottomPadding())
                            .background(color = AppTheme.colors.screenBackground)
                    )
                }
            }
        }
    }

    @Composable
    private fun ExerciseScreen(
        modifier: Modifier,
        viewModel: DefinitionProblemGroupExerciseViewModel,
    ) {
        val saveStatus = viewModel.statusSave.collectAsStateWithLifecycle()
        when(val res =saveStatus.value){
            is SaveStatus.Error -> {
                Log.e("Error", res.msg)
                requireContext().showToast(getString(R.string.unknown_error_placeholder))
            }
            SaveStatus.Init -> Unit
            SaveStatus.Success -> findNavController().popBackStack()
        }
        val viewState = viewModel.screenState.collectAsStateWithLifecycle().value

        ExerciseContent(modifier = modifier.padding(horizontal = 16.dp),
            content = viewState,
            onScopeFieldChange = { viewModel.changeScopeField(it) },
            onEmotionFieldChange = { viewModel.changeEmotionField(it) },
            onTargetFieldChange = { viewModel.changeTargetField(it) },
            onSaveResultClick = { viewModel.saveResult() })
    }

    @Composable
    private fun ExerciseContent(
        content: DefinitionProblemGroupExerciseContent,
        onScopeFieldChange: (String) -> Unit,
        onEmotionFieldChange: (String) -> Unit,
        onTargetFieldChange: (String) -> Unit,
        onSaveResultClick: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.padding(top = 30.dp))

            QuestionItem(questionId = R.string.group_problem_q_1,
                hintId = R.string.group_problem_hint_1,
                textInput = content.scopeField,
                descId = R.string.group_problem_desc_1,
                fieldErrorStr = content.scopeFieldErrorId?.let { stringResource(id = it) },
                onFieldChange = { onScopeFieldChange(it) })

            Spacer(modifier = Modifier.padding(top = 40.dp))

            QuestionItem(questionId = R.string.group_problem_q_2,
                hintId = R.string.group_problem_hint_2,
                textInput = content.emotionField,
                descId = R.string.group_problem_desc_2,
                fieldErrorStr = content.emotionFieldErrorId?.let { stringResource(id = it) },
                onFieldChange = { onEmotionFieldChange(it) })

            Spacer(modifier = Modifier.padding(top = 40.dp))

            QuestionItem(questionId = R.string.group_problem_q_3,
                hintId = R.string.group_problem_hint_3,
                textInput = content.targetField,
                descId = R.string.empty,
                fieldErrorStr = content.targetFieldErrorId?.let { stringResource(id = it) },
                onFieldChange = { onTargetFieldChange(it) })

            Spacer(modifier = Modifier.padding(top = 20.dp))

            PrimaryTextButton(textString = stringResource(id = R.string.save),
                onClick = { onSaveResultClick() })
        }
    }

    @Composable
    private fun QuestionItem(
        questionId: Int,
        hintId: Int,
        textInput: String,
        descId: Int,
        fieldErrorStr: String?,
        onFieldChange: (String) -> Unit,
    ) {

        Text(
            text = stringResource(id = questionId),
            style = AppTheme.typography.titleXS,
            color = AppTheme.colors.primaryText
        )

        Spacer(modifier = Modifier.padding(top = 20.dp))

        PrimaryTextField(
            field = textInput,
            placeHolderText = stringResource(id = hintId),
            onFieldChange = { onFieldChange(it) },
            errorStr = fieldErrorStr,
            keyboardOptions = KeyboardOptions(
                imeAction = if (questionId == R.string.group_problem_q_3) ImeAction.Done
                else ImeAction.Next
            )
        )

        if (descId != R.string.empty) {
            Spacer(modifier = Modifier.padding(top = 10.dp))

            Text(
                text = stringResource(id = descId),
                style = AppTheme.typography.bodyM,
                color = AppTheme.colors.primaryText
            )
        }
    }

    @Composable
    @Preview(showBackground = true)
    private fun ExerciseContent_Preview() {
        AppTheme {
            ExerciseContent(content = DefinitionProblemGroupExerciseContent(),
                onSaveResultClick = {},
                onTargetFieldChange = {},
                onEmotionFieldChange = {},
                onScopeFieldChange = {})
        }
    }
}