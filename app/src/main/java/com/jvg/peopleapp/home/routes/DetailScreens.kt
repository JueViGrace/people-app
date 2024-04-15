package com.jvg.peopleapp.home.routes

import cafe.adriel.voyager.core.screen.Screen
import com.jvg.peopleapp.people.domain.model.Person
import com.jvg.peopleapp.people.presentation.person.screen.CreatePersonScreen
import com.jvg.peopleapp.people.presentation.person.screen.PersonDetailsScreen

sealed class DetailScreens(val screen: Screen) {
    data class PersonDetails(val person: Person? = null) : DetailScreens(screen = PersonDetailsScreen(person))
    data class CreatePerson(val person: Person? = null) : DetailScreens(screen = CreatePersonScreen(person))
}