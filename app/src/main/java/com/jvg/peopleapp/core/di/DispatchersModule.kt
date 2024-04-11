package com.jvg.peopleapp.core.di

import com.jvg.peopleapp.core.common.Constants.IO_DISPATCHER
import com.jvg.peopleapp.core.common.Constants.MAIN_DISPATCHER
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dispatchersModule = module {
    single(named(IO_DISPATCHER)) {
        Dispatchers.IO
    }
    single(named(MAIN_DISPATCHER)) {
        Dispatchers.Main
    }
}