package com.robertconstantindinescu.woutapp.feature_posts.presentation.post_details

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.LocalSpacing
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.util.CreatePostConstants.IMAGE_HEIGHT
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.util.CreatePostConstants.IMAGE_WIDTH

@Composable
fun PostDetailsScreen(
    imageLoader: ImageLoader,
    viewModel: PostDetailsViewModel = hiltViewModel()
) {
    val dimens = LocalSpacing.current
    val state = viewModel.state
    Log.d("state", state.post.toString())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimens.spaceMedium),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    modifier = Modifier
                        .height(100.dp)
                        .clip(CircleShape),
                    painter = rememberAsyncImagePainter(
                        model = state.post?.profileImage,
                        imageLoader = imageLoader
                    ),
                    contentDescription = "Profile picture",
                )

                Spacer(modifier = Modifier.height(dimens.spaceSmall))

                Text(
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.post_details_username))
                        append(" ")
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            append("${state.post?.userName}")
                        }
                    },
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Spacer(modifier = Modifier.height(dimens.spaceSmall))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimens.spaceMedium),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .aspectRatio(IMAGE_WIDTH / IMAGE_HEIGHT)
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        BorderStroke(1.dp, color = MaterialTheme.colorScheme.onBackground),
                        shape = RoundedCornerShape(10.dp)
                    ),
                painter = rememberAsyncImagePainter(
                    model = state.post?.imageUrl,
                    imageLoader = imageLoader
                ),
                contentDescription = "Profile picture",
            )
        }

        Spacer(modifier = Modifier.height(dimens.spaceSmall))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimens.spaceMedium),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.sport_type))
                        append(": ")
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            append("${state.post?.sportType}")
                        }
                    },
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(dimens.spaceSmall))

                Text(
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.main_feed_participants))
                        append(" ")
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            append("${state.post?.subscriptionsCount}")
                        }
                    },
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimens.spaceMedium),
            ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.post_details_description_title),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(dimens.spaceSmall))
                Text(
                    text = state.post?.description ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Justify
                )

            }
        }

    }

}

























