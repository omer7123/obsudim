package com.example.mypsychologist.ui.authentication.authFragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mypsychologist.NavbarHider
import com.example.mypsychologist.R
import com.example.mypsychologist.data.model.AuthModel
import com.example.mypsychologist.databinding.FragmentAuthBinding
import com.example.mypsychologist.extensions.bounce
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.authentication.authFragment.AuthState
import com.example.mypsychologist.presentation.authentication.authFragment.AuthViewModel
import com.example.mypsychologist.presentation.di.MultiViewModelFactory
import javax.inject.Inject

class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = requireNotNull(_binding)

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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(layoutInflater)
        viewModel.stateScreen.observe(viewLifecycleOwner) { state ->
            render(state)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    private fun initListener() {
        binding.registrationButton.setOnClickListener {
            val email = binding.mail.text.toString()
            val password = binding.password.text.toString()

            viewModel.auth(AuthModel(email, password))
        }

        binding.toolbar.toolbar.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun render(state: AuthState) {
        when (state) {
            AuthState.Initial -> {}
            is AuthState.Error -> renderError(state.msg)
            AuthState.Loading -> renderLoading()
            AuthState.Success -> renderSuccess()
            is AuthState.Content -> renderContent(state)
        }
    }

    private fun renderContent(state: AuthState.Content) {
        if (state.email)
            binding.inputMailLayout.bounce()

        if (state.password)
            binding.inputPasswordLayout.bounce()

    }

    private fun renderError(msg: String) {
        binding.progressCircular.isVisible = false
        binding.content.isVisible = true
        Log.e("fdf", msg)
        requireContext().showToast(msg)
    }

    private fun renderSuccess() {
        binding.progressCircular.isVisible = false
        findNavController().navigate(R.id.action_authFragment_to_main_fragment)
    }

    private fun renderLoading() {
        binding.progressCircular.isVisible = true
        binding.content.isVisible = false
    }

    override fun onDetach() {
        navbarHider?.setNavbarVisibility(true)
        navbarHider = null
        super.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}