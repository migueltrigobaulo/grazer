package com.example.grazercodingtest.domain.useCase

import com.example.grazercodingtest.data.remote.repository.UserRepositoryImpl
import kotlinx.coroutines.flow.flow
import org.koin.java.KoinJavaComponent.inject

class UserListUseCase {
    private val userRepository = inject<UserRepositoryImpl>(UserRepositoryImpl::class.java)

    operator fun invoke(token: String) = flow {
        emit(userRepository.value.getUserList(token))
    }
}