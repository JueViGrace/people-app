package com.jvg.peopleapp.core.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

val dispatchersModule = module {
    single<CoroutineContext> { Dispatchers.IO }
    single<CoroutineScope> { CoroutineScope(get()) }
}
