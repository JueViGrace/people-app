package com.jvg.peopleapp.home.presentation.navigation.navigator

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.jvg.peopleapp.home.presentation.navigation.routes.HomeScreens

object HomeNavigator : Screen {
    private fun readResolve(): Any = HomeNavigator

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        Navigator(
            screen = HomeScreens.Dashboard.screen,
            key = key
        ) { navigator: Navigator ->
            SlideTransition(navigator = navigator)
        }
    }
}
