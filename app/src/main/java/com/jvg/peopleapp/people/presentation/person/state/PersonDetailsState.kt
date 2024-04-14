package com.jvg.peopleapp.people.presentation.person.state

import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.people.domain.model.Person

data class PersonDetailsState(
    val person: RequestState<Person> = RequestState.Idle
)
