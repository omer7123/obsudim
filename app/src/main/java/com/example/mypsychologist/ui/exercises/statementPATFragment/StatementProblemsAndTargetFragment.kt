package com.example.mypsychologist.ui.exercises.statementPATFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.presentation.di.MultiViewModelFactory
import com.example.mypsychologist.presentation.exercises.statementProblemsAndTargetFragment.ScreenPage
import com.example.mypsychologist.presentation.exercises.statementProblemsAndTargetFragment.StatementProblemsAndTargetViewModel
import com.example.mypsychologist.ui.theme.AppTheme
import javax.inject.Inject

class StatementProblemsAndTargetFragment : Fragment() {

    @Inject
    lateinit var vmFactory: MultiViewModelFactory
    private val viewModel: StatementProblemsAndTargetViewModel by lazy {
        ViewModelProvider(this, vmFactory)[StatementProblemsAndTargetViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().exercisesComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply{
        setContent {
            Scaffold {
                AppTheme {
                    StatementProblemsAndTargetScreen(
                        viewModel = viewModel,
                        navController = findNavController(),
                        modifier = Modifier
                            .padding(
                                top = it.calculateTopPadding(),
                                end = it.calculateBottomPadding()
                            )
                    )
                }
            }
        }
    }

    @Composable
    fun StatementProblemsAndTargetScreen(
        viewModel: StatementProblemsAndTargetViewModel,
        navController: NavController,
        modifier: Modifier = Modifier
    ){
        val viewState = viewModel.screenState.collectAsStateWithLifecycle()
        when(viewState.value.screenPage){
            ScreenPage.CHOICE_SPHERE -> ChoiceSpherePage(viewState.value.choseSphere, viewState.value.listSphere)
            ScreenPage.CHOICE_EMOTION -> TODO()
            ScreenPage.CHOICE_TARGET -> TODO()
        }
    }

    @Composable
    fun ChoiceSpherePage(
        choseSphere: String,
        listSphere: List<String>
    ){
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(listSphere){
                ItemSelecable(it, choseSphere)
            }
        }
    }

    @Composable
    fun ItemSelecable(title: String, choseSphere: String) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)){

            Text(
                text = title,
                style = AppTheme.typography.bodyM,

                )
        }
    }

}