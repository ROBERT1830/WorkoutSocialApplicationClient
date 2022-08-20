package com.robertconstantindinescu.woutapp.core.presentation.navigation.screen

import com.robertconstantindinescu.woutapp.core.util.CoreConstants.POST_DETAILS_SCREEN

sealed class OtherScreens(val route: String) {
    object PostDetailsScreen: OtherScreens(POST_DETAILS_SCREEN)
}
