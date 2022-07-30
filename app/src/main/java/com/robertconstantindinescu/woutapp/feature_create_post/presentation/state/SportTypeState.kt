package com.robertconstantindinescu.woutapp.feature_create_post.presentation.state
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.model.Sport
import com.robertconstantindinescu.woutapp.feature_create_post.presentation.model.Sport.Companion.defaultSportTypes

data class SportTypeState(
    val isSelected: Boolean = false,
    val sports: List<Sport> = defaultSportTypes
)
