package com.jvg.peopleapp.home.routes

import cafe.adriel.voyager.core.screen.Screen
import com.jvg.peopleapp.dashboard.presentation.navigation.screen.DashboardScreen
import com.jvg.peopleapp.people.presentation.navigation.screen.PeopleScreen

sealed class HomeScreens(val screen: Screen) {
    data object Dashboard : HomeScreens(screen = DashboardScreen)
    data object People : HomeScreens(screen = PeopleScreen)
}