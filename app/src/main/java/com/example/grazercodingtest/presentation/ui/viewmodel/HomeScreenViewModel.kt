package com.example.grazercodingtest.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grazercodingtest.data.local.SecureTokenManager
import com.example.grazercodingtest.data.remote.response.UserDto
import com.example.grazercodingtest.domain.useCase.UserListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val userListUseCase: UserListUseCase,
    secureTokenManager: SecureTokenManager
) : ViewModel() {
    sealed class UserRequestState {
        data object Initial : UserRequestState()
        data object Loading : UserRequestState()
        data class Success(val userList: List<UserDto>) : UserRequestState()
        data class Error(val message: String) : UserRequestState()
    }

    private val _userListState = MutableStateFlow<UserRequestState>(UserRequestState.Initial)
    val userListState: StateFlow<UserRequestState> = _userListState

    init {
        secureTokenManager.getToken()?.let { token ->
            viewModelScope.launch {
                _userListState.value = UserRequestState.Loading
                userListUseCase.invoke(token).collect { result ->
                    if (result.isSuccess) {
                        val userList = result.getOrNull()
                        if (userList != null) {
                            _userListState.value = UserRequestState.Success(userList)
                        } else {
                            _userListState.value = UserRequestState.Error("User list is null")
                        }
                    } else if (result.isFailure) {
                        val error = result.exceptionOrNull()?.message ?: "Unknown error occurred"
                        _userListState.value = UserRequestState.Error(error)
                    }
                }
            }
        }
    }
}