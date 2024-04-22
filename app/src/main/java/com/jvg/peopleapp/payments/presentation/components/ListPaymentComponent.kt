package com.jvg.peopleapp.payments.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jvg.peopleapp.R
import com.jvg.peopleapp.core.common.timestampToDate
import com.jvg.peopleapp.core.presentation.ui.components.CustomClickableCard
import com.jvg.peopleapp.core.presentation.ui.components.RowComponent
import com.jvg.peopleapp.payments.domain.model.Payment
import com.jvg.peopleapp.payments.domain.model.PaymentMethods
import org.mongodb.kbson.ObjectId

@Composable
fun ListPaymentComponent(
    payment: Payment,
    onSelect: (ObjectId) -> Unit
) {
    CustomClickableCard(
        onClick = {
            onSelect(payment.id)
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
                value = payment.id.toHexString()
            )

            RowComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
                field = "Fecha de creación",
                value = payment.id.timestamp.timestampToDate(1),
                icon = painterResource(id = R.drawable.ic_calendar_month_24px)
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.onSurface)

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
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
                    if (payment.paymentMethod == PaymentMethods.Transfer.method) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RowComponent(
                                icon = painterResource(id = R.drawable.ic_checkbook_24px),
                                field = "Referencia",
                                value = payment.reference ?: ""
                            )

                            RowComponent(
                                icon = painterResource(id = R.drawable.ic_account_balance_24px),
                                field = "Banco",
                                value = payment.bank ?: ""
                            )
                        }
                    }
                }
            }

            if (payment.person != null) {
                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RowComponent(
                        icon = painterResource(id = R.drawable.ic_person_24px),
                        field = "Pago de",
                        value = "${payment.person.name} ${payment.person.lastname}"
                    )

                    RowComponent(
                        icon = painterResource(id = R.drawable.ic_id_card_24px),
                        field = "Cédula",
                        value = payment.person.code
                    )
                }
            }
        }
    }
}
