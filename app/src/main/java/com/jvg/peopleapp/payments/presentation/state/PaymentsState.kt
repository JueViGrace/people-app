package com.jvg.peopleapp.payments.presentation.state

import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.payments.domain.model.Payment

data class PaymentsState(
    val payments: RequestState<List<Payment>> = RequestState.Loading,
)
