package com.jvg.peopleapp.core.presentation.navigation.details

import cafe.adriel.voyager.core.screen.Screen
import com.jvg.peopleapp.person.domain.model.Person
import com.jvg.peopleapp.person.presentation.details.screen.PersonDetailsScreen

sealed class DetailScreens(val screen: Screen) {
    data class PersonDetails(val person: Person? = null) : DetailScreens(screen = PersonDetailsScreen(person))
}