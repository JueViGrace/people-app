package com.jvg.peopleapp.home.presentation.navigation.routes

import cafe.adriel.voyager.core.screen.Screen
import com.jvg.peopleapp.home.domain.model.Person
import com.jvg.peopleapp.home.presentation.dashboard.screen.DashboardScreen
import com.jvg.peopleapp.home.presentation.details.screen.PersonDetailsScreen

sealed class HomeScreens(val screen: Screen) {
    data object Dashboard : HomeScreens(screen = DashboardScreen)
    data class PersonDetails(val person: Person? = null) : HomeScreens(screen = PersonDetailsScreen(person))
}
