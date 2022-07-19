package com.robertconstantindinescu.woutapp.core.presentation.navigation.screen

import com.robertconstantindinescu.woutapp.core.util.CoreConstants.LOGIN_SCREEN
import com.robertconstantindinescu.woutapp.core.util.CoreConstants.MAIN_FEED
import com.robertconstantindinescu.woutapp.core.util.CoreConstants.SIGNUP_SCREEN
import com.robertconstantindinescu.woutapp.core.util.CoreConstants.SPLASH_SCREEN

sealed class AuthScreen(val route: String) {
    object SplashAuthScreen: AuthScreen(SPLASH_SCREEN)
    object LoginAuthScreen: AuthScreen(LOGIN_SCREEN)
    object SignupAuthScreen: AuthScreen(SIGNUP_SCREEN)
}
