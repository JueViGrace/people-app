package com.jvg.peopleapp.people.presentation.person.state

import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.people.domain.model.Person
import org.mongodb.kbson.ObjectId

data class PersonDetailsState(
    val person: RequestState<Person> = RequestState.Loading,
    val selectedPerson: ObjectId? = null,
    val nameError: String? = null,
    val lastNameError: String? = null,
    val codeError: String? = null,
    val phoneError: String? = null,
    val emailError: String? = null,
    val startsAtError: String? = null,
    val finishesAtError: String? = null,
    val errors: Boolean? = null
)
