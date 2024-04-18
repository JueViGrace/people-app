package com.jvg.peopleapp.people.presentation.person.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jvg.peopleapp.R
import com.jvg.peopleapp.core.common.timestampToDate
import com.jvg.peopleapp.core.presentation.ui.components.AppBar
import com.jvg.peopleapp.core.presentation.ui.components.CustomClickableCard
import com.jvg.peopleapp.core.presentation.ui.components.CustomText
import com.jvg.peopleapp.core.presentation.ui.components.FABComponent
import com.jvg.peopleapp.core.presentation.ui.components.TextFieldComponent
import com.jvg.peopleapp.people.domain.model.Person
import org.mongodb.kbson.ObjectId

@Composable
fun PersonDetailsComponent(
    person: Person,
    popBack: () -> Unit,
    onAdd: (ObjectId) -> Unit
) {
    Scaffold(
        topBar = {
            AppBar(
                title = "${person.name} ${person.lastname}",
                icon = rememberVectorPainter(Icons.AutoMirrored.Default.ArrowBack),
                onClick = popBack
            )
        },
        floatingActionButton = {
            FABComponent(
                onAdd = {
                    onAdd(person.id)
                },
                icon = painterResource(id = R.drawable.ic_edit_24px)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
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