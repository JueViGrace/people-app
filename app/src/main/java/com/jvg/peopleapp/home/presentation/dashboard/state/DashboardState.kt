package com.jvg.peopleapp.home.presentation.dashboard.state

import com.jvg.peopleapp.home.domain.model.Person
import com.jvg.peopleapp.home.presentation.state.RequestState

data class DashboardState(
    val activePeople: RequestState<List<Person>> = RequestState.Idle,
    val inactivePeople: RequestState<List<Person>> = RequestState.Idle,
)
