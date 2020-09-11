package com.rostislav.ledo.di.modules

import com.rostislav.ledo.ui.ledsgame.view.LedsGameActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeLedsGameActivity(): LedsGameActivity

}