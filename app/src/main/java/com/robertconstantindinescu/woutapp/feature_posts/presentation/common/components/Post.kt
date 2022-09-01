package com.robertconstantindinescu.woutapp.feature_posts.presentation.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.LocalSpacing
import com.robertconstantindinescu.woutapp.feature_posts.presentation.common.model.PostVO

@ExperimentalMaterial3Api
@Composable
fun Post(
    post: PostVO,
    imageLoader: ImageLoader,
    deleteEnabled: Boolean = false,
    shareEnabled: Boolean = false,
    favoritesEnabled: Boolean = false,
    subscribeEnabled: Boolean = false,
    onFavoritesClick: () -> Unit = {},
    onShareClick: (postId: String) -> Unit = {},
    onSubscribeClick: () -> Unit = {},
    onDetailNavigate: (postId: String) -> Unit = {},
    onDeletePost: (post: PostVO) -> Unit = {}
) {
    val dimens = LocalSpacing.current


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimens.spaceMedium)
            .shadow(10.dp, shape = RoundedCornerShape(dimens.spaceMedium))
            .clip(RoundedCornerShape(dimens.spaceMedium))
            .background(MaterialTheme.colorScheme.inverseSurface)
            .clickable { onDetailNavigate(post.postId) },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = dimens.spaceMedium, top = dimens.spaceMedium),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(50.dp)
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(dimens.spaceMedium),
                        color = MaterialTheme.colorScheme.background
                    )
                    .clip(RoundedCornerShape(dimens.spaceMedium)),
                contentScale = ContentScale.Crop,
                painter = rememberAsyncImagePainter(
                    model = post.profileImage,
                    imageLoader = imageLoader
                ),
                contentDescription = stringResource(id = R.string.sign_profile_image)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimens.spaceSmall)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = post.userName ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.background
                )

                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.main_feed_participants))
                        append(" ")
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            append("${post.subscriptionsCount}")
                        }
                    },
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.background
                )
            }
        }

        Text(
            modifier = Modifier.padding(start = dimens.spaceMedium),
            text = stringResource(id = R.string.main_feed_location_text, post.location ?: ""),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.background
        )


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

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = dimens.spaceMedium),
                textAlign = TextAlign.Start,
                text = post.sportType ?: "",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.background
            )
            ActionRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimens.spaceMedium, horizontal = dimens.spaceSmall),
                isAddedToFavorites = post.isAddedToFavorites ?: false,
                isUserSubscribed = post.isUserSubscribed ?: false,
                favoriteEnabled = favoritesEnabled, //post.isAddedToFavorites != null,
                subscribeEnabled = subscribeEnabled,
                deleteEnabled = deleteEnabled,
                shareEnabled = shareEnabled,
                onFavoritesClick = {
                    onFavoritesClick()
                },
                onSubscribeClick = {
                    onSubscribeClick()
                },
                onShareClick = {
                    onShareClick(post.postId)
                },
                onDeletePost = {
                    onDeletePost(post)
                }
            )
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.padding(start = dimens.spaceMedium),
                textAlign = TextAlign.Start,
                text = stringResource(id = R.string.main_feed_description_title),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.background
            )
            Text(
                modifier = Modifier.padding(
                    start = dimens.spaceMedium,
                    end = dimens.spaceMedium,
                    bottom = dimens.spaceMedium,
                    top = dimens.spaceSmall
                ),
                textAlign = TextAlign.Justify,
                text = post.description ?: "", //use string resource
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.background,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

    }

}