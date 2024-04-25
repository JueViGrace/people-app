package com.jvg.peopleapp

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.paging.PagingData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jvg.peopleapp.core.state.RequestState
import com.jvg.peopleapp.payments.domain.model.Payment
import com.jvg.peopleapp.payments.presentation.components.PaymentsContent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PaymentsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val _state = mutableStateOf<RequestState<Flow<PagingData<Payment>>>>(RequestState.Idle)
    private val state = _state.value

    @Test
    fun addNoState_Assert_PaymentsListDoesNotExist() {
        composeTestRule.setContent {
            PaymentsContent(
                modifier = Modifier.testTag("PaymentsLazyColumn"),
                payments = state
            )
        }
        composeTestRule.onNodeWithTag("PaymentsLazyColumn").assertDoesNotExist()
    }

    @Test
    fun addSuccessState_Assert_CorrectSuccessListIsDisplayed() {
        _state.value = RequestState.Success(data = flowOf(PagingData.from(listOf(Payment(), Payment()))))
        composeTestRule.setContent {
            PaymentsContent(
                payments = state
            )
        }
        composeTestRule.onNodeWithTag("PaymentsLazyColumn").assertExists()
    }
}
