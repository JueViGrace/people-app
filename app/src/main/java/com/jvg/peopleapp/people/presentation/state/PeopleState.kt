package com.jvg.peopleapp.people.presentation.state

import androidx.paging.PagingData
import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.people.domain.model.Person
import kotlinx.coroutines.flow.Flow

data class PeopleState(
    val people: RequestState<Flow<PagingData<Person>>> = RequestState.Loading,
    val activePeople: RequestState<List<Person>> = RequestState.Loading,
    val inactivePeople: RequestState<List<Person>> = RequestState.Loading
)
