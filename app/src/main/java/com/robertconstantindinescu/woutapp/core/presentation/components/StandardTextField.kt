package com.robertconstantindinescu.woutapp.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.LocalSpacing

@ExperimentalMaterial3Api
@Composable
fun StandardTexField(
    modifier: Modifier = Modifier,
    text: String = "",
    hint: String = "",
    maxLength: Int = 400,
    error: String = "",
    textStyle: TextStyle = TextStyle(
        color = MaterialTheme.colorScheme.onSurface
    ),
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    roundedCorner: RoundedCornerShape = RoundedCornerShape(10.dp),
    singleLine: Boolean = true,
    maxLines: Int = 1,
    leadingIcon: ImageVector? = null,
    leadingIconSize: Dp = LocalSpacing.current.IconSizeMedium,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPasswordToggleDisplayed: Boolean = keyboardType == KeyboardType.Password,
    onValueChange: (String) -> Unit = {},
    isPasswordHide: Boolean = true,
    onPasswordToggleClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        // TODO: Round borders nad shadow
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(5.dp),
            shape = roundedCorner
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = {
                    if (it.length <= maxLength) onValueChange(it)
                },
                maxLines = maxLines,
                textStyle = textStyle,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = backgroundColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(text = hint, style = MaterialTheme.typography.bodyMedium)
                },
                isError = error != "",
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType
                ),
                visualTransformation = if (isPasswordHide && isPasswordToggleDisplayed) {
                    PasswordVisualTransformation()
                } else {
                    VisualTransformation.None
                },
                singleLine = singleLine,
                leadingIcon = if (leadingIcon != null) {
                    {
                        Icon(
                            imageVector = leadingIcon,
                            contentDescription = stringResource(id = R.string.icon_text_field),
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(leadingIconSize)
                        )
                    }
                } else null,
                trailingIcon = if (isPasswordToggleDisplayed) {
                    {
                        IconButton(onClick = { onPasswordToggleClick() }) {
                            Icon(
                                imageVector = if (isPasswordHide) {
                                    Icons.Filled.VisibilityOff
                                } else {
                                    Icons.Filled.Visibility

                                }, contentDescription = if (isPasswordHide) {
                                    stringResource(R.string.password_visible_content_description)
                                } else {
                                    stringResource(R.string.password_hidden_content_description)
                                },
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                } else null,
                )
        }
        if (error.isNotEmpty()) {
            Text(
                text = error,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }

    }

}