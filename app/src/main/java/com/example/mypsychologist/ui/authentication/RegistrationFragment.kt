package com.example.mypsychologist.ui.authentication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mypsychologist.databinding.FragmentRegistrationBinding
import com.example.mypsychologist.domain.entity.authenticationEntity.Auth
import com.example.mypsychologist.domain.entity.authenticationEntity.Register
import com.example.mypsychologist.extensions.getAppComponent
import com.example.mypsychologist.extensions.showToast
import com.example.mypsychologist.presentation.authentication.RegisterState
import com.example.mypsychologist.presentation.authentication.RegisterViewModel
import javax.inject.Inject

class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = requireNotNull(_binding)

    @Inject
    lateinit var vmFactory: RegisterViewModel.Factory
    private val viewModel: RegisterViewModel by viewModels { vmFactory }

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
        //viewModel.register(Register(Auth("ilyafomin12@gmail.com", "fdfd123456789"), "fdfd123456789")) Это запрос для нового бэка
    }

    private fun render(state: RegisterState) {
        when(state){
            is RegisterState.Error -> {
                renderError(state.msg)
            }
            is RegisterState.Success -> {
                renderSuccess()
            }
            RegisterState.Initial -> {}
            RegisterState.Loading -> renderLoading()
        }
    }

    private fun renderError(msg: String) {
        Log.e("Error: ", msg)
        requireContext().showToast(msg)
    }

    private fun renderLoading() {
        //progressBar
    }

    private fun renderSuccess() {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}