package com.jvg.peopleapp.core.presentation.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.jvg.peopleapp.home.routes.HomeTabs

@Composable
fun BottomBarComponent(tabNavigator: TabNavigator) {
    val bottomList = HomeTabs::class.sealedSubclasses.map { it.objectInstance?.tab as Tab }
    BottomAppBar(
        actions = {
            bottomList.forEach { tab ->
                BottomNavigationItem(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 5.dp),
                    tabNavigator,
                    tab,
                )
            }
        },
        contentPadding = PaddingValues(
            top = BottomAppBarDefaults.windowInsets.asPaddingValues().calculateTopPadding(),
            bottom = BottomAppBarDefaults.windowInsets.asPaddingValues().calculateBottomPadding(),
        ),
    )
}
