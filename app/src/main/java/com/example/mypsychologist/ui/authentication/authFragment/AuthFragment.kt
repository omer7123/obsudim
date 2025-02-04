package com.example.mypsychologist.ui.authentication.authFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.mypsychologist.NavbarHider
import com.example.mypsychologist.R
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.authentication.authFragment.AuthContent
import com.example.mypsychologist.presentation.authentication.authFragment.AuthViewModel
import com.example.mypsychologist.presentation.di.MultiViewModelFactory
import com.example.mypsychologist.ui.core.PrimarySecurityTextField
import com.example.mypsychologist.ui.core.PrimaryTextButton
import com.example.mypsychologist.ui.core.PrimaryTextField
import com.example.mypsychologist.ui.core.SecondaryTextButton
import com.example.mypsychologist.ui.theme.AppTheme
import javax.inject.Inject

class AuthFragment : Fragment() {
    private var navbarHider: NavbarHider? = null

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory
    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().authenticationComponent().create().inject(this)

        if (context is NavbarHider) {
            navbarHider = context
            navbarHider!!.setNavbarVisibility(false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = ComposeView(context = requireContext()).apply {
        setContent {
            AppTheme {
                AuthScreen(viewModel, findNavController())
            }
        }
    }

    @Composable
    fun AuthScreen(viewModel: AuthViewModel, navController: NavController) {
        val viewState = viewModel.stateScreen.collectAsState()
        val res = viewState.value
        if (res.success) {
            navController.navigate(R.id.action_authFragment_to_main_fragment)
        }
        else {
            AuthInitial(
                email = res.email,
                password = res.password,
                res,
                onEmailChange = { email ->
                    viewModel.emailChange(email)
                },
                onPasswordChange = { password ->
                    viewModel.passwordChange(password)
                },
                onSubmitClick = {
                    viewModel.auth()
                },
                onRegisterClick = {
                    navController.navigate(R.id.action_authFragment_to_registrationFragment)
                }
            )
        }
    }

    @Composable
    fun AuthInitial(
        email: String,
        password: String,
        res: AuthContent,
        onEmailChange: (String) -> Unit,
        onPasswordChange: (String) -> Unit,
        onSubmitClick: () -> Unit,
        onRegisterClick: () -> Unit
    ) {
        if (!res.error.isNullOrEmpty()) {
            requireContext().showToast(res.error)
            viewModel.removeError()
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.ic_auth_back),
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )

            Text(
                modifier = Modifier.padding(start = 16.dp, top = 64.dp),
                text = stringResource(R.string.have_you_account),
                style = AppTheme.typography.titleCygreFont,
                color = AppTheme.colors.primaryTextInvert,
            )

            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .align(Alignment.BottomCenter)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topEnd = 28.dp, topStart = 28.dp)
                    )
                    .padding(horizontal = 16.dp)
                    .imePadding()
            ) {
                PrimaryTextField(
                    field = email,
                    placeHolderText = stringResource(id = R.string.mail),
                    onFieldChange = { newEmail ->
                        onEmailChange(newEmail)
                    },
                    imeAction = ImeAction.Next,
                    modifier = Modifier.padding(top = 16.dp)
                )

                PrimarySecurityTextField(
                    field = password,
                    placeHolderText = stringResource(id = R.string.password),
                    onFieldChange = { newPass ->
                        onPasswordChange(newPass)
                    },
                    imeAction = ImeAction.Done,
                    modifier = Modifier.padding(top = 16.dp)
                )

                PrimaryTextButton(
                    textString = stringResource(id = R.string.log_in),
                    isLoading = res.loading,
                    onClick = onSubmitClick,
                    modifier = Modifier.padding(top = 30.dp)
                )

                SecondaryTextButton(
                    textString = stringResource(id = R.string.register),
                    onClick = {
                        onRegisterClick()
                    },
                    modifier = Modifier.padding(top = 16.dp, bottom = 52.dp)
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun AuthInitial_Preview() {
        AppTheme {
            AuthInitial(email = "",
                password = "",
                res = AuthContent(loading = true),
                onEmailChange = {},
                onPasswordChange = {},
                onSubmitClick = {},
                onRegisterClick = {})
        }
    }

    override fun onDetach() {
        navbarHider?.setNavbarVisibility(true)
        navbarHider = null
        super.onDetach()
    }
}