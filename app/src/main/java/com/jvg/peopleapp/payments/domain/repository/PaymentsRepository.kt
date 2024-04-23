package com.jvg.peopleapp.payments.domain.repository

import com.jvg.peopleapp.core.common.Constants.DB_ERROR_MESSAGE
import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.payments.data.local.sources.PaymentsDataSource
import com.jvg.peopleapp.payments.domain.model.Payment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PaymentsRepository(
    private val paymentsDataSource: PaymentsDataSource
) {

    fun getAllPayments(): Flow<RequestState<List<Payment>>> = flow {
        emit(RequestState.Loading)

        try {
            paymentsDataSource.getAllPayments()
                .catch { e ->
                    emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
                }
                .collect { list ->
                    if (list.isNotEmpty()) {
                        emit(RequestState.Success(data = list))
                    } else {
                        emit(RequestState.Error(message = "Lista vacía"))
                    }
                }
        } catch (e: Exception) {
            emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
        }
    }.flowOn(Dispatchers.IO)

    fun getPaymentById(id: String): Flow<RequestState<Payment>> = flow {
        emit(RequestState.Loading)

        try {
            paymentsDataSource.getPaymentById(id)
                .catch { e ->
                    emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
                }
                .collect { list ->
                    if (list.id?.isNotEmpty() == true) {
                        emit(RequestState.Success(data = list))
                    } else {
                        emit(RequestState.Error(message = "Este pago no existe"))
                    }
                }
        } catch (e: Exception) {
            emit(RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun addPayment(payment: Payment) = paymentsDataSource.addPayment(payment)

    suspend fun deletePayment(id: String) = paymentsDataSource.deletePayment(id)
}
