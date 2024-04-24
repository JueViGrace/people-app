package com.jvg.peopleapp.people.presentation.person.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jvg.peopleapp.R
import com.jvg.peopleapp.core.presentation.ui.components.AppBar
import com.jvg.peopleapp.core.presentation.ui.components.CustomClickableCard
import com.jvg.peopleapp.core.presentation.ui.components.CustomText
import com.jvg.peopleapp.core.presentation.ui.components.DeleteAlertDialog
import com.jvg.peopleapp.core.presentation.ui.components.FABComponent
import com.jvg.peopleapp.core.presentation.ui.components.RowComponent
import com.jvg.peopleapp.core.presentation.ui.components.TextFieldComponent
import com.jvg.peopleapp.payments.domain.model.Payment
import com.jvg.peopleapp.people.domain.model.Person

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PersonDetailsComponent(
    person: Person,
    popBack: () -> Unit,
    onAdd: (String) -> Unit,
    onPayment: (String) -> Unit,
    onDelete: () -> Unit
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    if (showDialog) {
        DeleteAlertDialog(
            questionText = "¿Seguro que quieres borrar a ${person.name}?",
            onShowChange = {
                showDialog = it
            },
            onDismiss = {
            },
            onConfirm = {
                onDelete()
            }
        )
    }

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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.Top)
        ) {
            item {
                CustomClickableCard(
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CustomText(
                                text = "Nombre y apellido:",
                                fontWeight = FontWeight.Light,
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            )

                            CustomText(
                                text = person.name,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            )

                            CustomText(
                                text = person.lastname,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
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
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
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
                        value = person.createdAt,
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
            }

            item {
                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface)
            }

            item {
                CustomText(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Pagos",
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    textAlign = TextAlign.Center
                )
            }

            item {
                if (person.payments.isNotEmpty()) {
                    val state = rememberLazyListState()

                    LazyRow(
                        state = state,
                        modifier = Modifier
                            .padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                        flingBehavior = rememberSnapFlingBehavior(lazyListState = state)
                    ) {
                        items(
                            items = person.payments,
                            key = { item: Payment -> item.id }
                        ) { payment ->
                            CustomClickableCard(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    onPayment(payment.id)
                                }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(5.dp),
                                    verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    RowComponent(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 10.dp),
                                        horizontalArrangement = Arrangement.spacedBy(
                                            5.dp,
                                            Alignment.CenterHorizontally
                                        ),
                                        icon = painterResource(id = R.drawable.ic_fingerprint_24px),
                                        field = "Id",
                                        value = payment.id
                                    )

                                    RowComponent(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 10.dp),
                                        horizontalArrangement = Arrangement.spacedBy(
                                            5.dp,
                                            Alignment.CenterHorizontally
                                        ),
                                        field = "Fecha de creación",
                                        value = payment.createdAt,
                                        icon = painterResource(id = R.drawable.ic_calendar_month_24px)
                                    )

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RowComponent(
                                            icon = painterResource(id = R.drawable.ic_receipt_24px),
                                            field = "Método de pago",
                                            value = payment.paymentMethod
                                        )

                                        RowComponent(
                                            icon = painterResource(id = R.drawable.ic_attach_money_24px),
                                            field = "Cantidad",
                                            value = payment.amount.toString()
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CustomText(
                            text = "${person.name} no posee ningún pago",
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        )
                    }
                }
            }

            item {
                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface)
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ElevatedButton(
                        onClick = {
                            showDialog = true
                        },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        ),
                        enabled = !person.active
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally)
                        ) {
                            CustomText(text = "Borrar Persona", fontSize = MaterialTheme.typography.bodyLarge.fontSize)
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete button"
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier)
            }
        }
    }
}
