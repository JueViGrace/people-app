package com.jvg.peopleapp.people.presentation.events

sealed interface PeopleEvents {
    data class OnDeletePerson(val id: String?) : PeopleEvents
    data class OnSetActive(val id: String?, val isActive: Boolean) : PeopleEvents
}
