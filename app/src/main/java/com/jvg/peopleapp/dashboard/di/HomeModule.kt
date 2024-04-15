package com.jvg.peopleapp.dashboard.di

import com.jvg.peopleapp.core.common.Constants.IO_DISPATCHER
import com.jvg.peopleapp.people.presentation.viewmodel.PeopleViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val homeModule = module {

    factory {
        PeopleViewModel(get(), get(named(IO_DISPATCHER)))
    }

}
