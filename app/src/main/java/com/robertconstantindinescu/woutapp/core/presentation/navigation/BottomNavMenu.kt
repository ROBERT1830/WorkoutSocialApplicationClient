package com.robertconstantindinescu.woutapp.core.presentation.navigation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.robertconstantindinescu.woutapp.core.presentation.navigation.screen.BottomMenuScreen

@Composable
fun BottomNavMenu(
    navController: NavController,
    onBottomIconClick: (route: String) -> Unit = {},
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.background
) {

    //list of items to be displayed
    val menuItems = listOf(
        BottomMenuScreen.MainFeedScreen,
        BottomMenuScreen.PersonalPostScreen,
        BottomMenuScreen.Default,
        BottomMenuScreen.FavoritesScreen,
        BottomMenuScreen.Activity,
    )

    BottomNavigation(
        modifier = Modifier
            .graphicsLayer {
                clip = true
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                shadowElevation = 2.5f
            },
        elevation = 8.dp,
        backgroundColor = MaterialTheme.colorScheme.surface,
    ){
        //Current page from backStack
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        //Current route
        val currentRoute = navBackStackEntry?.destination?.route

        menuItems.forEach { bottomMenuItem ->
            BottomNavigationItem(
                selected = currentRoute == bottomMenuItem.route,
                selectedContentColor = selectedColor,
                unselectedContentColor = unselectedColor,
                onClick = { onBottomIconClick(bottomMenuItem.route) },
                label = {
                    Text(
                        text = bottomMenuItem.title,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 10.sp
                    )
                },
                alwaysShowLabel = false,
                icon = {
                    bottomMenuItem.icon?.let {
                        Icon(imageVector = bottomMenuItem.icon, contentDescription = bottomMenuItem.title)
                    }

                }
            )
        }

    }


}