package com.robertconstantindinescu.woutapp.feature_main_feed.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.LocalSpacing
import com.robertconstantindinescu.woutapp.feature_main_feed.presentation.model.MainFeedPostVO

@ExperimentalMaterial3Api
@Composable
fun Post(
    post: MainFeedPostVO,
    imageLoader: ImageLoader,
    onFavoritesClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onSubscribeClick: () -> Unit = {}
) {
    val dimens = LocalSpacing.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimens.spaceLarge)
            .clip(RoundedCornerShape(dimens.spaceMedium))
            .background(MaterialTheme.colorScheme.inverseSurface),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.align(Alignment.Start),
                textAlign = TextAlign.Start,
                text = post.userName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.background
            )
            Text(
                modifier = Modifier.align(Alignment.Start),
                textAlign = TextAlign.Start,
                text = post.sportType,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.background
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimens.spaceMedium)
                .clip(
                    RoundedCornerShape(dimens.spaceMedium)
                )
                .shadow(10.dp)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
                painter = rememberAsyncImagePainter(
                    model = post.imageUrl,
                    imageLoader = imageLoader
                ),
                contentDescription = stringResource(id = R.string.main_feed_post_image),
                contentScale = ContentScale.Crop
            )
        }


        ActionRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimens.spaceMedium, horizontal = dimens.spaceSmall),
            isAddedToFavorites = post.isAddedToFavorites,
            isUserSubscribed = post.isUserSubscribed

        )

    }

}