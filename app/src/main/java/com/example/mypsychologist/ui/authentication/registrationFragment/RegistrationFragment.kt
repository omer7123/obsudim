package com.example.mypsychologist.ui.authentication.registrationFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.R
import com.example.mypsychologist.data.model.OldRegister
import com.example.mypsychologist.databinding.FragmentRegistrationBinding
import com.example.mypsychologist.extensions.bounce
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.authentication.registrationFragment.RegisterState
import com.example.mypsychologist.presentation.authentication.registrationFragment.RegisterViewModel
import com.example.mypsychologist.presentation.di.MultiViewModelFactory
import javax.inject.Inject

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = requireNotNull(_binding)


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
    ): View {
        _binding = FragmentRegistrationBinding.inflate(layoutInflater, container, false)

        viewModel.stateScreen.observe(viewLifecycleOwner) { state ->
            render(state)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.authByToken()
        initListener()
    }


    private fun initListener() {
        binding.registrationButton.setOnClickListener {
            val email = binding.mailEt.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.repeatPassword.text.toString()

            viewModel.register(OldRegister(email, "", password, confirmPassword))
        }

        binding.goToLoginButton.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_authFragment)
        }
    }

    private fun render(state: RegisterState) {
        when (state) {
            is RegisterState.Error -> renderError(state.msg)
            is RegisterState.Success -> renderSuccess()
            is RegisterState.SuccessAuth -> renderSuccessAuth()
            RegisterState.Initial -> {}
            RegisterState.Loading -> renderLoading()
            is RegisterState.Content -> renderContent(state)
        }
    }

    private fun renderSuccessAuth() {
        binding.progressCircular.isVisible = false
        findNavController().navigate(R.id.action_registrationFragment_to_main_fragment)
    }

    private fun renderContent(state: RegisterState.Content) {
        if (state.email)
            binding.inputMailLayout.bounce()

        if (state.password)
            binding.inputPasswordLayout.bounce()

        if (state.confirmPassword)
            binding.repeatPasswordLayout.bounce()
    }

    private fun renderError(msg: String) {
        binding.content.isVisible = true
        binding.progressCircular.isVisible = false
        requireContext().showToast(msg)
    }

    private fun renderLoading() {
        binding.content.isVisible = false
        binding.progressCircular.isVisible = true
    }

    private fun renderSuccess() {
        binding.progressCircular.isVisible = false
        findNavController().navigate(R.id.action_registrationFragment_to_startBoardFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}