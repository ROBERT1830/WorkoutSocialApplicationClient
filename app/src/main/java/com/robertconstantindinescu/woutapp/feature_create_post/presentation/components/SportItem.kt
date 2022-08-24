package com.robertconstantindinescu.woutapp.feature_create_post.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.DarkGreen10
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.DarkGreen80
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.Grey99
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.LocalSpacing
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.model.Sport

@Composable
fun SportItem(
    item: Sport,
    color: Color,
    isSelected: Boolean = false,
    onSelectClick: () -> Unit = {}
) {

    val context = LocalContext.current
    val dimens = LocalSpacing.current


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .aspectRatio(1f)
            .shadow(dimens.spaceSmall, RoundedCornerShape(dimens.spaceSmall))
            .clip(RoundedCornerShape(dimens.spaceSmall))
            .background(color)
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(dimens.spaceMedium)) {
            Text(
                text = item.type.text,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 26.sp, //space betwen text vertically
                modifier = Modifier.align(Alignment.TopStart),
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
            )
            Icon(
                painter = painterResource(id = item.drawableRes),
                contentDescription = item.type.text,
                tint = Grey99,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .size(dimens.IconSizeMedium)
            )
            Text(
                text = stringResource(id = R.string.select_sport),
                style = MaterialTheme.typography.bodyMedium,
                color =  DarkGreen10,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(dimens.spaceSmall))
                    .background(if (isSelected) DarkGreen80 else Grey99)
                    .clickable {
                        onSelectClick()
                    }
                    .padding(dimens.spaceSmall)

            )
        }

    }
}