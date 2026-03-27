package com.infocal.nav.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.infocal.nav.R
import com.infocal.nav.databinding.FragmentLoginBinding
import com.infocal.nav.viewModel.LoginViewModel
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding?=null
    private val binding get()=_binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       _binding= FragmentLoginBinding.inflate(inflater,container,false)
        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        observeUiState()
    }

    private fun setupListeners() {
        binding.usernameEditText.doAfterTextChanged { text ->
            viewModel.onUsernameChanged(text?.toString().orEmpty())
        }

        binding.passwordEditText.doAfterTextChanged { text ->
            viewModel.onPasswordChanged(text?.toString().orEmpty())
        }

        binding.loginButton.setOnClickListener {
            viewModel.login(
                onSuccess = {
                    //todo Navega o muestra éxito

                },
                onError = { message ->
                    //todo Mostrar error al usuario y no al input text
                    binding.passwordTextInputLayout.error = message
                }
            )
        }

        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.usernameTextInputLayout.error = state.usernameError
                    binding.passwordTextInputLayout.error = state.passwordError
                    binding.loginButton.isEnabled = state.isFormValid && !state.isLoading

                    binding.loginButton.text =
                        if (state.isLoading) "Validando..." else "Iniciar sesión"
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}