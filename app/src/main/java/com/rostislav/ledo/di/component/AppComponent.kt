package com.rostislav.ledo.di.component

import com.rostislav.ledo.CustomApplication
import com.rostislav.ledo.di.modules.AppModule
import com.rostislav.ledo.di.modules.BuildersModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        BuildersModule::class,
        AppModule::class
    ]
)

interface AppComponent {
    fun inject(app: CustomApplication)
}