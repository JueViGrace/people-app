package com.jvg.peopleapp.home.presentation.dashboard.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jvg.peopleapp.core.common.Constants.DELAY
import com.jvg.peopleapp.home.domain.model.Person
import com.jvg.peopleapp.home.domain.sources.HomeDataSource
import com.jvg.peopleapp.home.presentation.dashboard.state.DashboardState
import com.jvg.peopleapp.home.presentation.events.PeopleActions
import com.jvg.peopleapp.home.presentation.state.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

typealias MutablePeople = MutableStateFlow<DashboardState>
typealias People = StateFlow<DashboardState>

class DashborardViewModel(
    private val homeDataSource: HomeDataSource
) : ScreenModel {
    private var _state: MutablePeople = MutableStateFlow(DashboardState())
    val state: People = _state

    init {
        _state.update { s  ->
            s.copy(activePeople = RequestState.Loading, inactivePeople = RequestState.Loading)
        }
        screenModelScope.launch {
            delay(DELAY)
            homeDataSource.getActivePeople().collectLatest { value ->
                _state.update { s ->
                    s.copy(
                        activePeople = value
                    )
                }
            }
        }

        screenModelScope.launch {
            delay(DELAY)
            homeDataSource.getInactivePeople().collectLatest { value ->
                _state.update { s ->
                    s.copy(
                        inactivePeople = value
                    )
                }
            }
        }
    }

    fun setActions(action: PeopleActions) {
        when (action) {
            is PeopleActions.SetActive -> {
                setActive(action.person, action.isActive)
            }
            is PeopleActions.Delete -> {
                deleteEmployee(action.person)
            }
            else -> {}
        }
    }

    private fun setActive(person: Person, isActive: Boolean) {
        screenModelScope.launch(Dispatchers.IO) {
            homeDataSource.setActive(person, isActive)
        }
    }

    private fun deleteEmployee(person: Person) {
        screenModelScope.launch(Dispatchers.IO) {
            homeDataSource.deletePerson(person)
        }
    }
}
