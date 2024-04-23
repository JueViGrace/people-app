package com.jvg.peopleapp.payments.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.payments.domain.repository.PaymentsRepository
import com.jvg.peopleapp.payments.presentation.state.PaymentsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PaymentsViewModel(
    private val paymentsRepository: PaymentsRepository,
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
            paymentsRepository.getAllPayments().collect { value ->
                _state.update { paymentsState ->
                    paymentsState.copy(
                        payments = value
                    )
                }
            }
        }
    }
}
