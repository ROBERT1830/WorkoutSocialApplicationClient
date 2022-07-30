package com.robertconstantindinescu.woutapp.feature_create_post.presentation.components

import android.widget.ScrollView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.presentation.components.StandardTexField
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.LocalSpacing
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.PostEvents
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.PostViewModel
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.util.CreatePostConstants.IMAGE_HEIGHT
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.util.CreatePostConstants.IMAGE_WIDTH
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.util.CropActivityResultContract

@Composable
fun MainContentPostForms(
    imageLoader: ImageLoader,
    onNavigateUp: () -> Unit = {},
    viewModel: PostViewModel = hiltViewModel()
) {
    val dimens = LocalSpacing.current
    val imageUriState = viewModel.postImageState
    val formsState = viewModel.formsState

    //Gallery and Cropper
    val cropActivityLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(IMAGE_WIDTH, IMAGE_HEIGHT)
    ) {
        viewModel.onEvent(PostEvents.onCropImage(it))
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        //this makes the user pick some content (images)
        contract = ActivityResultContracts.GetContent()
    ){
        cropActivityLauncher.launch(it)
    }


    Column(modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(dimens.spaceMedium)) {
            Box(
                modifier = Modifier
                    .aspectRatio(IMAGE_WIDTH / IMAGE_HEIGHT)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = MaterialTheme.shapes.medium
                    )
                    .clickable {
                        galleryLauncher.launch("image/*")

                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AddAPhoto,
                    contentDescription = stringResource(id = R.string.create_post_screen_choose_image),
                    tint = MaterialTheme.colorScheme.onSurface
                )

                //if imageUri is not null, the user picked something
                imageUriState?.let { uri ->
                    Image(
                        modifier = Modifier.matchParentSize(),
                        painter = rememberAsyncImagePainter(model = uri, imageLoader = imageLoader),
                        contentDescription = stringResource(id = R.string.create_post_screen_post_image)
                    )

                }
            }
        }
        Spacer(modifier = Modifier.height(dimens.spaceMedium))
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = dimens.spaceMedium, vertical = dimens.spaceSmall)) {
            StandardTexField(
                //modifier = Modifier.padding(dimens.spaceSmall),
                text = formsState.description,
                singleLine = false,
                maxLines = 5,
                onValueChange = {
                    viewModel.onEvent(PostEvents.onEnterDesscription(it))
                },
                hint = stringResource(id = R.string.create_post_screen_description_hint)
            )
        }
        //Spacer(modifier = Modifier.height(dimens.spaceSmall))
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = dimens.spaceMedium, vertical = dimens.spaceSmall)) {
            StandardTexField(
                //modifier = Modifier.padding(dimens.spaceSmallM),
                text = formsState.location,
                singleLine = false,
                maxLines = 2,
                onValueChange = {
                    viewModel.onEvent(PostEvents.onEnterLocation(it))
                },
                hint = stringResource(id = R.string.create_post_screen_location_hint)
            )
        }

    }

}