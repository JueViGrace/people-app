package com.jvg.peopleapp.payments.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.jvg.peopleapp.core.presentation.ui.components.ErrorScreen
import com.jvg.peopleapp.core.presentation.ui.components.LoadingScreen
import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.payments.domain.model.Payment
import kotlinx.coroutines.flow.Flow

@Composable
fun PaymentsContent(
    modifier: Modifier = Modifier,
    payments: RequestState<Flow<PagingData<Payment>>>,
    onSelect: ((String) -> Unit)? = null,
) {
    payments.DisplayResult(
        onError = { message ->
            ErrorScreen(message)
        },
        onLoading = {
            LoadingScreen()
        },
        onSuccess = { list ->
            val pages = list.collectAsLazyPagingItems()

            if (pages.itemCount > 0) {
                LazyColumn(
                    modifier = modifier.padding(horizontal = 5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top)
                ) {
                    items(
                        count = pages.itemCount,
                        key = pages.itemKey { item: Payment -> item.id },
                        contentType = pages.itemContentType { "Payments" }
                    ) { index ->

                        val payment = pages[index]

                        if (payment != null) {
                            ListPaymentComponent(
                                payment = payment,
                                onSelect = { id ->
                                    onSelect?.invoke(id)
                                }
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(70.dp))
                    }
                }
            } else {
                ErrorScreen()
            }
        }
    )
}
