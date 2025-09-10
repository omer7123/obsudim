package com.example.mypsychologist.ui.authentication.registrationFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.data.repository.PhoneVisualTransformation
import com.example.mypsychologist.extensions.convertMillisToDate
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.authentication.registrationFragment.Gender
import com.example.mypsychologist.presentation.authentication.registrationFragment.RegisterContent
import com.example.mypsychologist.presentation.authentication.registrationFragment.RegisterStatus
import com.example.mypsychologist.presentation.authentication.registrationFragment.RegisterViewModel
import com.example.mypsychologist.presentation.authentication.registrationFragment.StepScreen
import com.example.mypsychologist.presentation.di.MultiViewModelFactory
import com.example.mypsychologist.ui.core.composeComponents.DatePickerModal
import com.example.mypsychologist.ui.core.composeComponents.PrimaryPickerTextField
import com.example.mypsychologist.ui.core.composeComponents.PrimaryTextButton
import com.example.mypsychologist.ui.core.composeComponents.PrimaryTextField
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

        Content(
            viewState = viewState,
            onBackNavIcon = {
                when(viewState.value.step){
                    StepScreen.PersonalScreen -> navController.popBackStack()
                    StepScreen.RegistrationScreen -> viewModel.prevStep()
                }
            },
            onNameChange = {viewModel.changeName(it)},
            onCityChange = {viewModel.changeCity(it)},
            onGenderChange = {viewModel.changeGender(it)},
            onDateBirthDayChange = {viewModel.changeBirthday(it)},
            onNextClick = {viewModel.onNextClick()},
            onRegisterClick = {viewModel.register()},
            onEmailChange = {viewModel.changeEmail(it)},
            onPhoneChange = {viewModel.changePhone(it)},
            onPasswordChange = {viewModel.changePassword(it)},
            onConfirmPasswordChange = {viewModel.changeConfirmPassword(it)}
        )
    }

    @Composable
    private fun Content(
        viewState: State<RegisterContent>,
        onBackNavIcon: () -> Unit,
        onNameChange: (String) -> Unit,
        onCityChange: (String) -> Unit,
        onGenderChange: (Gender) -> Unit,
        onDateBirthDayChange: (String) -> Unit,
        onNextClick: () -> Unit,
        onEmailChange: (String) -> Unit,
        onPhoneChange: (String) -> Unit,
        onPasswordChange: (String) -> Unit,
        onConfirmPasswordChange: (String) -> Unit,
        onRegisterClick: () -> Unit
    ) {
        val showDatePicker = remember {
            mutableStateOf(false)
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.ic_auth_back),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            androidx.compose.material3.IconButton(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = 12.dp, start = 8.dp),
                onClick = {
                    onBackNavIcon()
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back_white),
                    contentDescription = stringResource(id = R.string.feedback),
                    tint = AppTheme.colors.screenBackground,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
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
                    viewState = viewState,
                    onNameChange = {onNameChange(it)},
                    onCityChange = {onCityChange(it)},
                    onGenderChange = {onGenderChange(it)},
                    showDatePicker = showDatePicker,
                    onNextClick = {onNextClick()},
                    onEmailChange = {onEmailChange(it)},
                    onPhoneChange = {onPhoneChange(it)},
                    onPasswordChange = {onPasswordChange(it)},
                    onConfirmPasswordChange = {onConfirmPasswordChange(it)},
                    onRegisterClick = {onRegisterClick()}
                )
            }
            if(showDatePicker.value) {
                DatePickerModal(
                    onDateSelected = {
                        onDateBirthDayChange(it?.convertMillisToDate() ?: "")
                    },
                    onDismiss = {showDatePicker.value = false}
                )
            }

        }
    }

    @Composable
    private fun ChangeScreen(
        viewState: State<RegisterContent>,
        onNameChange: (String) -> Unit,
        onCityChange: (String) -> Unit,
        onGenderChange: (Gender) -> Unit,
        showDatePicker: MutableState<Boolean>,
        onNextClick: () -> Unit,
        onEmailChange: (String) -> Unit,
        onPhoneChange: (String) -> Unit,
        onPasswordChange: (String) -> Unit,
        onConfirmPasswordChange: (String) -> Unit,
        onRegisterClick: () -> Unit
    ) {
        val content = viewState.value
        when(content.step){
            StepScreen.PersonalScreen -> {
                PersonalDataScreen(
                    value = content,
                    onNameChange = {onNameChange(it)},
                    onCityChange = {onCityChange(it)},
                    onGenderChange = {onGenderChange(it)},
                    showDatePicker = showDatePicker,
                    onNextClick = {onNextClick()}
                )
            }
            StepScreen.RegistrationScreen -> {
                RegistrationDataScreen(
                    value = content,
                    onEmailChange = onEmailChange,
                    onPhoneChange = onPhoneChange,
                    onPasswordChange = onPasswordChange,
                    onConfirmPasswordChange = onConfirmPasswordChange,
                    onRegisterClick = {onRegisterClick() }
                )
            }
        }
    }

    @Composable
    private fun PersonalDataScreen(
        value: RegisterContent,
        onNameChange: (String) -> Unit,
        onCityChange: (String) -> Unit,
        onGenderChange: (Gender) -> Unit,
        showDatePicker: MutableState<Boolean>,
        onNextClick: () -> Unit
    ) {


        Text(
            text = stringResource(id = R.string.enter_your_data),
            style = AppTheme.typography.titleXS,
            color = AppTheme.colors.primaryText,
        )

        PrimaryTextField(
            field = value.name,
            placeHolderText = stringResource(id = R.string.name),
            onFieldChange = {onNameChange(it)},
            modifier = Modifier.padding(top = 30.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Words
            ),
            errorStr = value.nameError?.let { stringResource(id = it) } ?: ""
        )

        Spacer(modifier = Modifier.padding(top = 16.dp))

        PrimaryTextField(
            field = value.city,
            placeHolderText = stringResource(id = R.string.city),
            onFieldChange = {onCityChange(it)},
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Words
            ),
            errorStr = value.cityError?.let { stringResource(id = it) } ?: ""
        )


        Spacer(modifier = Modifier.padding(top = 16.dp))
        PrimaryPickerTextField(
            field = value.birthday,
            placeHolderText = stringResource(id = R.string.birthday),
            onFieldChange = {},
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker.value = !showDatePicker.value }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            errorStr = value.birthdayError?.let { stringResource(id = it) },

        )

        Spacer(modifier = Modifier.padding(top = 16.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = when (value.gender) {
                            Gender.MALE -> AppTheme.colors.secondaryBackground
                            else -> AppTheme.colors.tertiaryBackground
                        },
                        shape = RoundedCornerShape(12.dp)
                    )
                    .weight(0.5f)
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = {
                            onGenderChange(Gender.MALE)
                        }
                    ),

            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .align(Alignment.Center),
                    text = stringResource(id = R.string.man),
                    style = AppTheme.typography.bodyM,
                    color = AppTheme.colors.primaryText
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Box(
                modifier = Modifier
                    .background(
                        color = when (value.gender) {
                            Gender.FEMALE -> AppTheme.colors.secondaryBackground
                            else -> AppTheme.colors.tertiaryBackground
                        },
                        shape = RoundedCornerShape(12.dp)
                    )
                    .weight(0.5f)
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = {
                            onGenderChange(Gender.FEMALE)
                        }
                    )
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .align(Alignment.Center),
                    text = stringResource(id = R.string.woman),
                    style = AppTheme.typography.bodyM,
                    color = AppTheme.colors.primaryText
                )
            }
        }

        PrimaryTextButton(
            textString = stringResource(id = R.string.next),
            onClick = {onNextClick()},
            modifier = Modifier.padding(top = 30.dp)
        )
    }

    @Composable
    private fun RegistrationDataScreen(
        value: RegisterContent,
        onEmailChange: (String) -> Unit,
        onPhoneChange: (String) -> Unit,
        onPasswordChange: (String) -> Unit,
        onConfirmPasswordChange: (String) -> Unit,
        onRegisterClick: () -> Unit
    ) {
        var passwordVisible by remember { mutableStateOf(false) }

        Text(
            text = stringResource(id = R.string.just_a_little_left),
            style = AppTheme.typography.titleXS,
            color = AppTheme.colors.primaryText,
        )
        Text(
            text = stringResource(id = R.string.placeholder_registration),
            style = AppTheme.typography.bodyL,
            color = AppTheme.colors.primaryText,
            modifier = Modifier.padding(top = 10.dp)
        )

        Spacer(modifier = Modifier.padding(top = 16.dp))

        PrimaryTextField(
            field = value.email,
            placeHolderText = stringResource(id = R.string.mail),
            onFieldChange = { newEmail ->
                onEmailChange(newEmail)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            errorStr = value.emailError?.let { stringResource(id = it) } ?: ""
        )

        Spacer(modifier = Modifier.padding(top = 16.dp))

        PrimaryTextField(
            field = value.phoneNumber,
            placeHolderText = stringResource(id = R.string.phone),
            onFieldChange = { phoneNumber ->
                onPhoneChange(phoneNumber.take(LENGTH_PHONE_NUMBER))
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Phone
            ),
            visualTransformation = PhoneVisualTransformation(),
            errorStr = value.phoneNumberError?.let { stringResource(id = it) } ?: "",
        )

        Spacer(modifier = Modifier.padding(top = 16.dp))

        PrimaryPickerTextField(
            field = value.password,
            placeHolderText = stringResource(id = R.string.password),
            onFieldChange = { pass ->
                onPasswordChange(pass)
            },
            imeAction = ImeAction.Next,
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                IconButton(onClick = {
                    passwordVisible = !passwordVisible
                }) {
                    Icon(
                        imageVector = image,
                        contentDescription = "",
                        tint = AppTheme.colors.primaryText
                    )
                }
            },
            visualTransformation = if(passwordVisible) {
                    VisualTransformation.None
                }
                else PasswordVisualTransformation(),

            errorStr = value.passwordError?.let { stringResource(id = it) }

        )

        Spacer(modifier = Modifier.padding(top = 16.dp))

        PrimaryPickerTextField(
            field = value.confirmPassword,
            placeHolderText = stringResource(id = R.string.repeat_password),
            onFieldChange = { pass ->
                onConfirmPasswordChange(pass)
            },
            imeAction = ImeAction.Next,
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                IconButton(onClick = {
                    passwordVisible = !passwordVisible
                }) {
                    Icon(imageVector = image, contentDescription = "", tint = AppTheme.colors.primaryText)
                }
            },
            errorStr = value.confirmPasswordError?.let{ stringResource(id = it)},
            visualTransformation =
                if(passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
        )

        PrimaryTextButton(
            textString = stringResource(id = R.string.register),
            onClick = {onRegisterClick()},
            modifier = Modifier.padding(top = 30.dp)
        )
    }

    @Preview
    @Composable
    fun Content_Preview(){
        AppTheme {
            Content(
                viewState = remember {
                    mutableStateOf(RegisterContent(step = StepScreen.RegistrationScreen))
                },
                {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}
            )
        }
    }
    companion object{
        const val LENGTH_PHONE_NUMBER = 10
    }
}
