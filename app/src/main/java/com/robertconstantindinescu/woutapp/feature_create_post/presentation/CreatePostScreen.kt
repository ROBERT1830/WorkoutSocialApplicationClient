package com.robertconstantindinescu.woutapp.feature_create_post.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.robertconstantindinescu.woutapp.core.util.UiEvent
import com.robertconstantindinescu.woutapp.core.util.UiText
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.components.CreatePostButton
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.components.PagerScreen
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.model.CreatePostPage
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.util.CreatePostConstants.HORIZONTAL_PAGER_WEIGHT
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.util.CreatePostConstants.NUMBER_OF_PAGES
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.util.CreatePostConstants.PAGING_INDICATOR_WIDTH
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterial3Api
@ExperimentalPagerApi
@Composable
fun CreatePostScreen(
    viewModel: PostViewModel = hiltViewModel(),
    onShowSnackBar: (text: UiText) -> Unit = {},
    onNavigateUp: () -> Unit = {}

) {
    val context = LocalContext.current
    //Pages
    val pages = listOf(
        CreatePostPage.First,
        CreatePostPage.Second
    )

    //Create pagerState that is remembered across compositions
    val pagerState = rememberPagerState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { uiEvent ->
            when (uiEvent) {
                is UiEvent.ShowSnackBar -> {
                    onShowSnackBar(uiEvent.text)
                }
                is UiEvent.NavigateUp -> {
                    onNavigateUp()
                }
                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {

        HorizontalPagerIndicator(
            modifier = Modifier
                .weight(0.5f)
                .align(Alignment.CenterHorizontally),
            pagerState = pagerState,
            activeColor = MaterialTheme.colorScheme.primary,
            inactiveColor = MaterialTheme.colorScheme.onBackground,
            indicatorWidth = PAGING_INDICATOR_WIDTH,
            spacing = PAGING_INDICATOR_WIDTH
        )
        
        CreatePostButton(modifier = Modifier.weight(0.5f), pagerState = pagerState) {
            viewModel.onEvent(PostEvents.onCreatePostClick(context))
        }

        HorizontalPager(
            modifier = Modifier.weight(HORIZONTAL_PAGER_WEIGHT),
            state = pagerState,
            count = NUMBER_OF_PAGES,
            verticalAlignment = Alignment.Top
        ) { positionPage ->

            PagerScreen(createPostPage = pages[positionPage])
        }
    }
}