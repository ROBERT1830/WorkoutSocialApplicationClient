package com.robertconstantindinescu.woutapp.feature_authentication.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertconstantindinescu.woutapp.core.presentation.navigation.screen.AuthScreen
import com.robertconstantindinescu.woutapp.core.presentation.navigation.screen.BottomMenuScreen
import com.robertconstantindinescu.woutapp.core.util.Resource
import com.robertconstantindinescu.woutapp.core.util.UiEvent
import com.robertconstantindinescu.woutapp.feature_authentication.domain.use_case.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val useCases: AuthUseCases
): ViewModel() {

    private val _uiEvent = Channel<UiEvent<InitNavigationTo>>()
    val uiEvent = _uiEvent.receiveAsFlow()
//    enum class InitNavigation {
//        MAIN_FEED_SCREEN,
//        LOGIN_SCREEN
//    }

    sealed class InitNavigationTo(val route: String) {
        object LoginScreen: InitNavigationTo(AuthScreen.LoginAuthScreen.route)
        object MainFeedScreen: InitNavigationTo(BottomMenuScreen.MainFeedScreen.route)
    }

    init {
        viewModelScope.launch {
            when(useCases.initAuthUseCase()) {
                is Resource.Success -> {
                    _uiEvent.send(UiEvent.NavigateTo(InitNavigationTo.MainFeedScreen))
                }
                is Resource.Error -> {
                    _uiEvent.send(UiEvent.NavigateTo(InitNavigationTo.LoginScreen))
                }
            }
        }
    }

}