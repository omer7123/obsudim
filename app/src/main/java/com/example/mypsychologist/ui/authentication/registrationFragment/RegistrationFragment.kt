package com.example.mypsychologist.ui.authentication.registrationFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.authentication.registrationFragment.RegisterContent
import com.example.mypsychologist.presentation.authentication.registrationFragment.RegisterStatus
import com.example.mypsychologist.presentation.authentication.registrationFragment.RegisterViewModel
import com.example.mypsychologist.presentation.authentication.registrationFragment.StepScreen
import com.example.mypsychologist.presentation.di.MultiViewModelFactory
import com.example.mypsychologist.ui.core.PrimaryTextButton
import com.example.mypsychologist.ui.core.PrimaryTextField
import com.example.mypsychologist.ui.theme.AppTheme
import javax.inject.Inject

class RegistrationFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[RegisterViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().authenticationComponent().create().inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )= ComposeView(requireContext()).apply {
        setContent {
            AppTheme{
                RegistrationScreen(viewModel, findNavController())
            }
        }
    }

    @Composable
    fun RegistrationScreen(
        viewModel: RegisterViewModel,
        navController: NavController
    ) {
        val viewState = viewModel.stateScreen.collectAsState()
        val registerStatus = viewModel.statusRegistration.collectAsState()

        when(val res = registerStatus.value){
            is RegisterStatus.Error -> requireContext().showToast(res.msg)
            RegisterStatus.Initial -> Unit
            RegisterStatus.Loading -> {
                Box {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            RegisterStatus.Success -> {
                navController.navigate(R.id.action_registrationFragment_to_startBoardFragment)
            }
        }

        Content(viewState)
    }

    @Composable
    private fun Content(viewState: State<RegisterContent>) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.ic_auth_back),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(
                        color = AppTheme.colors.screenBackground,
                        shape = RoundedCornerShape(topEnd = 28.dp, topStart = 28.dp)
                    )
                    .imePadding()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .padding(bottom = 52.dp)
            ) {
                ChangeScreen(
                    viewState,
                )
            }
        }
    }

    @Composable
    private fun ChangeScreen(viewState: State<RegisterContent>, modifier: Modifier = Modifier) {
        val content = viewState.value
        when(content.step){
            StepScreen.PersonalScreen -> PersonalDataScreen(content)
            StepScreen.RegistrationScreen -> RegistrationDataScreen(content)
        }
    }

    @Composable
    private fun PersonalDataScreen(value: RegisterContent) {
        Text(
            text = stringResource(id = R.string.enter_your_data),
            style = AppTheme.typography.titleXS,
            color = AppTheme.colors.primaryText,
        )
        PrimaryTextField(
            field = value.name,
            placeHolderText = stringResource(id = R.string.name),
            onFieldChange = {},
            imeAction = ImeAction.Next,
            modifier = Modifier.padding(top = 30.dp)
        )
        PrimaryTextField(
            field = value.name,
            placeHolderText = stringResource(id = R.string.name),
            onFieldChange = {},
            imeAction = ImeAction.Next,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        PrimaryTextField(
            field = value.name,
            placeHolderText = stringResource(id = R.string.name),
            onFieldChange = {},
            imeAction = ImeAction.Next,
        )
        PrimaryTextField(
            field = value.name,
            placeHolderText = stringResource(id = R.string.name),
            onFieldChange = {},
            imeAction = ImeAction.Next,
            modifier = Modifier.padding(top = 16.dp)
        )
        PrimaryTextButton(
            textString = stringResource(id = R.string.next),
            onClick = {},
            modifier = Modifier.padding(top = 30.dp)
        )
    }

    @Composable
    private fun RegistrationDataScreen(value: RegisterContent) {

    }

    @Preview
    @Composable
    fun Content_Preview(){
        AppTheme {
            Content(
                viewState = remember {
                    mutableStateOf(RegisterContent(step = StepScreen.PersonalScreen))
                }
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }


    private fun initListener() {
//        binding.registrationButton.setOnClickListener {
//            val email = binding.mailEt.text.toString()
//            val password = binding.password.text.toString()
//            val confirmPassword = binding.repeatPassword.text.toString()
//
//            viewModel.register(OldRegister(email, "", password, confirmPassword))
//        }
//
//        binding.goToLoginButton.setOnClickListener {
//            findNavController().navigate(R.id.action_registrationFragment_to_authFragment)
//        }
    }

    private fun render(state: RegisterContent) {
//        when (state) {
//            is RegisterContent.Error -> renderError(state.msg)
//            is RegisterContent.Success -> renderSuccess()
//            is RegisterContent.SuccessAuth -> renderSuccessAuth()
//            RegisterContent.Initial -> {}
//            RegisterContent.Loading -> renderLoading()
//            is RegisterContent.Content -> renderContent(state)
//        }
    }

    private fun renderSuccessAuth() {
//        binding.progressCircular.isVisible = false
//        findNavController().navigate(R.id.action_registrationFragment_to_main_fragment)
    }

//    private fun renderContent(state: RegisterContent.Content) {
//        if (state.email)
//            binding.inputMailLayout.bounce()
//
//        if (state.password)
//            binding.inputPasswordLayout.bounce()
//
//        if (state.confirmPassword)
//            binding.repeatPasswordLayout.bounce()
//    }

    private fun renderError(msg: String) {
//        binding.content.isVisible = true
//        binding.progressCircular.isVisible = false
        requireContext().showToast(msg)
    }

    private fun renderLoading() {
//        binding.content.isVisible = false
//        binding.progressCircular.isVisible = true
    }

    private fun renderSuccess() {
//        binding.progressCircular.isVisible = false
        findNavController().navigate(R.id.action_registrationFragment_to_startBoardFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
//        _binding = null
    }
}