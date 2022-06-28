package com.robertconstantindinescu.woutapp.core.presentation

import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.robertconstantindinescu.woutapp.LoginScreen
import com.robertconstantindinescu.woutapp.core.presentation.navigation.Screen
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.WoutAppTheme
import com.robertconstantindinescu.woutapp.feature_splash.presentation.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WoutAppTheme {

                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()

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
                            composable(Screen.LoginScreen.route) {
                                LoginScreen()
                            }

                        }
                    }
                }
            }
        }
    }
}
