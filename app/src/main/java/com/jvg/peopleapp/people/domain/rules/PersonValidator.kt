package com.jvg.peopleapp.people.domain.rules

import android.util.Patterns
import com.jvg.peopleapp.core.common.Constants.PHONE_LENGTH
import com.jvg.peopleapp.people.domain.model.Person

object PersonValidator {
    fun validatePerson(person: Person): PersonValidationResult {
        var result = PersonValidationResult()

        if (person.name.isBlank()) {
            result = result.copy(nameError = "Nombre no puede estar vacío")
        }

        if (person.lastname.isBlank()) {
            result = result.copy(lastnameError = "Apellido no puede estar vacío")
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(person.email).matches()) {
            result = result.copy(emailError = "Email debe ser un email válido")
        }

        if (person.code.map{ it.isDigit() }.contains(false)) {
            result = result.copy(codeError = "Cédula solo debe contener números")
        }

        if (person.phone.length < PHONE_LENGTH) {
            result = result.copy(phoneError = "Teléfono debe tener 10 o más caracteres")
        }

        if (person.startsAt.isBlank()) {
            result = result.copy(startsAtError = "Hora de entrada no puede estar vacío")
        }

        if (person.finishesAt.isBlank()) {
            result = result.copy(finishesAtError = "Hora de salida no puede estar vacío")
        }

        return result
    }

    data class PersonValidationResult(
        val nameError: String? = null,
        val lastnameError: String? = null,
        val emailError: String? = null,
        val codeError: String? = null,
        val phoneError: String? = null,
        val startsAtError: String? = null,
        val finishesAtError: String? = null,
    )
}
