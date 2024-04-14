package com.jvg.peopleapp.people.presentation.events

import com.jvg.peopleapp.people.domain.model.Person
import org.mongodb.kbson.ObjectId

sealed interface PeopleActions {
    data class Add(val person: Person) : PeopleActions
    data class Update(val person: Person) : PeopleActions
    data class Delete(val id: ObjectId?) : PeopleActions
    data class SetActive(val id: ObjectId?, val isActive: Boolean) : PeopleActions
}
