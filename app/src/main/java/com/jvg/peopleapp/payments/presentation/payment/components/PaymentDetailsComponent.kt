package com.jvg.peopleapp.payments.presentation.payment.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import com.jvg.peopleapp.R
import com.jvg.peopleapp.core.presentation.ui.components.AppBar
import com.jvg.peopleapp.core.presentation.ui.components.CustomClickableCard
import com.jvg.peopleapp.core.presentation.ui.components.FABComponent
import com.jvg.peopleapp.core.presentation.ui.components.RowComponent
import com.jvg.peopleapp.payments.domain.model.Payment
import com.jvg.peopleapp.payments.domain.model.PaymentMethods
import org.mongodb.kbson.ObjectId

@Composable
fun PaymentDetailsComponent(
    payment: Payment,
    popBack: () -> Unit,
    onAdd: (ObjectId) -> Unit,
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                CustomClickableCard {
                    if (payment.paymentMethod == PaymentMethods.Transfer.method){
                        RowComponent(
                            modifier = Modifier.fillMaxWidth(),
                            field = "Banco",
                            value = payment.bank ?: "",
                            icon = painterResource(R.drawable.ic_account_balance_24px)
                        )
                        RowComponent(
                            modifier = Modifier.fillMaxWidth(),
                            field = "Referencia",
                            value = payment.reference ?: "",
                            icon = painterResource(R.drawable.ic_checkbook_24px)
                        )
                    }
                }
            }
        }
    }
}