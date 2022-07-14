package com.robertconstantindinescu.woutapp.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.robertconstantindinescu.woutapp.LoginScreen
import com.robertconstantindinescu.woutapp.core.presentation.navigation.Screen
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.WoutAppTheme
import com.robertconstantindinescu.woutapp.feature_authentication.presentation.register.SignUpScreen
import com.robertconstantindinescu.woutapp.feature_splash.presentation.SplashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WoutAppTheme {

                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                val context = LocalContext.current

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        scaffoldState = scaffoldState
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.SplashScreen.route
                        ){
                            composable(Screen.SplashScreen.route) {
                                SplashScreen {
                                    navController.popBackStack()
                                    navController.navigate(Screen.LoginScreen.route)
                                }
                            }
                            composable(
                                route = Screen.LoginScreen.route + "?email={email}",
                                arguments = listOf(
                                    navArgument(
                                        name = "email"
                                    ){
                                        type = NavType.StringType
                                        nullable = true
                                        defaultValue = null
                                    }
                                )
                            ) {
                                LoginScreen(
                                    onLoginClick = {

                                    },
                                    onSignUpClick = {
                                        navController.navigate(Screen.SignupScreen.route)
                                    }
                                )
                            }
                            composable(Screen.SignupScreen.route){
                                SignUpScreen(
                                    scaffoldState = scaffoldState,
                                    onLoginNavigation = { email ->
                                        email?.let {
                                            navController.navigate(Screen.LoginScreen.route + "?email=$email")
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

                        }
                    }
                }
            }
        }
    }
}
