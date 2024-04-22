package com.jvg.peopleapp.payments.presentation.payment.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jvg.peopleapp.core.presentation.ui.components.ErrorScreen
import com.jvg.peopleapp.core.presentation.ui.components.LoadingScreen
import com.jvg.peopleapp.home.routes.DetailScreens
import com.jvg.peopleapp.payments.presentation.payment.components.PaymentDetailsComponent
import com.jvg.peopleapp.payments.presentation.payment.viewmodel.PaymentViewModel
import org.koin.core.parameter.parametersOf

data class PaymentDetailsScreen(val id: String) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<PaymentViewModel>(parameters = { parametersOf(id) })
        val state by viewModel.state.collectAsState()

        state.payment.DisplayResult(
            onError = { message ->
                ErrorScreen(message)
            },
            onLoading = { LoadingScreen() },
            onSuccess = { payment ->
                PaymentDetailsComponent(
                    payment = payment,
                    onAdd = { id ->
                        navigator.push(DetailScreens.CreatePayment(id).screen)
                    },
                    popBack = {
                        navigator.pop()
                    }
                )
            }
        )
    }
}
