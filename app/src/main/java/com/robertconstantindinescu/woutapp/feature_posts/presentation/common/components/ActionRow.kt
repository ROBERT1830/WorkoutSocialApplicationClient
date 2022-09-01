package com.robertconstantindinescu.woutapp.feature_posts.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.DarkGreen10
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.Green10
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.LocalSpacing

@Composable
fun ActionRow(
    modifier: Modifier,
    favoriteEnabled: Boolean = false,
    shareEnabled: Boolean = false,
    subscribeEnabled: Boolean = false,
    deleteEnabled: Boolean = false,
    isAddedToFavorites: Boolean = false,
    isUserSubscribed: Boolean = false,
    iconSize: Dp = 40.dp,
    onFavoritesClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onSubscribeClick: () -> Unit = {},
    onDeletePost:  () -> Unit = {}
) {
    val dimens = LocalSpacing.current
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (favoriteEnabled) {
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
                    } else Green10
                )
            }
            Spacer(modifier = Modifier.width(dimens.spaceSmall))
        }

        if (shareEnabled) {
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
        }

        Spacer(modifier = Modifier.width(dimens.spaceSmall))

        if (subscribeEnabled) {
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

        if (deleteEnabled) {
            IconButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(dimens.spaceMedium))
                    .size(iconSize),
                onClick = { onDeletePost() }
            ) {
                Icon(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(dimens.spaceSmall),
                    imageVector = Icons.Filled.Delete,
                    contentDescription = if (isAddedToFavorites) {
                        stringResource(id = R.string.remove_from_favorites)
                    } else stringResource(id = R.string.add_to_favorites),
                    tint = if (isAddedToFavorites) {
                        MaterialTheme.colorScheme.primary
                    } else Green10
                )
            }
            Spacer(modifier = Modifier.width(dimens.spaceSmall))
        }
    }
}