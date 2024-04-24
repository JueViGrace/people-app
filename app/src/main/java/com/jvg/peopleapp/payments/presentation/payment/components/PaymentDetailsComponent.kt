package com.jvg.peopleapp.payments.presentation.payment.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jvg.peopleapp.R
import com.jvg.peopleapp.core.common.toCustomDateFormat
import com.jvg.peopleapp.core.presentation.ui.components.AppBar
import com.jvg.peopleapp.core.presentation.ui.components.CustomClickableCard
import com.jvg.peopleapp.core.presentation.ui.components.CustomText
import com.jvg.peopleapp.core.presentation.ui.components.FABComponent
import com.jvg.peopleapp.core.presentation.ui.components.RowComponent
import com.jvg.peopleapp.core.presentation.ui.components.TextFieldComponent
import com.jvg.peopleapp.payments.domain.model.Payment
import com.jvg.peopleapp.payments.domain.model.PaymentMethods

@Composable
fun PaymentDetailsComponent(
    payment: Payment,
    popBack: () -> Unit,
    onAdd: (String) -> Unit,
    onPerson: (String) -> Unit
) {
    Scaffold(
        topBar = {
            AppBar(
                title = "Pago de: ${payment.person?.name} ${payment.person?.lastname}",
                icon = rememberVectorPainter(Icons.AutoMirrored.Default.ArrowBack),
                onClick = popBack
            )
        },
        floatingActionButton = {
            FABComponent(
                onAdd = {
                    onAdd(payment.id)
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
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CustomText(
                                text = "Método de pago:",
                                fontSize = MaterialTheme.typography.titleMedium.fontSize
                            )
                            CustomText(
                                text = payment.paymentMethod,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            CustomText(
                                text = "Creado el:",
                                fontSize = MaterialTheme.typography.titleMedium.fontSize
                            )
                            CustomText(
                                text = payment.createdAt.toCustomDateFormat(),
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize
                            )
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.ic_payments_24px),
                            contentDescription = "Payment",
                            modifier = Modifier.size(100.dp)
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
                    if (payment.paymentMethod == PaymentMethods.Transfer.method) {
                        TextFieldComponent(
                            modifier = Modifier.fillMaxWidth(),
                            value = payment.bank,
                            newValue = { _ -> },
                            icon = R.drawable.ic_account_balance_24px,
                            readOnly = true,
                            label = "Banco"
                        )

                        TextFieldComponent(
                            modifier = Modifier.fillMaxWidth(),
                            value = payment.reference,
                            newValue = { _ -> },
                            icon = R.drawable.ic_checkbook_24px,
                            readOnly = true,
                            label = "Banco"
                        )

                        TextFieldComponent(
                            modifier = Modifier.fillMaxWidth(),
                            value = payment.holderCode,
                            newValue = { _ -> },
                            icon = R.drawable.ic_id_card_24px,
                            readOnly = true,
                            label = "Titular"
                        )
                    }

                    TextFieldComponent(
                        modifier = Modifier.fillMaxWidth(),
                        value = payment.amount.toString(),
                        newValue = { _ -> },
                        icon = R.drawable.ic_attach_money_24px,
                        readOnly = true,
                        label = "Cantidad"
                    )

                    TextFieldComponent(
                        modifier = Modifier.fillMaxWidth(),
                        value = payment.madeAt,
                        newValue = { _ -> },
                        icon = R.drawable.ic_calendar_month_24px,
                        readOnly = true,
                        label = "Fecha de realización"
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
                    text = "Pertenece a",
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    textAlign = TextAlign.Center
                )
            }

            item {
                payment.person?.let { person ->
                    CustomClickableCard(
                        modifier = Modifier
                            .padding(horizontal = 5.dp),
                        onClick = {
                            if (person.deletedAt == null) {
                                onPerson(person.id)
                            }
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(5.dp),
                            verticalArrangement = Arrangement.spacedBy(
                                5.dp,
                                Alignment.CenterVertically
                            ),
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
                                value = person.id
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
                                value = person.createdAt,
                                icon = painterResource(id = R.drawable.ic_calendar_month_24px)
                            )

                            HorizontalDivider(color = MaterialTheme.colorScheme.onSurface)

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 5.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                RowComponent(
                                    icon = painterResource(id = R.drawable.ic_person_24px),
                                    field = "Nombre",
                                    value = "${person.name} ${person.lastname}"
                                )

                                RowComponent(
                                    icon = painterResource(id = R.drawable.ic_id_card_24px),
                                    field = "Cédula",
                                    value = person.code
                                )
                            }

                            if (person.deletedAt != null) {
                                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface)
                                Row(
                                    modifier = Modifier.padding(5.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CustomText(
                                        text = "Esta persona ha sido borrada",
                                        color = Color.Black,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f)
                                            .background(
                                                MaterialTheme.colorScheme.error,
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                            .padding(all = 5.dp),
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(70.dp))
            }
        }
    }
}
