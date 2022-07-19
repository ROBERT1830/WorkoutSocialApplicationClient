package com.robertconstantindinescu.woutapp.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun StandartButton(
    modifier: Modifier = Modifier,
    text: String = "",
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    icon: ImageVector? = null,
    backGroundColor: Color = MaterialTheme.colorScheme.onSurface,
    onButtonClick: () -> Unit = {}
) {
    Button(
        modifier = modifier,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 10.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp
        ),
        onClick = { },
    ) {
        icon?.let {
            Icon(
                modifier = modifier,
                imageVector = icon,
                contentDescription = null )
        }
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.bodyMedium
        )

    }

}