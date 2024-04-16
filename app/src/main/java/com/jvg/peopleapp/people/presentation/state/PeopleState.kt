package com.jvg.peopleapp.people.presentation.state

import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.people.domain.model.Person

data class PeopleState(
    val people: RequestState<List<Person>> = RequestState.Loading,
    val activePeople: RequestState<List<Person>> = RequestState.Loading,
    val inactivePeople: RequestState<List<Person>> = RequestState.Loading
)
