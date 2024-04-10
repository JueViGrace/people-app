package com.jvg.peopleapp.home.di

import com.jvg.peopleapp.home.data.local.HomeDataSourceImpl
import com.jvg.peopleapp.home.domain.sources.HomeDataSource
import com.jvg.peopleapp.home.presentation.dashboard.viewmodel.DashborardViewModel
import com.jvg.peopleapp.home.presentation.details.viewmodel.PersonViewModel
import org.koin.dsl.module

val homeModule = module {
    single<HomeDataSource> {
        HomeDataSourceImpl(get())
    }
    factory {
        DashborardViewModel(get())
    }
    factory {
        PersonViewModel(get())
    }
}
