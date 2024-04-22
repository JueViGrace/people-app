package com.jvg.peopleapp.people.domain.model

import com.jvg.peopleapp.payments.domain.model.Payment
import com.jvg.peopleapp.people.data.local.model.PersonCollection
import org.mongodb.kbson.ObjectId

data class Person(
    val id: ObjectId = ObjectId(),
    val name: String = "",
    val lastname: String = "",
    val code: String = "",
    val phone: String = "",
    val email: String = "",
    val startsAt: String = "",
    val finishesAt: String = "",
    val active: Boolean = true,
    val payments: List<Payment> = emptyList()
) {
    fun toDatabase(): PersonCollection = PersonCollection().apply {
        _id = this@Person.id
        name = this@Person.name
        lastname = this@Person.lastname
        code = this@Person.code
        phone = this@Person.phone
        email = this@Person.email
        startsAt = this@Person.startsAt
        finishesAt = this@Person.finishesAt
        active = this@Person.active
    }
}
