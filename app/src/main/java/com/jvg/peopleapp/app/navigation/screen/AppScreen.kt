package com.jvg.peopleapp.app.navigation.screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition

internal data class AppScreen(
    val initialScreen: Screen
) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        Navigator(
            screen = initialScreen,
        ) { navigator ->
            SlideTransition(navigator)
        }
    }
}
