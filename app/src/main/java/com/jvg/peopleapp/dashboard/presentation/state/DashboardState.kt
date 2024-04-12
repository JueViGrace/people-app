package com.jvg.peopleapp.dashboard.presentation.state

import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.person.domain.model.Person

data class DashboardState(
    val activePeople: RequestState<List<Person>> = RequestState.Idle,
    val inactivePeople: RequestState<List<Person>> = RequestState.Idle,
)
