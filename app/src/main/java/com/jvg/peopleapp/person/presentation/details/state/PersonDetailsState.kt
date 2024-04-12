package com.jvg.peopleapp.person.presentation.details.state

import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.person.domain.model.Person

data class PersonDetailsState(
    val person: RequestState<Person> = RequestState.Idle
)
