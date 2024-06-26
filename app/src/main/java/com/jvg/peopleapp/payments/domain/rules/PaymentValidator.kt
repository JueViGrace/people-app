package com.jvg.peopleapp.payments.domain.rules

import com.jvg.peopleapp.payments.domain.model.Banks
import com.jvg.peopleapp.payments.domain.model.Payment
import com.jvg.peopleapp.payments.domain.model.PaymentMethods

object PaymentValidator {
    fun validatePayment(payment: Payment): PaymentValidationResult {
        var result = PaymentValidationResult()

        if (payment.paymentMethod.isBlank()) {
            result = result.copy(paymentMethodError = "Debe seleccionar un método de pago")
        } else if (
            (
            payment.paymentMethod != PaymentMethods.Transfer.method &&
                payment.paymentMethod != PaymentMethods.Cash.method
            )
        ) {
            result = result.copy(paymentMethodError = "Método de pago inválido")
        }

        if (payment.paymentMethod == PaymentMethods.Transfer.method) {
            if (payment.reference.isBlank()) {
                result = result.copy(referenceError = "La referencia no puede estar vacía")
            }

            if (payment.bank.isBlank()) {
                result = result.copy(bankError = "Debe seleccionar un banco")
            } else if (
                !Banks::class.sealedSubclasses.map { kClass ->
                    kClass.objectInstance?.name
                }.contains(payment.bank)
            ) {
                result = result.copy(bankError = "Banco inválido")
            }

            if (payment.holderCode.isBlank()) {
                result = result.copy(holderCodeError = "Tipo de documento no puede estar vacía")
            } else if (payment.holderCode.map { it.isDigit() }.contains(false)) {
                result = result.copy(holderCodeError = "Documento debe contener sólo números")
            }
        }

        if (payment.madeAt.isBlank()) {
            result = result.copy(madeAtError = "Debe elegir la fecha del pago")
        }

        if (payment.amount.isNaN() || payment.amount <= 0.0) {
            result = result.copy(amountError = "La cantidad recibida debe ser un número mayor a 0")
        }

        if (payment.person == null) {
            result = result.copy(personError = "Debe seleccionar a una persona")
        }

        return result
    }

    data class PaymentValidationResult(
        val paymentMethodError: String? = null,
        val referenceError: String? = null,
        val bankError: String? = null,
        val holderCodeError: String? = null,
        val madeAtError: String? = null,
        val amountError: String? = null,
        val personError: String? = null,
    )
}
