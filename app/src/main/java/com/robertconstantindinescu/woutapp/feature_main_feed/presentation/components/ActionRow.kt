package com.robertconstantindinescu.woutapp.feature_main_feed.presentation.components

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.*

@Composable
fun ActionRow(
    modifier: Modifier,
    isAddedToFavorites: Boolean = false,
    isUserSubscribed: Boolean = false,
    iconSize: Dp = 40.dp,
    onFavoritesClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onSubscribeClick: () -> Unit = {}
) {
    val dimens = LocalSpacing.current
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier
                .clip(RoundedCornerShape(dimens.spaceMedium))
                .size(iconSize),
            onClick = { onFavoritesClick() }
        ) {
            Icon(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(dimens.spaceSmall),
                imageVector = Icons.Filled.Favorite,
                contentDescription = if (isAddedToFavorites) {
                    stringResource(id = R.string.remove_from_favorites)
                } else stringResource(id = R.string.add_to_favorites),
                tint = if (isAddedToFavorites) {
                    MaterialTheme.colorScheme.primary
                } else GreenGrey90
            )
        }
        Spacer(modifier = Modifier.width(dimens.spaceSmall))
        IconButton(
            modifier = Modifier
                .clip(RoundedCornerShape(dimens.spaceMedium))
                .size(iconSize),
            onClick = { onShareClick() }
        ) {
            Icon(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(dimens.spaceSmall),
                imageVector = Icons.Filled.Share,
                contentDescription = stringResource(id = R.string.share_post)
            )
        }
        Spacer(modifier = Modifier.width(dimens.spaceSmall))

        Text(
            text = stringResource(id = R.string.main_feed_subscribe_event),
            style = MaterialTheme.typography.bodyMedium,
            color = DarkGreen10,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clip(RoundedCornerShape(dimens.spaceSmall))
                .background(
                    if (isUserSubscribed)
                        MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface
                )
                .clickable {
                    onSubscribeClick()
                }
                .padding(dimens.spaceSmall)

        )
    }

}