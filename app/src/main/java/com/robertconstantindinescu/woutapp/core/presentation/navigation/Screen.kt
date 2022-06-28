package com.robertconstantindinescu.woutapp.core.presentation.navigation

import com.robertconstantindinescu.woutapp.core.util.CoreConstants.LOGIN_SCREEN
import com.robertconstantindinescu.woutapp.core.util.CoreConstants.SIGNUP_SCREEN
import com.robertconstantindinescu.woutapp.core.util.CoreConstants.SPLASH_SCREEN

sealed class Screen(val route: String) {
    object SplashScreen: Screen(SPLASH_SCREEN)
    object LoginScreen: Screen(LOGIN_SCREEN)
    object SignupScreen: Screen(SIGNUP_SCREEN)
}
