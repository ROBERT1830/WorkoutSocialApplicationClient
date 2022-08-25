

package com.robertconstantindinescu.woutapp.feature_create_post.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.LocalSpacing

@ExperimentalPagerApi
@Composable
fun CreatePostButton(
    modifier: Modifier,
    pagerState: PagerState,
    onCreatePostClick: () -> Unit = {}
) {
    val dimens = LocalSpacing.current
    Spacer(modifier = Modifier.height(dimens.spaceLarge))
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimens.spaceMedium),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            visible = pagerState.currentPage == 1
        ) {
            Button(
                onClick = { onCreatePostClick() },
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Text(text = "Finish")
            }
        }

    }

}