package com.jvg.peopleapp.home.presentation.events

import com.jvg.peopleapp.home.domain.model.Person

sealed interface PeopleActions {
    data class Add(val person: Person) : PeopleActions
    data class Update(val person: Person) : PeopleActions
    data class Delete(val person: Person) : PeopleActions
    data class SetActive(val person: Person, val isActive: Boolean) : PeopleActions
}
