package com.example.grazercodingtest.presentation.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grazercodingtest.data.local.SecureTokenManager
import com.example.grazercodingtest.domain.useCase.LoginUseCase
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val loginUseCase: LoginUseCase,
    private val secureTokenManager: SecureTokenManager
) : ViewModel() {
    sealed class LoginState {
        data object Initial : LoginState()
        data object Loading : LoginState()
        data class Success(val token: String) : LoginState()
        data class Error(val message: String) : LoginState()
    }

    private val _loginState = mutableStateOf<LoginState>(LoginState.Initial)
    val loginState: LoginState
        get() = _loginState.value

    private val expandedState = mutableStateOf(true)
    val isExpanded: Boolean
        get() = expandedState.value

    private val _email = mutableStateOf("asdf@asd.es")
    val email: String
        get() = _email.value

    private val _password = mutableStateOf("aaaa")
    val password: String
        get() = _password.value

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun clearLoginState() {
        _loginState.value = LoginState.Initial
    }

    private fun saveToken(token: String) {
        secureTokenManager.saveToken(token)
    }

    fun getToken(): String? {
        return secureTokenManager.getToken()
    }

    fun clearToken() {
        secureTokenManager.clearToken()
    }

    fun onExpand() {
        expandedState.value = !expandedState.value
    }

    fun onLogin() {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            loginUseCase(_email.value, _password.value).collect { result ->
                _loginState.value = when {
                    result.isSuccess -> {
                        val token = result.getOrNull()
                        if (token != null) {
                            saveToken(token)
                            LoginState.Success(token)
                        } else {
                            LoginState.Error("Login successful but token is null")
                        }
                    }
                    result.isFailure -> {
                        val error = result.exceptionOrNull()?.message ?: "Unknown error occurred"
                        LoginState.Error(error)
                    }
                    else -> LoginState.Error("Unexpected login result")
                }
            }
        }
    }

}