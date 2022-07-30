package com.robertconstantindinescu.woutapp.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.robertconstantindinescu.woutapp.LoginScreen
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.presentation.navigation.BottomNavMenu
import com.robertconstantindinescu.woutapp.core.presentation.navigation.screen.AuthScreen
import com.robertconstantindinescu.woutapp.core.presentation.navigation.screen.BottomMenuScreen
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.WoutAppTheme
import com.robertconstantindinescu.woutapp.feature_authentication.presentation.register.SignUpScreen
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.CreatePostScreen
import com.robertconstantindinescu.woutapp.feature_main_feed.presentation.MainFeedScreen
import com.robertconstantindinescu.woutapp.feature_splash.presentation.SplashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WoutAppTheme {

                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val scaffoldState = rememberScaffoldState()
                val context = LocalContext.current

                val showBottomMenu = navBackStackEntry?.destination?.route in listOf(
                    BottomMenuScreen.MainFeedScreen.route,
                    BottomMenuScreen.PersonalPostScreen.route,
                    BottomMenuScreen.FavoritesScreen.route,
                )

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        scaffoldState = scaffoldState,
                        bottomBar = {
                            if (showBottomMenu) {
                                BottomNavMenu(
                                    navController = navController,
                                    onBottomIconClick = { route ->
                                        navController.navigate(route) {
                                            navController.graph.startDestinationRoute?.let { route ->
//                                                popUpTo(route) {
//                                                    saveState = true
//                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    }
                                )
                            }
                        },
                        floatingActionButton = {
                            if (showBottomMenu) {
                                FloatingActionButton(
                                    backgroundColor = MaterialTheme.colorScheme.onSurface,
                                    onClick = { navController.navigate(BottomMenuScreen.CreatePostScreen.route) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = stringResource(id = R.string.create_post)
                                    )
                                }
                            }

                        },
                        isFloatingActionButtonDocked = true,
                        floatingActionButtonPosition = FabPosition.Center
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = AuthScreen.SplashAuthScreen.route
                        ) {
                            composable(AuthScreen.SplashAuthScreen.route) {
//                                SplashScreen {
//                                    navController.popBackStack()
//                                    navController.navigate(AuthScreen.LoginAuthScreen.route)
//                                }
                                SplashScreen(
                                    onNavigateTo = { route ->
                                        navController.popBackStack()
                                        navController.navigate(route)
                                    }
                                )
                            }
                            composable(
                                route = AuthScreen.LoginAuthScreen.route + "?email={email}",
                                arguments = listOf(
                                    navArgument(
                                        name = "email"
                                    ) {
                                        type = NavType.StringType
                                        nullable = true
                                        defaultValue = null
                                    }
                                )
                            ) {
                                LoginScreen(
                                    onSignUpClick = {
                                        navController.navigate(AuthScreen.SignupAuthScreen.route)
                                    },
                                    onShowSnackBar = { uiText ->
                                        lifecycleScope.launchWhenCreated {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = uiText.asString(context = context)
                                            )
                                        }
                                    },
                                    onNavigateTo = {
                                        navController.navigate(BottomMenuScreen.MainFeedScreen.route)
                                    }
                                )
                            }
                            composable(AuthScreen.SignupAuthScreen.route) {
                                SignUpScreen(
                                    scaffoldState = scaffoldState,
                                    onLoginNavigation = { email ->
                                        email?.let {
                                            navController.navigate(AuthScreen.LoginAuthScreen.route + "?email=$email")
                                        } ?: kotlin.run {
                                            navController.navigateUp()
                                        }
                                    },
                                    onShowSnackBar = { uiText ->
                                        lifecycleScope.launchWhenStarted {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = uiText.asString(context = context)
                                            )
                                        }

                                    }
                                )
                            }

                            composable(route = BottomMenuScreen.MainFeedScreen.route) {
                                MainFeedScreen()
                            }

                            composable(route = BottomMenuScreen.FavoritesScreen.route) {

                            }

                            composable(route = BottomMenuScreen.CreatePostScreen.route) {
                                CreatePostScreen(
                                    navHostController =  navController,
                                    onShowSnackBar = { uiText ->
                                        lifecycleScope.launchWhenStarted {
                                            scaffoldState.snackbarHostState.showSnackbar(
                                                message = uiText.asString(context = context)
                                            )
                                        }
                                    },
                                    onNavigateUp = {
                                        navController.navigateUp()
                                    }
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}
