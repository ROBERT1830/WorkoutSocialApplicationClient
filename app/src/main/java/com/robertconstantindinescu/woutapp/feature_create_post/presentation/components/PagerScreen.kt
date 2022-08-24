package com.robertconstantindinescu.woutapp.feature_create_post.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.LocalSpacing
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.PostViewModel
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.model.CreatePostPage

@Composable
fun PagerScreen(createPostPage: CreatePostPage) {

    val dimens = LocalSpacing.current
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth().padding(dimens.spaceExtraLarge),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = createPostPage.title.asString(context),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(dimens.spaceSmall))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = createPostPage.description.asString(context),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center
                    )

                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                createPostPage.mainContent.invoke()
            }
        }


    }

}