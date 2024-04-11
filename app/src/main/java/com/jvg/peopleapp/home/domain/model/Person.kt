package com.jvg.peopleapp.home.domain.model

import com.jvg.peopleapp.core.common.toStringFormat
import com.jvg.peopleapp.home.data.local.model.PersonCollection
import org.mongodb.kbson.ObjectId
import java.util.Date

data class Person(
    val id: ObjectId = ObjectId(),
    val name: String = "",
    val lastname: String = "",
    val code: String = "",
    val phone: String = "",
    val email: String = "",
    val createdAt: String = Date().toStringFormat(1),
    val startsAt: String = "",
    val finishesAt: String = "",
    val active: Boolean = true,
) {
    fun toDatabase(): PersonCollection = PersonCollection().apply {
        _id = this@Person.id
        name = this@Person.name
        lastname = this@Person.lastname
        code = this@Person.code
        phone = this@Person.phone
        email = this@Person.email
        createdAt = this@Person.createdAt
        startsAt = this@Person.startsAt
        finishesAt = this@Person.finishesAt
        active = this@Person.active
    }
}
