package com.robertconstantindinescu.woutapp.feature_create_post.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.PostEvents
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.PostViewModel
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.model.Sport
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.util.CreatePostConstants.NUMBER_OF_CELLS

@Composable
fun MainContentSportType(
    viewModel: PostViewModel = hiltViewModel()
) {
    val state = viewModel.sportTypeState
    Column(modifier = Modifier.fillMaxWidth()) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxHeight(),
            columns = GridCells.Fixed(NUMBER_OF_CELLS),
            content = {
                items(state.sports) { sportType ->
                    SportItem(item = sportType, color = sportType.color, sportType.isSelected){
                        viewModel.onEvent(PostEvents.onSelectSportType(sportType))
                    }
                }
            }
        )
    }
}