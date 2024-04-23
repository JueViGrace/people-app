package com.jvg.peopleapp.payments.presentation.payment.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jvg.peopleapp.core.presentation.ui.components.RowComponent
import com.jvg.peopleapp.payments.domain.model.PaymentMethods

@Composable
fun PaymentMethodsRadioComponent(
    modifier: Modifier = Modifier,
    paymentMethods: List<PaymentMethods>,
    value: String,
    onValueChanged: (String) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        paymentMethods.forEach { method ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp, Alignment.CenterHorizontally)
            ) {
                RowComponent(
                    icon = method.icon?.let { painterResource(id = it) },
                    value = method.method
                )
                RadioButton(
                    selected = value == method.method,
                    onClick = {
                        onValueChanged(method.method)
                    }
                )
            }
        }
    }
}
