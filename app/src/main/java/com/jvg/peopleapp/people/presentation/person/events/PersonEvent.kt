package com.jvg.peopleapp.people.presentation.person.events

sealed interface PersonEvent {
    data class OnNameChanged(val value: String) : PersonEvent
    data class OnLastNameChanged(val value: String) : PersonEvent
    data class OnCodeChanged(val value: String) : PersonEvent
    data class OnPhoneChanged(val value: String) : PersonEvent
    data class OnEmailChanged(val value: String) : PersonEvent
    data class OnStartsAtChanged(val value: String) : PersonEvent
    data class OnFinishesAtChanged(val value: String) : PersonEvent
    data class OnActiveChanged(val value: Boolean) : PersonEvent
    data object SavePerson : PersonEvent
    data object DismissPerson : PersonEvent
    data object DeletePerson : PersonEvent
}
