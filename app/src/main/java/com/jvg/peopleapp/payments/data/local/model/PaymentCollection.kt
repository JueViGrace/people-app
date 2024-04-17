package com.jvg.peopleapp.payments.data.local.model

import com.jvg.peopleapp.payments.domain.model.Payment
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class PaymentCollection : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var reference: String = ""
    var madeAt: String = ""
    var bank: String = ""
    var amount: Double = 0.0
    var note: String = ""
    var dueDate: String = ""

    fun toDomain(): Payment = Payment(
        id = _id,
        reference = reference,
        madeAt = madeAt,
        bank = bank,
        amount = amount,
        note = note,
        dueDate = dueDate
    )
}
