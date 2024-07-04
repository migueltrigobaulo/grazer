package com.example.grazercodingtest.di

import com.example.grazercodingtest.data.local.SecureTokenManager
import com.example.grazercodingtest.data.remote.repository.LoginRepositoryImpl
import com.example.grazercodingtest.data.remote.repository.UserRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoriesModule = module {
    single { LoginRepositoryImpl(get()) }
    single { UserRepositoryImpl(get()) }
    single { SecureTokenManager(androidContext()) }
}