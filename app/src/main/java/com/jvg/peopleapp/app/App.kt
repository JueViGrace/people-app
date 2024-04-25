package com.jvg.peopleapp.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.jvg.peopleapp.app.navigation.routes.AppRoutes
import com.jvg.peopleapp.app.navigation.screen.AppScreen
import com.jvg.peopleapp.core.presentation.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Navigator(
                screen = AppScreen(initialScreen = AppRoutes.Home.screen)
            ) { navigator: Navigator ->
                SlideTransition(navigator)
            }
        }
    }
}
