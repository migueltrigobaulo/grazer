package com.example.grazercodingtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.grazercodingtest.di.networkModule
import com.example.grazercodingtest.di.repositoriesModule
import com.example.grazercodingtest.di.useCasesModule
import com.example.grazercodingtest.di.viewModelModules
import com.example.grazercodingtest.presentation.ui.theme.GrazerCodingTestTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidContext(this@MainActivity)
            modules(
                viewModelModules,
                useCasesModule,
                networkModule,
                repositoriesModule
            )
        }
        setContent {
            GrazerCodingTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost(navController = rememberNavController(), modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}