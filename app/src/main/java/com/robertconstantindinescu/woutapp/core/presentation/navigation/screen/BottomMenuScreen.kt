package com.robertconstantindinescu.woutapp.core.presentation.navigation.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.robertconstantindinescu.woutapp.core.util.CoreConstants.ACTIVITY
import com.robertconstantindinescu.woutapp.core.util.CoreConstants.ACTIVITY_SCREEN
import com.robertconstantindinescu.woutapp.core.util.CoreConstants.CREATE_POST
import com.robertconstantindinescu.woutapp.core.util.CoreConstants.CREATE_POST_SCREEN
import com.robertconstantindinescu.woutapp.core.util.CoreConstants.FAVORITES
import com.robertconstantindinescu.woutapp.core.util.CoreConstants.FAVORITES_SCREEN
import com.robertconstantindinescu.woutapp.core.util.CoreConstants.MAIN_FEED
import com.robertconstantindinescu.woutapp.core.util.CoreConstants.MAIN_FEED_SCREEN
import com.robertconstantindinescu.woutapp.core.util.CoreConstants.PERSONAL_POST
import com.robertconstantindinescu.woutapp.core.util.CoreConstants.PERSONAL_POST_SCREEN

sealed class BottomMenuScreen(val route: String, val icon: ImageVector? = null, val title: String) {
    object MainFeedScreen : BottomMenuScreen(
        route = MAIN_FEED_SCREEN, icon = Icons.Default.List, title = MAIN_FEED
    )
    object PersonalPostScreen : BottomMenuScreen(
        route = PERSONAL_POST_SCREEN, icon = Icons.Default.Person, title = PERSONAL_POST
    )
    object FavoritesScreen : BottomMenuScreen(
        route = FAVORITES_SCREEN, icon = Icons.Default.Favorite, title = FAVORITES
    )
    object CreatePostScreen : BottomMenuScreen(
        route = CREATE_POST_SCREEN, icon = Icons.Default.Create, title = CREATE_POST
    )
    object Activity : BottomMenuScreen(
        route = ACTIVITY_SCREEN, icon = Icons.Default.Create, title = ACTIVITY
    )

    object Default : BottomMenuScreen(
        route = "", icon = null, title = ""
    )
}
