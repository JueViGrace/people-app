package com.jvg.peopleapp.people.presentation.state

import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.people.domain.model.Person

data class PeopleState(
    val people: RequestState<List<Person>> = RequestState.Idle,
    val activePeople: RequestState<List<Person>> = RequestState.Idle,
    val inactivePeople: RequestState<List<Person>> = RequestState.Idle,
    val selectedPerson: Person? = null,
    val nameError: String? = null,
    val lastNameError: String? = null,
    val codeError: String? = null,
    val phoneError: String? = null,
    val emailError: String? = null,
    val startsAtError: String? = null,
    val finishesAtError: String? = null,
    val errors: Boolean? = null
)
