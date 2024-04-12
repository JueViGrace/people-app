package com.jvg.peopleapp.app.navigation.routes

import cafe.adriel.voyager.core.screen.Screen
import com.jvg.peopleapp.core.presentation.navigation.home.navigator.HomeNavigator

sealed class AppRoutes(val screen: Screen) {
    data object Home : AppRoutes(screen = HomeNavigator)
}