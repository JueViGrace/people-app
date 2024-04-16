package com.jvg.peopleapp.people.presentation.events

import org.mongodb.kbson.ObjectId

sealed interface PeopleEvents {
    data class OnDeletePerson(val id: ObjectId?) : PeopleEvents
    data class OnSetActive(val id: ObjectId?, val isActive: Boolean) : PeopleEvents
}
