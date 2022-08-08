package com.robertconstantindinescu.woutapp.feature_authentication.presentation.register

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.presentation.components.StandardTexField
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.LocalSpacing
import com.robertconstantindinescu.woutapp.core.util.CropActivityResultContract
import com.robertconstantindinescu.woutapp.core.util.UiEvent
import com.robertconstantindinescu.woutapp.core.util.UiText
import com.robertconstantindinescu.woutapp.feature_authentication.domain.util.AuthError
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterial3Api
@Composable
fun SignUpScreen(
    scaffoldState: ScaffoldState,
    imageLoader: ImageLoader,
    onLoginNavigation: (email: String?) -> Unit = {},
    onShowSnackBar: (text: UiText) -> Unit = {},
    viewModel: SignUpViewModel = hiltViewModel()
) {

    val dimens = LocalSpacing.current
    val profileimageState = viewModel.profileImageState
    val usernameState = viewModel.usernameState
    val emailState = viewModel.emailState
    val passwordState = viewModel.passwordState

    val cropProfilePictureLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(1f, 1f)
    ) {
        viewModel.onEvent(SignUpEvent.OnCropImage(it))
    }

    val profilePictureGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        if(it == null) {
            return@rememberLauncherForActivityResult
        }
        cropProfilePictureLauncher.launch(it)
    }


    //Receive single Ui events
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { uiEvent ->
            when (uiEvent) {
                is UiEvent.ShowSnackBar -> {
                    onShowSnackBar(uiEvent.text)

                }
                is UiEvent.NavigateUp -> {
                    val params = uiEvent.params as? SignUpViewModel.Params
                    onLoginNavigation(params?.email)
                }

            }

        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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

        Spacer(modifier = Modifier.height(dimens.spaceMedium))

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
                    .clickable { profilePictureGalleryLauncher.launch("image/*") },
                contentAlignment = Center
            ) {
                Icon(
                    imageVector = Icons.Default.AddAPhoto,
                    contentDescription = stringResource(id = R.string.create_post_screen_choose_image),
                    tint = MaterialTheme.colorScheme.onSurface
                )
                profileimageState?.let { uri ->
                    Image(
                        modifier = Modifier.matchParentSize(),
                        painter = rememberAsyncImagePainter(model = uri, imageLoader = imageLoader),
                        contentDescription = stringResource(id = R.string.sign_profile_image)
                    )
                }
            }
            Spacer(modifier = Modifier.height(dimens.spaceLarge))
            StandardTexField(
                modifier = Modifier.padding(
                    vertical = dimens.spaceSmall
                ),
                text = usernameState.text,
                hint = stringResource(id = R.string.signup_user_name),
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
                    viewModel.onEvent(SignUpEvent.OnEnterUserName(it))
                }
            )
            StandardTexField(
                modifier = Modifier.padding(
                    vertical = dimens.spaceSmall
                ),
                text = emailState.text,
                hint = stringResource(id = R.string.signup_user_email),
                error = when (emailState.error) {
                    is AuthError.FieldEmpty -> {
                        stringResource(id = R.string.filed_no_empty)
                    }
                    is AuthError.FieldShort -> {
                        stringResource(id = R.string.field_too_short)
                    }
                    is AuthError.InvalidEmail -> {
                        stringResource(id = R.string.invalid_email)
                    }
                    else -> ""
                },
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
                error = when (passwordState.authStandardFieldState.error) {
                    is AuthError.FieldEmpty -> {
                        stringResource(id = R.string.filed_no_empty)
                    }
                    is AuthError.FieldShort -> {
                        stringResource(id = R.string.field_too_short)
                    }
                    is AuthError.InvalidPassword -> {
                        stringResource(id = R.string.invalid_password)
                    }
                    else -> ""
                },
                keyboardType = KeyboardType.Password,
                leadingIcon = Icons.Default.Password,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.OnEnterPassword(it))
                },
                onPasswordToggleClick = {
                    viewModel.onEvent(SignUpEvent.OnPasswordToggleClick)
                },
                isPasswordHide = passwordState.isPasswordHide
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
                    } else {
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = null,
                        )
                    }

                }
            }

            Spacer(modifier = Modifier.height(dimens.spaceMedium))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimens.spaceLarge)
                    .clickable(
                        onClick = {
                            onLoginNavigation(null)
                        },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ),
                textAlign = TextAlign.Center,
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
}