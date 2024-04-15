package com.jvg.peopleapp.people.presentation.person.events

import com.jvg.peopleapp.people.domain.model.Person
import org.mongodb.kbson.ObjectId

sealed interface PersonEvent {
    data class OnNameChanged(val value: String) : PersonEvent
    data class OnLastNameChanged(val value: String) : PersonEvent
    data class OnCodeChanged(val value: String) : PersonEvent
    data class OnPhoneChanged(val value: String) : PersonEvent
    data class OnEmailChanged(val value: String) : PersonEvent
    data class OnStartsAtChanged(val value: String) : PersonEvent
    data class OnFinishesAtChanged(val value: String) : PersonEvent
    data class OnActiveChanged(val value: Boolean) : PersonEvent
    data class SetActive(val id: ObjectId?, val isActive: Boolean) : PersonEvent
    data object OnAddNewPerson : PersonEvent
    data object SavePerson : PersonEvent
    data object DissmissPerson : PersonEvent
    data class SelectedPerson(val person: Person) : PersonEvent
    data class EditPerson(val person: Person) : PersonEvent
    data class DeletePerson(val id: ObjectId? = null) : PersonEvent
}
