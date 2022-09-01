package com.robertconstantindinescu.woutapp.feature_create_post.presentation.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.robertconstantindinescu.woutapp.R
import com.robertconstantindinescu.woutapp.core.presentation.ui.theme.*

data class Sport(
    val type: SportType,
    @DrawableRes val drawableRes: Int,
    val isSelected: Boolean = false,
    val color: Color
) {
    enum class SportType(val text: String) {
        SWIMMING("swimming"),
        TENNIS("tennis"),
        TABLE_TENNIS("table tennis"),
        RUNNING("running"),
        GYMNASIUM("gymnasium"),
        VOLLEYBALL("volleyball")
    }
    companion object {
        //default sportTypes

        val defaultSportTypes = listOf(
            Sport(
                type = SportType.SWIMMING,
                drawableRes = R.drawable.ic_swim,
                color = BlueViolet
            ),
            Sport(
                type = SportType.TENNIS,
                drawableRes = R.drawable.ic_tenis,
                color = LightGreen
            ),
            Sport(
                type = SportType.TABLE_TENNIS,
                drawableRes = R.drawable.ic_ping_pong,
                color = Beige
            ),
            Sport(
                type = SportType.RUNNING,
                drawableRes = R.drawable.ic_run,
                color = OrangeYellow
            ),
            Sport(
                type = SportType.GYMNASIUM,
                drawableRes = R.drawable.ic_gym,
                color = AquaBlue
            ),
            Sport(
                type = SportType.VOLLEYBALL,
                drawableRes = R.drawable.ic_volleyball,
                color = LightRed
            )
        )
    }
}

