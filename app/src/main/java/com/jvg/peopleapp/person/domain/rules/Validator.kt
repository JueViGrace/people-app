package com.jvg.peopleapp.person.domain.rules

import android.util.Patterns
import com.jvg.peopleapp.core.common.Constants.CODE_LENGTH
import com.jvg.peopleapp.core.common.Constants.PHONE_LENGTH
import com.jvg.peopleapp.person.domain.model.Person

object Validator {
    fun validatePerson(person: Person): ValidationResult {
        var result = ValidationResult()

        if (person.name.isBlank()) {
            result = result.copy(nameError = "Nombre no puede estar vacío")
        }

        if (person.lastname.isBlank()) {
            result = result.copy(lastnameError = "Apellido no puede estar vacío")
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(person.email).matches()) {
            result = result.copy(emailError = "Email debe ser un email válido")
        }

        if (person.code.isBlank()) {
            result = result.copy(codeError = "Cédula no puede estar vacío")
        } else if (person.code.length > CODE_LENGTH) {
            result = result.copy(codeError = "Cédula debe tener 8 caracteres o menos")
        }

        if (person.phone.length < PHONE_LENGTH) {
            result = result.copy(phoneError = "Teléfono debe tener 10 o más caracteres")
        }

        return result
    }

    data class ValidationResult(
        val nameError: String? = null,
        val lastnameError: String? = null,
        val emailError: String? = null,
        val codeError: String? = null,
        val phoneError: String? = null
    )
}
