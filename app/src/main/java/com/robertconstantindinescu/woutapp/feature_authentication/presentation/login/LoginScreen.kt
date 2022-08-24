package com.robertconstantindinescu.woutapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.robertconstantindinescu.woutapp.core.presentation.components.StandardTexField
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.LocalSpacing
import com.robertconstantindinescu.woutapp.core.util.UiEvent
import com.robertconstantindinescu.woutapp.core.util.UiText
import com.robertconstantindinescu.woutapp.feature_authentication.presentation.login.LoginEvent
import com.robertconstantindinescu.woutapp.feature_authentication.presentation.login.LoginViewModel
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterial3Api
@Composable
fun LoginScreen(
    onSignUpClick: () -> Unit = {},
    onShowSnackBar: (text: UiText) -> Unit = {},
    onNavigateTo: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel()
) {
    val dimens = LocalSpacing.current
    val emailState = viewModel.emailState
    val passwordState = viewModel.passwordState
    val loginState = viewModel.loginState

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { uiEvent ->
            when(uiEvent) {
                is UiEvent.ShowSnackBar -> {
                    onShowSnackBar(uiEvent.text)
                }
                is UiEvent.NavigateTo -> {
                     onNavigateTo()
                }
                else -> Unit
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = dimens.spaceMedium,
                    vertical = dimens.spaceLarge
                ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimens.spaceExtraLarge)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.login_title),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    Spacer(modifier = Modifier.height(dimens.spaceSmall))
                    Text(
                        text = stringResource(id = R.string.login_subtitle),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = dimens.spaceExtraLarge
                    )
            ) {
                StandardTexField(
                    modifier = Modifier.padding(
                        vertical = dimens.spaceSmall
                    ),
                    text = emailState.text,
                    hint = stringResource(id = R.string.user_email),
                    error = "",
                    keyboardType = KeyboardType.Email,
                    leadingIcon = Icons.Default.Email,
                    onValueChange = {
                        viewModel.onEvent(LoginEvent.OnEnterEmail(it))
                    }
                )
                StandardTexField(
                    modifier = Modifier.padding(
                        vertical = dimens.spaceSmall
                    ),
                    text = passwordState.defaultFieldState.text,
                    hint = stringResource(id = R.string.user_password),
                    error = "",
                    keyboardType = KeyboardType.Password,
                    isPasswordHide = passwordState.isPasswordHide,
                    leadingIcon = Icons.Default.Password,
                    onPasswordToggleClick = {
                        viewModel.onEvent(LoginEvent.OnPasswordToggleClick)
                    },
                    onValueChange = {
                        viewModel.onEvent(LoginEvent.OnEnterPassword(it))
                    }

                )
            }

            Button(
                onClick = { viewModel.onEvent(LoginEvent.OnLoginClick) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimens.spaceExtraLarge),
            ) {
                Text(
                    text = stringResource(id = R.string.login_button_text),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimens.spaceSmall)
            )

            Divider(
                modifier = Modifier
                    .width(80.dp)
                    .align(CenterHorizontally)
                    .padding(vertical = dimens.spaceMedium),
                color = MaterialTheme.colorScheme.primary,
                thickness = 1.dp,
            )

            Text(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .clickable(
                        onClick = {
                            onSignUpClick()
                        },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ),
                text = buildAnnotatedString {
                    append(stringResource(id = R.string.dont_have_an_account_yet))
                    append(" ")
                    val signUpText = stringResource(id = R.string.sign_up_button_text)
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        append(signUpText)
                    }
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )

        }
        if (loginState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Center), color = MaterialTheme.colorScheme.primary)
        }

    }
}























