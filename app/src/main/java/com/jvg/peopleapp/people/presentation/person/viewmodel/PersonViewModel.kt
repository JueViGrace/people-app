package com.jvg.peopleapp.people.presentation.person.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.people.data.local.sources.PeopleDataSource
import com.jvg.peopleapp.people.domain.model.Person
import com.jvg.peopleapp.people.domain.rules.PersonValidator
import com.jvg.peopleapp.people.presentation.person.events.PersonEvent
import com.jvg.peopleapp.people.presentation.person.state.PersonDetailsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class PersonViewModel(
    private val peopleDataSource: PeopleDataSource,
    private val id: ObjectId? = null
) : ScreenModel {

    private var _state: MutableStateFlow<PersonDetailsState> = MutableStateFlow(PersonDetailsState())
    val state: StateFlow<PersonDetailsState> = _state.asStateFlow()

    var newPerson: Person? by mutableStateOf(null)
        private set

    init {
        if (id != null) {
            _state.update { personDetailsState ->
                personDetailsState.copy(
                    person = RequestState.Loading
                )
            }

            screenModelScope.launch {
                peopleDataSource.getOneById(id).collect { value ->
                    _state.update { personDetailsState ->
                        personDetailsState.copy(
                            person = value,
                            selectedPerson = value.getSuccessDataOrNull()?.id
                        )
                    }
                    newPerson = value.getSuccessDataOrNull()
                }
            }
        } else {
            newPerson = Person()
        }
    }

    fun onEvent(event: PersonEvent) {
        when (event) {
            is PersonEvent.DeletePerson -> {
                screenModelScope.launch(Dispatchers.IO) {
                    _state.value.selectedPerson?.let { id ->
                        peopleDataSource.deletePerson(id)
                    }
                }
            }
            is PersonEvent.OnActiveChanged -> {
                newPerson = newPerson?.copy(
                    active = event.value
                )
            }
            is PersonEvent.OnCodeChanged -> {
                newPerson = newPerson?.copy(
                    code = event.value
                )
            }
            is PersonEvent.OnEmailChanged -> {
                newPerson = newPerson?.copy(
                    email = event.value
                )
            }
            is PersonEvent.OnFinishesAtChanged -> {
                newPerson = newPerson?.copy(
                    finishesAt = event.value
                )
            }
            is PersonEvent.OnLastNameChanged -> {
                newPerson = newPerson?.copy(
                    lastname = event.value
                )
            }
            is PersonEvent.OnNameChanged -> {
                newPerson = newPerson?.copy(
                    name = event.value
                )
            }
            is PersonEvent.OnPhoneChanged -> {
                newPerson = newPerson?.copy(
                    phone = event.value
                )
            }
            is PersonEvent.OnStartsAtChanged -> {
                newPerson = newPerson?.copy(
                    startsAt = event.value
                )
            }
            PersonEvent.SavePerson -> {
                newPerson?.let { person ->
                    val result = PersonValidator.validatePerson(person)
                    val errors = listOfNotNull(
                        result.nameError,
                        result.lastnameError,
                        result.codeError,
                        result.phoneError,
                        result.emailError,
                        result.startsAtError,
                        result.finishesAtError
                    )

                    if (errors.isEmpty()) {
                        _state.update { peopleState ->
                            peopleState.copy(
                                nameError = null,
                                lastNameError = null,
                                codeError = null,
                                phoneError = null,
                                emailError = null,
                                startsAtError = null,
                                finishesAtError = null,
                                errors = false
                            )
                        }

                        screenModelScope.launch(Dispatchers.IO) {
                            peopleDataSource.addPerson(person)
                        }
                    } else {
                        _state.update { peopleState ->
                            peopleState.copy(
                                nameError = result.nameError,
                                lastNameError = result.lastnameError,
                                codeError = result.codeError,
                                phoneError = result.phoneError,
                                emailError = result.emailError,
                                startsAtError = result.startsAtError,
                                finishesAtError = result.finishesAtError,
                                errors = true
                            )
                        }
                    }
                }
            }

            PersonEvent.DismissPerson -> {
                _state.update { peopleState ->
                    peopleState.copy(
                        nameError = null,
                        lastNameError = null,
                        codeError = null,
                        phoneError = null,
                        emailError = null,
                        startsAtError = null,
                        finishesAtError = null,
                        selectedPerson = null,
                        errors = null
                    )
                }
                newPerson = null
            }
        }
    }

    fun getError() = _state.value.errors == false
}
