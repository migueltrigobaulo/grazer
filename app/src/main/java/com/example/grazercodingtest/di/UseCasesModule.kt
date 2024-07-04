package com.example.grazercodingtest.di

import com.example.grazercodingtest.domain.useCase.LoginUseCase
import com.example.grazercodingtest.domain.useCase.UserListUseCase
import org.koin.dsl.module

val useCasesModule = module {
    single { LoginUseCase() }
    single { UserListUseCase() }
}