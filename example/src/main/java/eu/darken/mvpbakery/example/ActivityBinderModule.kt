package eu.darken.mvpbakery.example

import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import eu.darken.mvpbakery.example.screens.MainActivity
import eu.darken.mvpbakery.example.screens.MainComponent
import eu.darken.mvpbakery.injection.activity.ActivityKey

@Module(subcomponents = [MainComponent::class])
internal abstract class ActivityBinderModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    internal abstract fun mainActivity(impl: MainComponent.Builder): AndroidInjector.Factory<out Activity>
}