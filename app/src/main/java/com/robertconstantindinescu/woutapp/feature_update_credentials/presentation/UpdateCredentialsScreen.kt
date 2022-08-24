package com.robertconstantindinescu.woutapp.feature_update_credentials.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.presentation.components.StandardTexField
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.LocalSpacing
import com.robertconstantindinescu.woutapp.core.util.UiEvent
import com.robertconstantindinescu.woutapp.feature_authentication.domain.util.AuthError
import kotlinx.coroutines.flow.collectLatest



@ExperimentalMaterial3Api
@Composable
fun UpdateCredentialsScreen(
    scaffoldState: ScaffoldState,
    viewModel: UpdateCredentialsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val dimens = LocalSpacing.current

    val usernameState = viewModel.usernameState
    val emailState = viewModel.emailState
    val loadingState = viewModel.updateCredentialState

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { uiEvent ->
            when (uiEvent) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = uiEvent.text.asString(context)
                    )
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
                        text = stringResource(id = R.string.update_credential_screen_title),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    Spacer(modifier = Modifier.height(dimens.spaceSmall))
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
                    hint = emailState.text,
                    error = when (usernameState.error) {
                        is AuthError.FieldEmpty -> {
                            stringResource(id = R.string.filed_no_empty)
                        }
                        is AuthError.FieldShort -> {
                            stringResource(id = R.string.field_too_short)
                        }
                        else -> ""
                    },
                    keyboardType = KeyboardType.Email,
                    leadingIcon = Icons.Default.Email,
                    onValueChange = {
                        viewModel.onEvent(UpdateCredentialsEvent.OnEnterEmail(it))
                    }
                )

                StandardTexField(
                    modifier = Modifier.padding(
                        vertical = dimens.spaceSmall
                    ),
                    text = usernameState.text,
                    hint = usernameState.text,
                    error = when (usernameState.error) {
                        is AuthError.FieldEmpty -> {
                            stringResource(id = R.string.filed_no_empty)
                        }
                        is AuthError.FieldShort -> {
                            stringResource(id = R.string.field_too_short)
                        }
                        else -> ""
                    },
                    keyboardType = KeyboardType.Text,
                    leadingIcon = Icons.Default.Person,
                    onValueChange = {
                        viewModel.onEvent(UpdateCredentialsEvent.OnEnterUserName(it))
                    }
                )
            }

            Button(
                onClick = { viewModel.onEvent(UpdateCredentialsEvent.OnUpdateCredentials) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimens.spaceExtraLarge),
            ) {
                Text(
                    text = stringResource(id = R.string.update_credential_screen_update_button),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }
        if (loadingState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }

    }
}
