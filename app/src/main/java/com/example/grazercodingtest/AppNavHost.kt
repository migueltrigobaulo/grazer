package com.example.grazercodingtest

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.grazercodingtest.presentation.ui.screen.HomeScreen
import com.example.grazercodingtest.presentation.ui.screen.LoginScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Login.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination, enterTransition = {
            fadeIn()
        }, exitTransition = {
            fadeOut()
        }, popEnterTransition = {
            fadeIn()
        }, popExitTransition = {
            fadeOut()
        }
    ) {
        composable(NavigationItem.Home.route) {
            HomeScreen()
        }
        composable(NavigationItem.Login.route) {
            LoginScreen(navigateToHome = {
                navController.navigate(NavigationItem.Home.route) {
                    popUpTo(NavigationItem.Login.route) {
                        inclusive = true
                    }
                }
            })
        }
    }
}