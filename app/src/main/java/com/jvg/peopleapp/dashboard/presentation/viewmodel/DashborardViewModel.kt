package com.jvg.peopleapp.dashboard.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jvg.peopleapp.core.common.Constants.DELAY
import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.person.data.local.sources.PeopleDataSource
import com.jvg.peopleapp.dashboard.presentation.state.DashboardState
import com.jvg.peopleapp.person.presentation.events.PeopleActions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class DashborardViewModel(
    private val peopleDataSource: PeopleDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : ScreenModel {
    private var _state: MutableStateFlow<DashboardState> = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state.asStateFlow()

    init {
        _state.update { s ->
            s.copy(activePeople = RequestState.Loading, inactivePeople = RequestState.Loading)
        }
        screenModelScope.launch {
            delay(DELAY)
            peopleDataSource.getActivePeople().collect { value ->
                _state.update { s ->
                    s.copy(
                        activePeople = value
                    )
                }
            }
        }

        screenModelScope.launch {
            delay(DELAY)
            peopleDataSource.getInactivePeople().collect { value ->
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
                setActive(action.id, action.isActive)
            }
            is PeopleActions.Delete -> {
                deleteEmployee(action.id)
            }
            else -> {}
        }
    }

    private fun setActive(id: ObjectId?, isActive: Boolean) {
        screenModelScope.launch(ioDispatcher) {
            peopleDataSource.setActive(id, isActive)
        }
    }

    private fun deleteEmployee(id: ObjectId?) {
        screenModelScope.launch(ioDispatcher) {
            peopleDataSource.deletePerson(id)
        }
    }
}
