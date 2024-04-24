package com.jvg.peopleapp.people.presentation.person.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jvg.peopleapp.R
import com.jvg.peopleapp.core.presentation.ui.components.AppBar
import com.jvg.peopleapp.core.presentation.ui.components.CustomText
import com.jvg.peopleapp.core.presentation.ui.components.FABComponent
import com.jvg.peopleapp.core.presentation.ui.components.TextFieldComponent
import com.jvg.peopleapp.core.presentation.ui.components.TimePickerComponent
import com.jvg.peopleapp.people.presentation.person.events.PersonEvent
import com.jvg.peopleapp.people.presentation.person.viewmodel.PersonViewModel
import org.koin.core.parameter.emptyParametersHolder
import org.koin.core.parameter.parametersOf

data class CreatePersonScreen(val id: String? = null) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<PersonViewModel>(
            parameters = {
                if (id != null) parametersOf(id) else emptyParametersHolder()
            }
        )
        val state by viewModel.state.collectAsState()
        val editPerson = viewModel.newPerson

        val topBarTitle = if (
            editPerson?.name?.isNotEmpty() == true && editPerson.lastname.isNotEmpty()
        ) {
            "${editPerson.name} ${editPerson.lastname}"
        } else {
            "Cree una nueva persona"
        }

        Scaffold(
            topBar = {
                AppBar(
                    title = topBarTitle,
                    icon = rememberVectorPainter(Icons.AutoMirrored.Default.ArrowBack),
                    onClick = {
                        viewModel.onEvent(PersonEvent.DismissPerson)
                        navigator.pop()
                    }
                )
            },
            floatingActionButton = {
                FABComponent(
                    onAdd = {
                        viewModel.onEvent(PersonEvent.SavePerson)

                        if (viewModel.getError()) {
                            viewModel.onEvent(PersonEvent.DismissPerson)
                            navigator.pop()
                        }
                    },
                    icon = painterResource(id = R.drawable.ic_done_24px)
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding()
                    ),
                verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_account_circle_24px),
                        modifier = Modifier.size(120.dp),
                        contentDescription = "Person"
                    )

                    TextFieldComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        value = editPerson?.name ?: "",
                        newValue = { newValue ->
                            viewModel.onEvent(PersonEvent.OnNameChanged(newValue))
                        },
                        label = "Nombre",
                        placeholder = "Nombre de la persona...",
                        supportingText = state.nameError,
                        errorStatus = state.nameError?.isNotEmpty() ?: false,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Words
                        ),
                        icon = R.drawable.ic_person_24px
                    )

                    TextFieldComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        value = editPerson?.lastname ?: "",
                        newValue = { newValue ->
                            viewModel.onEvent(PersonEvent.OnLastNameChanged(newValue))
                        },
                        label = "Apellido",
                        placeholder = "Apellido de la persona...",
                        supportingText = state.lastNameError,
                        errorStatus = state.lastNameError?.isNotEmpty() ?: false,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Words
                        ),
                        icon = R.drawable.ic_person_24px
                    )

                    TextFieldComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        value = editPerson?.code ?: "",
                        newValue = { newValue ->
                            viewModel.onEvent(PersonEvent.OnCodeChanged(newValue))
                        },
                        label = "Cédula",
                        placeholder = "Cédula de la persona...",
                        supportingText = state.codeError,
                        errorStatus = state.codeError?.isNotEmpty() ?: false,
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
                        value = editPerson?.phone ?: "",
                        newValue = { newValue ->
                            viewModel.onEvent(PersonEvent.OnPhoneChanged(newValue))
                        },
                        label = "Teléfono",
                        placeholder = "Teléfono de la persona...",
                        supportingText = state.phoneError,
                        errorStatus = state.phoneError?.isNotEmpty() ?: false,
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
                        value = editPerson?.email ?: "",
                        newValue = { newValue ->
                            viewModel.onEvent(PersonEvent.OnEmailChanged(newValue))
                        },
                        label = "Email",
                        placeholder = "Email de la persona...",
                        supportingText = state.emailError,
                        errorStatus = state.emailError?.isNotEmpty() ?: false,
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
                        value = editPerson?.startsAt ?: "",
                        onTextSelected = { newValue ->
                            viewModel.onEvent(PersonEvent.OnStartsAtChanged(newValue))
                        },
                        supportingText = state.startsAtError,
                        errorStatus = state.startsAtError?.isNotEmpty() ?: false,
                    )

                    TimePickerComponent(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        label = "Hora de salida",
                        painterResource = painterResource(id = R.drawable.ic_schedule_24px),
                        value = editPerson?.finishesAt ?: "",
                        onTextSelected = { newValue ->
                            viewModel.onEvent(PersonEvent.OnFinishesAtChanged(newValue))
                        },
                        supportingText = state.finishesAtError,
                        errorStatus = state.finishesAtError?.isNotEmpty() ?: false,
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
                            checked = editPerson?.active ?: true,
                            onCheckedChange = { newValue ->
                                viewModel.onEvent(PersonEvent.OnActiveChanged(newValue))
                            }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier)
                }
            }
        }
    }
}
