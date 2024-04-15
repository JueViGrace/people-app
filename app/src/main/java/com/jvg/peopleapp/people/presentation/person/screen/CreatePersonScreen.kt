package com.jvg.peopleapp.people.presentation.person.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jvg.peopleapp.R
import com.jvg.peopleapp.core.presentation.ui.components.AppBar
import com.jvg.peopleapp.core.presentation.ui.components.CustomText
import com.jvg.peopleapp.core.presentation.ui.components.TimePickerComponent
import com.jvg.peopleapp.people.domain.model.Person
import com.jvg.peopleapp.people.presentation.person.components.TextFieldComponent
import com.jvg.peopleapp.people.presentation.person.events.PersonEvent
import com.jvg.peopleapp.people.presentation.viewmodel.PeopleViewModel

data class CreatePersonScreen(val person: Person? = null) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.getNavigatorScreenModel<PeopleViewModel>()
        val state = viewModel.state.collectAsState()
        val person = viewModel.newPerson

        val topBarTitle = if (
            person?.name?.isNotEmpty() == true && person.lastname.isNotEmpty()
        ) {
            "${person.name} ${person.lastname}"
        } else {
            "Cree una nueva persona"
        }

        Scaffold(
            topBar = {
                AppBar(
                    title = topBarTitle,
                    icon = rememberVectorPainter(Icons.AutoMirrored.Default.ArrowBack),
                    onClick = {
                        viewModel.onEvent(PersonEvent.DissmissPerson)
                        navigator.pop()
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        viewModel.onEvent(PersonEvent.SavePerson)
                        state.value.errors?.let { errors ->
                            if (!errors) {
                                navigator.pop()
                            }
                        }
                    },
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Check icon")
                }
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding()
                    ),
                verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Icon(
                        imageVector = Icons.Outlined.AccountCircle,
                        modifier = Modifier.size(150.dp),
                        contentDescription = "Person"
                    )

                    TextFieldComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        value = person?.name ?: "",
                        newValue = { newValue ->
                            viewModel.onEvent(PersonEvent.OnNameChanged(newValue))
                        },
                        label = "Nombre",
                        placeholder = "Nombre de la persona...",
                        supportingText = state.value.nameError,
                        errorStatus = state.value.nameError?.isNotEmpty() ?: true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text
                        ),
                        icon = R.drawable.ic_person_24px
                    )

                    TextFieldComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        value = person?.lastname ?: "",
                        newValue = { newValue ->
                            viewModel.onEvent(PersonEvent.OnLastNameChanged(newValue))
                        },
                        label = "Apellido",
                        placeholder = "Apellido de la persona...",
                        supportingText = state.value.lastNameError,
                        errorStatus = state.value.lastNameError?.isNotEmpty() ?: true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text
                        ),
                        icon = R.drawable.ic_person_24px
                    )

                    TextFieldComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        value = person?.code ?: "",
                        newValue = { newValue ->
                            viewModel.onEvent(PersonEvent.OnCodeChanged(newValue))
                        },
                        label = "Cédula",
                        placeholder = "Cédula de la persona...",
                        supportingText = state.value.codeError,
                        errorStatus = state.value.codeError?.isNotEmpty() ?: true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Number
                        ),
                        icon = R.drawable.ic_id_card_24px
                    )

                    TextFieldComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        value = person?.phone ?: "",
                        newValue = { newValue ->
                            viewModel.onEvent(PersonEvent.OnPhoneChanged(newValue))
                        },
                        label = "Teléfono",
                        placeholder = "Teléfono de la persona...",
                        supportingText = state.value.phoneError,
                        errorStatus = state.value.phoneError?.isNotEmpty() ?: true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Phone
                        ),
                        icon = R.drawable.ic_call_24px
                    )

                    TextFieldComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        value = person?.email ?: "",
                        newValue = { newValue ->
                            viewModel.onEvent(PersonEvent.OnEmailChanged(newValue))
                        },
                        label = "Email",
                        placeholder = "Email de la persona...",
                        supportingText = state.value.emailError,
                        errorStatus = state.value.emailError?.isNotEmpty() ?: true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Email
                        ),
                        icon = R.drawable.ic_mail_24px
                    )

                    TimePickerComponent(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        label = "Hora de entrada",
                        painterResource = painterResource(id = R.drawable.ic_schedule_24px),
                        value = person?.startsAt ?: "",
                        onTextSelected = { newValue ->
                            viewModel.onEvent(PersonEvent.OnStartsAtChanged(newValue))
                        },
                        supportingText = state.value.startsAtError,
                        errorStatus = state.value.startsAtError?.isNotEmpty() ?: true,
                    )

                    TimePickerComponent(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        label = "Hora de salida",
                        painterResource = painterResource(id = R.drawable.ic_schedule_24px),
                        value = person?.finishesAt ?: "",
                        onTextSelected = { newValue ->
                            viewModel.onEvent(PersonEvent.OnFinishesAtChanged(newValue))
                        },
                        supportingText = state.value.finishesAtError,
                        errorStatus = state.value.finishesAtError?.isNotEmpty() ?: true,
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 10.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CustomText(text = "Persona activa: ")

                        Checkbox(
                            checked = person?.active ?: true,
                            onCheckedChange = { newValue ->
                                viewModel.onEvent(PersonEvent.OnActiveChanged(newValue))
                            }
                        )
                    }
                }
            }
        }
    }
}
