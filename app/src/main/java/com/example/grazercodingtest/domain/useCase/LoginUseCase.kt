package com.example.grazercodingtest.domain.useCase

import com.example.grazercodingtest.data.remote.repository.LoginRepositoryImpl
import kotlinx.coroutines.flow.flow
import org.koin.java.KoinJavaComponent.inject

class LoginUseCase {
    private val loginRepository = inject<LoginRepositoryImpl>(LoginRepositoryImpl::class.java)

    operator fun invoke(email: String, password: String) = flow {
        emit(loginRepository.value.login(email, password))
    }
}