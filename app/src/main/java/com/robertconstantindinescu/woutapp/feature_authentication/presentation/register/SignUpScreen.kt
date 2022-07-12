package com.robertconstantindinescu.woutapp.feature_authentication.presentation.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.presentation.components.StandardTexField
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.LocalSpacing
import com.robertconstantindinescu.woutapp.core.util.UiEvent
import com.robertconstantindinescu.woutapp.core.util.UiText
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpScreen(
    scaffoldState: ScaffoldState,
    onLoginNavigation: (email: String?) -> Unit = {},
    onShowSnackBar: (text:UiText) -> Unit = {},
    viewModel: SignUpViewModel = hiltViewModel()
) {

    val dimens = LocalSpacing.current
    val usernameState = viewModel.usernameState
    val emailState = viewModel.emailState
    val passwordState = viewModel.passwordState


    //Receive single Ui events
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { uiEvent ->
            when(uiEvent) {
                is UiEvent.ShowSnackBar -> {
                    onShowSnackBar(uiEvent.text)

                }
                is UiEvent.NavigateUp -> {
//                    val params = uiEvent.params as SignUpViewModel.Params
                    onLoginNavigation(uiEvent.params?.email)
                }

            }

        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimens.spaceMedium)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimens.spaceExtraLarge)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.signup_screen_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(dimens.spaceSmall))
                Text(
                    text = stringResource(id = R.string.signup_screen_subtitle),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }

        }


        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(dimens.spaceLarge))
            StandardTexField(
                modifier = Modifier.padding(
                    vertical = dimens.spaceSmall
                ),
                text = usernameState.text,
                hint = stringResource(id = R.string.signup_user_name),
                error = "",
                keyboardType = KeyboardType.Text,
                leadingIcon = Icons.Default.Person,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.OnEnterUserName(it))
                }
            )
            StandardTexField(
                modifier = Modifier.padding(
                    vertical = dimens.spaceSmall
                ),
                text = emailState.text,
                hint = stringResource(id = R.string.signup_user_email),
                error = "",
                keyboardType = KeyboardType.Email,
                leadingIcon = Icons.Default.Email,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.OnEnterEmail(it))
                }
            )
            StandardTexField(
                modifier = Modifier.padding(
                    vertical = dimens.spaceSmall
                ),
                text = passwordState.authStandardFieldState.text,
                hint = stringResource(id = R.string.signup_user_password),
                error = "",
                keyboardType = KeyboardType.Password,
                leadingIcon = Icons.Default.Password,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.OnEnterPassword(it))
                }
            )

            Spacer(modifier = Modifier.height(dimens.spaceMedium))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(
                    modifier = Modifier
                        .wrapContentWidth(),
                    onClick = {
                        viewModel.onEvent(SignUpEvent.OnRegisterClick)
                    },
                ) {
                    Text(
                        text = stringResource(id = R.string.singup_screen_register_button),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(dimens.spaceMedium))
                    if (viewModel.registerState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(30.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }else {
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = null,
                        )
                    }

                }
            }
        }
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = dimens.spaceLarge)
                .clickable {
                    onLoginNavigation(null)
                },
            text = buildAnnotatedString {
                append(stringResource(id = R.string.signup_screen_already_have_account))
                append(" ")
                val signUpText = stringResource(id = R.string.signup_screen_sign_in)
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
}