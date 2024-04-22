package com.jvg.peopleapp.payments.data.local.sources

import android.util.Log
import com.jvg.peopleapp.core.common.Constants.DB_ERROR_MESSAGE
import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.payments.data.local.model.PaymentCollection
import com.jvg.peopleapp.payments.domain.model.Payment
import com.jvg.peopleapp.people.data.local.model.PersonCollection
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId

class PaymentsDataSourceImpl(
    private val realm: Realm,
) : PaymentsDataSource {
    override fun getAllPayments(): Flow<RequestState<List<Payment>>> =
        realm.query<PaymentCollection>()
            .asFlow()
            .catch { e ->
                RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE)
            }
            .map { value ->
                RequestState.Success(
                    data = value.list.map { it.toDomain() }
                )
            }
            .flowOn(Dispatchers.IO)

    override fun getPaymentById(id: ObjectId): Flow<RequestState<Payment>> =
        realm.query<PaymentCollection>(query = "_id == $0", id)
            .find()
            .asFlow()
            .catch { e ->
                RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE)
            }
            .map { value ->
                RequestState.Success(value.list.first().toDomain())
            }
            .flowOn(Dispatchers.IO)

    override fun getPaymentsByPerson(id: ObjectId): Flow<RequestState<List<Payment>>> =
        realm.query<PaymentCollection>(query = "personCollection._id == $0", id)
            .find()
            .asFlow()
            .catch { e ->
                RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE)
            }
            .map { value ->
                RequestState.Success(value.list.map { it.toDomain() })
            }
            .flowOn(Dispatchers.IO)

    override fun getPaymentsWithOptions(
        query: String,
        vararg params: Any?
    ): Flow<RequestState<List<Payment>>> =
        realm.query<PaymentCollection>(query, params)
            .asFlow()
            .catch { e ->
                RequestState.Error(message = e.message ?: DB_ERROR_MESSAGE)
            }
            .map { value ->
                RequestState.Success(
                    data = value.list.map { it.toDomain() }
                )
            }
            .flowOn(Dispatchers.IO)

    override suspend fun addPayment(payment: Payment) {
        realm.write {
            val queriedPerson = realm.query<PersonCollection>(query = "_id == $0", payment.person?.id)
                .find()
                .first()

            findLatest(queriedPerson)?.paymentCollection?.add(
                PaymentCollection().apply {
                    _id = payment.id
                    paymentMethod = payment.paymentMethod
                    reference = payment.reference
                    bank = payment.bank
                    holderCode = payment.holderCode
                    amount = payment.amount
                    madeAt = payment.madeAt
                    note = payment.note
                }
            )

            copyToRealm(
                payment.toDatabase(),
                updatePolicy = UpdatePolicy.ALL
            )
        }
    }

    override suspend fun deletePayment(id: ObjectId) {
        realm.write {
            try {
                val queriedPayment = query<PaymentCollection>(query = "_id == $0", id)
                    .first()
                    .find()
                queriedPayment?.let { paymentCollection ->
                    findLatest(paymentCollection)?.let { currentPayment ->
                        delete(currentPayment)
                    }
                }
            } catch (e: Exception) {
                Log.e("Delete payment", "deletePayment: $e")
            }
        }
    }
}
