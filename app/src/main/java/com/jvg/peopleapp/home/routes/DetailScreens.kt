package com.jvg.peopleapp.home.routes

import cafe.adriel.voyager.core.screen.Screen
import com.jvg.peopleapp.people.presentation.person.screen.CreatePersonScreen
import com.jvg.peopleapp.people.presentation.person.screen.PersonDetailsScreen
import org.mongodb.kbson.ObjectId

sealed class DetailScreens(val screen: Screen) {
    data class PersonDetails(val id: ObjectId) : DetailScreens(screen = PersonDetailsScreen(id))
    data class CreatePerson(val id: ObjectId? = null) : DetailScreens(screen = CreatePersonScreen(id))
}
