package com.jvg.peopleapp.payments.domain.rules

import com.google.common.truth.Truth.assertThat
import com.jvg.peopleapp.payments.domain.model.Payment
import com.jvg.peopleapp.people.domain.model.Person
import org.junit.Test

class PaymentValidatorTest {

    @Test
    fun `payment method is empty returns error message`() {
        val result = PaymentValidator.validatePayment(
            Payment(
                paymentMethod = ""
            )
        )

        assertThat(result.paymentMethodError).isNotEmpty()
    }

    @Test
    fun `invalid payment method is provided returns error message`() {
        val result = PaymentValidator.validatePayment(
            Payment(
                paymentMethod = "invalid payment method"
            )
        )

        assertThat(result.paymentMethodError).isNotEmpty()
    }

    @Test
    fun `valid payment method is provided returns null`() {
        val result = PaymentValidator.validatePayment(
            Payment(
                paymentMethod = "Efectivo"
            )
        )

        assertThat(result.paymentMethodError).isNull()
    }

    @Test
    fun `cash payment method is provided check reference error is null`() {
        val result = PaymentValidator.validatePayment(
            Payment(
                paymentMethod = "Efectivo",
                reference = "23123"
            )
        )

        assertThat(result.referenceError).isNull()
    }

    @Test
    fun `cash payment method is provided check bank error is null`() {
        val result = PaymentValidator.validatePayment(
            Payment(
                paymentMethod = "Efectivo",
                bank = "bank"
            )
        )

        assertThat(result.bankError).isNull()
    }

    @Test
    fun `cash payment method is provided check holder code error is null`() {
        val result = PaymentValidator.validatePayment(
            Payment(
                paymentMethod = "Efectivo",
                holderCode = "1232131"
            )
        )

        assertThat(result.holderCodeError).isNull()
    }

    @Test
    fun `transfer payment method is provided check when reference blank error is returned`() {
        val result = PaymentValidator.validatePayment(
            Payment(
                paymentMethod = "Transferencia",
                reference = ""
            )
        )

        assertThat(result.referenceError).isNotEmpty()
    }

    @Test
    fun `transfer payment method is provided check when bank blank error is returned`() {
        val result = PaymentValidator.validatePayment(
            Payment(
                paymentMethod = "Transferencia",
                bank = ""
            )
        )

        assertThat(result.bankError).isNotEmpty()
    }

    @Test
    fun `transfer payment method is provided check when invalid bank error is returned`() {
        val result = PaymentValidator.validatePayment(
            Payment(
                paymentMethod = "Transferencia",
                bank = "invalid bank"
            )
        )

        assertThat(result.bankError).isNotEmpty()
    }

    @Test
    fun `transfer payment method is provided check when holder code blank error is returned`() {
        val result = PaymentValidator.validatePayment(
            Payment(
                paymentMethod = "Transferencia",
                holderCode = ""
            )
        )

        assertThat(result.holderCodeError).isNotEmpty()
    }

    @Test
    fun `transfer payment method is provided check when holder code is not digit error is returned`() {
        val result = PaymentValidator.validatePayment(
            Payment(
                paymentMethod = "Transferencia",
                holderCode = "invalid holder code 1231231"
            )
        )

        assertThat(result.holderCodeError).isNotEmpty()
    }

    @Test
    fun `transfer payment method and valid reference is provided check error is null`() {
        val result = PaymentValidator.validatePayment(
            Payment(
                paymentMethod = "Transferencia",
                reference = "1231231"
            )
        )

        assertThat(result.referenceError).isNull()
    }

    @Test
    fun `transfer payment method and valid bank is provided check error is null`() {
        val result = PaymentValidator.validatePayment(
            Payment(
                paymentMethod = "Transferencia",
                bank = "Banesco"
            )
        )

        assertThat(result.bankError).isNull()
    }

    @Test
    fun `transfer payment method and valid holder code is provided check error is null`() {
        val result = PaymentValidator.validatePayment(
            Payment(
                paymentMethod = "Transferencia",
                holderCode = "123412412"
            )
        )

        assertThat(result.holderCodeError).isNull()
    }

    @Test
    fun `blank made at date provided returns error message`() {
        val result = PaymentValidator.validatePayment(
            Payment(
                madeAt = ""
            )
        )

        assertThat(result.madeAtError).isNotEmpty()
    }

    @Test
    fun `below 0 or NaN amount returns error message`() {
        val result = PaymentValidator.validatePayment(
            Payment(
                amount = -10.0
            )
        )

        assertThat(result.amountError).isNotEmpty()
    }

    @Test
    fun `valid amount error returns null`() {
        val result = PaymentValidator.validatePayment(
            Payment(
                amount = 10.0
            )
        )

        assertThat(result.amountError).isNull()
    }

    @Test
    fun `person provided is null returns error message`() {
        val result = PaymentValidator.validatePayment(
            Payment(
                person = null
            )
        )

        assertThat(result.personError).isNotEmpty()
    }

    @Test
    fun `person provided is valid error returns null`() {
        val result = PaymentValidator.validatePayment(
            Payment(
                person = Person()
            )
        )

        assertThat(result.personError).isNull()
    }
}
