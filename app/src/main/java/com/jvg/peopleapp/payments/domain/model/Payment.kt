package com.jvg.peopleapp.payments.domain.model

import com.jvg.peopleapp.payments.data.local.model.PaymentCollection
import com.jvg.peopleapp.people.domain.model.Person
import org.mongodb.kbson.ObjectId

data class Payment(
    var id: ObjectId = ObjectId(),
    val paymentMethod: String = "",
    val reference: String? = "",
    val bank: String? = null,
    val holderCode: String? = "",
    val amount: Double = 0.0,
    val madeAt: String = "",
    val note: String = "",
    val person: Person? = null
) {
    fun toDatabase(): PaymentCollection = PaymentCollection().apply {
        _id = this@Payment.id
        paymentMethod = this@Payment.paymentMethod
        reference = this@Payment.reference
        bank = this@Payment.bank
        holderCode = this@Payment.holderCode
        amount = this@Payment.amount
        madeAt = this@Payment.madeAt
        note = this@Payment.note
        personCollection = this@Payment.person?.toDatabase()
    }
}
