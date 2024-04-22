package com.jvg.peopleapp.payments.data.local.sources

import com.jvg.peopleapp.SelfManagerDB
import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.payments.domain.model.Payment
import kotlinx.coroutines.flow.Flow

class PaymentsDataSourceImpl(
    db: SelfManagerDB
) : PaymentsDataSource {
    override fun getAllPayments(): Flow<RequestState<List<Payment>>> {
        TODO("Not yet implemented")
    }

    override fun getPaymentById(id: String): Flow<RequestState<Payment>> {
        TODO("Not yet implemented")
    }

    override fun getPaymentsByPerson(id: String): Flow<RequestState<List<Payment>>> {
        TODO("Not yet implemented")
    }

    override fun getPaymentsWithOptions(
        query: String,
        vararg params: Any?
    ): Flow<RequestState<List<Payment>>> {
        TODO("Not yet implemented")
    }

    override suspend fun addPayment(payment: Payment) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePayment(id: String) {
        TODO("Not yet implemented")
    }
}
