package com.robertconstantindinescu.woutapp.core.util

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun LifecycleEventListener(event: (lifeCycle: Lifecycle.Event) -> Unit) {

    val eventHandler  by rememberUpdatedState(newValue = event)
    val lifeCycleOwner by rememberUpdatedState(newValue = LocalLifecycleOwner.current)

    DisposableEffect(lifeCycleOwner) {
        val lifeCycle = lifeCycleOwner.lifecycle
        val observer = LifecycleEventObserver { _, event ->
            eventHandler(event)
        }

        lifeCycle.addObserver(observer)

        onDispose { lifeCycle.removeObserver(observer) }
    }
}