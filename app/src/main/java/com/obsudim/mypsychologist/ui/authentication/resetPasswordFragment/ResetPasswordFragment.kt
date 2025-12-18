package com.obsudim.mypsychologist.ui.authentication.resetPasswordFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.obsudim.mypsychologist.R
import com.obsudim.mypsychologist.extensions.getAppComponent
import com.obsudim.mypsychologist.presentation.authentication.resetPasswordFragment.ResetPasswordScreenState
import com.obsudim.mypsychologist.presentation.authentication.resetPasswordFragment.ResetPasswordViewModel
import com.obsudim.mypsychologist.presentation.di.MultiViewModelFactory
import com.obsudim.mypsychologist.ui.core.composeComponents.PlaceholderError
import com.obsudim.mypsychologist.ui.core.composeComponents.PrimaryTextButton
import com.obsudim.mypsychologist.ui.core.composeComponents.PrimaryTextField
import com.obsudim.mypsychologist.ui.core.composeComponents.scrollToElement
import com.obsudim.mypsychologist.ui.theme.AppTheme
import com.obsudim.mypsychologist.ui.theme.AppTheme.typography
import javax.inject.Inject


class ResetPasswordFragment : Fragment() {

    @Inject
    lateinit var vmFactory: MultiViewModelFactory
    private val viewModel: ResetPasswordViewModel by lazy {
        ViewModelProvider(this, vmFactory)[ResetPasswordViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().getAppComponent().authenticationComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            AppTheme {
                Scaffold { innerPadding ->
                    ResetPasswordScreen(
                        viewModel,
                        innerPadding
                    )
                }
            }
        }
    }

    @Composable
    private fun ResetPasswordScreen(
        viewModel: ResetPasswordViewModel,
        innerPadding: PaddingValues
    ) {
        val viewState = viewModel.screenState.collectAsState().value
        when (viewState) {
            is ResetPasswordScreenState.Content -> {
                ResetPasswordContent(
                    viewState,
                    innerPadding,
                    onEmailChange = {
                        viewModel.changeEmail(it)
                    },
                    onResetPassword = {
                        viewModel.sendRequestToResetPassword()
                    }
                )
            }

            ResetPasswordScreenState.Error -> {
                PlaceholderError()
            }

            ResetPasswordScreenState.SuccessRequest -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_tracker_mood_practice),
                        contentDescription = stringResource(
                            id = R.string.db_error
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 100.dp)
                            .padding(horizontal = 16.dp),
                        text = stringResource(id = R.string.place_reset),
                        style = typography.bodyL
                    )
                }
            }
        }
    }

    @Composable
    private fun ResetPasswordContent(
        viewState: ResetPasswordScreenState.Content,
        innerPadding: PaddingValues,
        onEmailChange: (String) -> Unit,
        onResetPassword: () -> Unit
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.ic_auth_back),
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )

            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .align(Alignment.BottomCenter)
                    .background(
                        color = AppTheme.colors.screenBackground,
                        shape = RoundedCornerShape(topEnd = 28.dp, topStart = 28.dp)
                    )
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 80.dp, top = 16.dp)
                    .imePadding()
                    .verticalScroll(scrollState)
            ) {
                Spacer(modifier = Modifier.padding(top = 16.dp))
                Text(
                    text = stringResource(R.string.reset_password),
                    style = typography.titleXS,
                    color = AppTheme.colors.primaryText,
                )

                Spacer(modifier = Modifier.padding(top = 60.dp))
                PrimaryTextField(
                    field = viewState.email,
                    placeHolderText = stringResource(id = R.string.mail),
                    onFieldChange = { newEmail ->
                        onEmailChange(newEmail)
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                    ),
                    modifier = Modifier
                        .scrollToElement(scrollState)
                )

                PrimaryTextButton(
                    textString = stringResource(id = R.string.restore),
                    isLoading = viewState.isLoading,
                    onClick = onResetPassword,
                    modifier = Modifier.padding(top = 30.dp)
                )

            }
        }
    }

    @Composable
    @Preview(showBackground = true)
    private fun ResetPasswordContent_Preview() {
        AppTheme {
            ResetPasswordContent(
                viewState = ResetPasswordScreenState.Content(),
                innerPadding = PaddingValues(),
                onEmailChange = {}
            ) {}
        }
    }
}