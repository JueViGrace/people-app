package com.jvg.peopleapp.person.presentation.details.screen

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jvg.peopleapp.R
import com.jvg.peopleapp.core.presentation.ui.components.CustomText
import com.jvg.peopleapp.core.presentation.ui.components.TimePickerComponent
import com.jvg.peopleapp.person.domain.model.Person
import com.jvg.peopleapp.person.domain.rules.Validator
import com.jvg.peopleapp.person.presentation.details.components.TextFieldComponent
import com.jvg.peopleapp.person.presentation.details.viewmodel.PersonViewModel
import com.jvg.peopleapp.person.presentation.events.PeopleActions
import org.mongodb.kbson.ObjectId

data class PersonDetailsScreen(val person: Person? = null) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<PersonViewModel>()

        val topBarTitle = if (
            person?.name?.isNotEmpty() == true && person.lastname.isNotEmpty()
        ) {
            "${person.name} ${person.lastname}"
        } else {
            "Cree una nueva persona"
        }

        var currentName by remember {
            mutableStateOf(person?.name ?: "")
        }
        var currentLastname by remember {
            mutableStateOf(person?.lastname ?: "")
        }
        var currentCode by remember {
            mutableStateOf(person?.code ?: "")
        }
        var currentPhone by remember {
            mutableStateOf(person?.phone ?: "")
        }
        var currentEmail by remember {
            mutableStateOf(person?.email ?: "")
        }
        var currentStartsAt by remember {
            mutableStateOf(person?.startsAt ?: "")
        }
        var currentFinishesAt by remember {
            mutableStateOf(person?.finishesAt ?: "")
        }
        var currentActive by remember {
            mutableStateOf(person?.active ?: true)
        }

        // TODO: Move to viewmodel
        val newPerson =
            Person(
                id = person?.id ?: ObjectId(),
                name = currentName,
                lastname = currentLastname,
                code = currentCode,
                phone = currentPhone,
                email = currentEmail,
                startsAt = currentStartsAt,
                finishesAt = currentFinishesAt,
                active = currentActive
            )

        val result = Validator.validatePerson(
            newPerson
        )

        val errors = listOfNotNull(
            result.nameError,
            result.lastnameError,
            result.codeError,
            result.phoneError,
            result.emailError,
            result.startsAtError,
            result.finishesAtError
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { CustomText(text = topBarTitle) },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back Arrow")
                        }
                    }
                )
            },
            floatingActionButton = {
                if (errors.isEmpty()) {
                    FloatingActionButton(
                        onClick = {
                            if (person != null) {
                                viewModel.setAction(
                                    action = PeopleActions.Update(
                                        person = newPerson
                                    )
                                )
                            } else {
                                viewModel.setAction(
                                    action = PeopleActions.Add(
                                        person = newPerson
                                    )
                                )
                            }
                            navigator.pop()
                        },
                    ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "Check icon")
                    }
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
                        value = currentName,
                        newValue = {
                            currentName = it
                        },
                        label = "Nombre",
                        placeholder = "Nombre de la persona...",
                        supportingText = result.nameError,
                        errorStatus = result.nameError?.isNotEmpty() == true,
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
                        value = currentLastname,
                        newValue = {
                            currentLastname = it
                        },
                        label = "Apellido",
                        placeholder = "Apellido de la persona...",
                        supportingText = result.lastnameError,
                        errorStatus = result.lastnameError?.isNotEmpty() == true,
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
                        value = currentCode,
                        newValue = {
                            currentCode = it
                        },
                        label = "Cédula",
                        placeholder = "Cédula de la persona...",
                        supportingText = result.codeError,
                        errorStatus = result.codeError?.isNotEmpty() == true,
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
                        value = currentPhone,
                        newValue = {
                            currentPhone = it
                        },
                        label = "Teléfono",
                        placeholder = "Teléfono de la persona...",
                        supportingText = result.phoneError,
                        errorStatus = result.phoneError?.isNotEmpty() == true,
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
                        value = currentEmail,
                        newValue = {
                            currentEmail = it
                        },
                        label = "Email",
                        placeholder = "Email de la persona...",
                        supportingText = result.emailError,
                        errorStatus = result.emailError?.isNotEmpty() == true,
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
                        value = currentStartsAt,
                        onTextSelected = {
                            currentStartsAt = it
                        },
                        supportingText = result.startsAtError,
                        errorStatus = currentStartsAt.isEmpty()
                    )

                    TimePickerComponent(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        label = "Hora de salida",
                        painterResource = painterResource(id = R.drawable.ic_schedule_24px),
                        value = currentFinishesAt,
                        onTextSelected = {
                            currentFinishesAt = it
                        },
                        supportingText = result.finishesAtError,
                        errorStatus = currentFinishesAt.isEmpty()
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
                            checked = currentActive,
                            onCheckedChange = {
                                currentActive = it
                            }
                        )
                    }
                }
            }
        }
    }
}
