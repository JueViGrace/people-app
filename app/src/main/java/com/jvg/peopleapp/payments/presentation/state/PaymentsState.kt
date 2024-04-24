package com.jvg.peopleapp.payments.presentation.state

import androidx.paging.PagingData
import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.payments.domain.model.Payment
import kotlinx.coroutines.flow.Flow

data class PaymentsState(
    val payments: RequestState<Flow<PagingData<Payment>>> = RequestState.Loading
)
