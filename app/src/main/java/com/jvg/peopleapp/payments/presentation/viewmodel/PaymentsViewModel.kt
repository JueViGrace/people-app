package com.jvg.peopleapp.payments.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.payments.data.local.sources.PaymentsDataSource
import com.jvg.peopleapp.payments.presentation.state.PaymentsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PaymentsViewModel(
    private val paymentsDataSource: PaymentsDataSource,
) : ScreenModel {

    private var _state: MutableStateFlow<PaymentsState> = MutableStateFlow(PaymentsState())
    val state: StateFlow<PaymentsState> = _state.asStateFlow()

    init {
        _state.update { paymentsState ->
            paymentsState.copy(
                payments = RequestState.Loading
            )
        }

        screenModelScope.launch {
            paymentsDataSource.getAllPayments().collect { value ->
                _state.update { paymentsState ->
                    paymentsState.copy(
                        payments = value
                    )
                }
            }
        }
    }
}
