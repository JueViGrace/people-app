package com.jvg.peopleapp.home.presentation.details.state

import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.home.domain.model.Person

data class PersonDetailsState(
    val person: RequestState<Person> = RequestState.Idle
)
