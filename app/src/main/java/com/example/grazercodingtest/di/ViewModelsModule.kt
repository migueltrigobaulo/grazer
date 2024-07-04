package com.example.grazercodingtest.di


import com.example.grazercodingtest.presentation.ui.viewmodel.HomeScreenViewModel
import com.example.grazercodingtest.presentation.ui.viewmodel.LoginScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { LoginScreenViewModel(get(), get()) }
    viewModel { HomeScreenViewModel(get(), get()) }
}