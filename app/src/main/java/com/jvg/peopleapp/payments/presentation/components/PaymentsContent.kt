package com.jvg.peopleapp.payments.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jvg.peopleapp.core.presentation.ui.components.ErrorScreen
import com.jvg.peopleapp.core.presentation.ui.components.LoadingScreen
import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.payments.domain.model.Payment
import org.mongodb.kbson.ObjectId

@Composable
fun PaymentsContent(
    modifier: Modifier = Modifier,
    payments: RequestState<List<Payment>>,
    onSelect: ((ObjectId) -> Unit)? = null,
) {
    payments.DisplayResult(
        onError = { message ->
            ErrorScreen(message)
        },
        onLoading = {
            LoadingScreen()
        },
        onSuccess = { list ->
            if (list.isNotEmpty()) {
                LazyColumn(
                    modifier = modifier.padding(horizontal = 5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(3.dp, Alignment.Top)
                ) {
                    items(
                        items = list,
                        key = { person -> person.id.toHexString() }
                    ) { payment ->
                        ListPaymentComponent(
                            payment = payment,
                            onSelect = { id ->
                                onSelect?.invoke(id)
                            }
                        )
                    }
                }
            } else {
                ErrorScreen()
            }
        }
    )
}
