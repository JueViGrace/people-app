package com.jvg.peopleapp.people.presentation.person.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jvg.peopleapp.R
import com.jvg.peopleapp.core.common.timestampToDate
import com.jvg.peopleapp.core.presentation.ui.components.AppBar
import com.jvg.peopleapp.core.presentation.ui.components.CustomClickableCard
import com.jvg.peopleapp.core.presentation.ui.components.CustomText
import com.jvg.peopleapp.core.presentation.ui.components.ErrorScreen
import com.jvg.peopleapp.core.presentation.ui.components.FABComponent
import com.jvg.peopleapp.core.presentation.ui.components.LoadingScreen
import com.jvg.peopleapp.home.routes.DetailScreens
import com.jvg.peopleapp.people.presentation.person.components.TextFieldComponent
import com.jvg.peopleapp.people.presentation.person.viewmodel.PersonViewModel
import org.koin.core.parameter.parametersOf
import org.mongodb.kbson.ObjectId

data class PersonDetailsScreen(val id: ObjectId) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<PersonViewModel>(parameters = { parametersOf(id) })
        val state = viewModel.state.collectAsState()

        state.value.person.DisplayResult(
            onLoading = {
                LoadingScreen()
            },
            onError = {
                ErrorScreen()
            },
            onSuccess = { person ->
                Scaffold(
                    topBar = {
                        AppBar(
                            title = "${person.name} ${person.lastname}",
                            icon = rememberVectorPainter(Icons.AutoMirrored.Default.ArrowBack),
                            onClick = {
                                navigator.pop()
                            }
                        )
                    },
                    floatingActionButton = {
                        FABComponent(
                            onAdd = {
                                navigator.push(DetailScreens.CreatePerson(person.id).screen)
                            },
                            icon = painterResource(id = R.drawable.ic_edit_24px)
                        )
                    }
                ) { paddingValues ->
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .padding(
                                top = paddingValues.calculateTopPadding(),
                                bottom = paddingValues.calculateBottomPadding()
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    CustomText(
                                        text = "Nombre y apellido:",
                                        fontWeight = FontWeight.Light,
                                        fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                    )

                                    CustomText(
                                        text = person.name,
                                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                    )

                                    CustomText(
                                        text = person.lastname,
                                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                    )

                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        CustomText(
                                            text = if (person.active) "Persona Activa:" else "Perosna inactiva:",
                                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                                        )
                                        Checkbox(
                                            checked = person.active,
                                            onCheckedChange = { _ ->
                                            },
                                            enabled = false
                                        )
                                    }
                                }

                                Icon(
                                    imageVector = Icons.Outlined.AccountCircle,
                                    modifier = Modifier.size(150.dp),
                                    contentDescription = "Person"
                                )
                            }

                            HorizontalDivider(color = MaterialTheme.colorScheme.onSurface)

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp),
                                verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                TextFieldComponent(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = person.code,
                                    newValue = { _ -> },
                                    icon = R.drawable.ic_id_card_24px,
                                    readOnly = true,
                                    label = "Cédula"
                                )

                                TextFieldComponent(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = person.phone,
                                    newValue = { _ -> },
                                    icon = R.drawable.ic_call_24px,
                                    readOnly = true,
                                    label = "Teléfono"
                                )

                                TextFieldComponent(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = person.email,
                                    newValue = { _ -> },
                                    icon = R.drawable.ic_mail_24px,
                                    readOnly = true,
                                    label = "Email"
                                )

                                TextFieldComponent(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = person.id.timestamp.timestampToDate(1),
                                    newValue = { _ -> },
                                    icon = R.drawable.ic_calendar_month_24px,
                                    readOnly = true,
                                    label = "Fecha de creación"
                                )

                                TextFieldComponent(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = person.startsAt,
                                    newValue = { _ -> },
                                    icon = R.drawable.ic_schedule_24px,
                                    readOnly = true,
                                    label = "Hora de entrada"
                                )

                                TextFieldComponent(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = person.finishesAt,
                                    newValue = { _ -> },
                                    icon = R.drawable.ic_schedule_24px,
                                    readOnly = true,
                                    label = "Hora de salida"
                                )
                            }

                            if (person.payments.isNotEmpty()) {
                                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface)

                                person.payments.forEach { payment ->
                                    CustomClickableCard(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalArrangement = Arrangement.spacedBy(
                                                5.dp,
                                                Alignment.CenterVertically
                                            ),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            CustomText(text = payment.reference)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}
