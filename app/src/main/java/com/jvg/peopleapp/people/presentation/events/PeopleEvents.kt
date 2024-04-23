package com.jvg.peopleapp.people.presentation.events

sealed interface PeopleEvents {
    data class OnSoftDeletePerson(val id: String?) : PeopleEvents
    data class OnSetActive(val id: String?, val isActive: Boolean) : PeopleEvents
}
