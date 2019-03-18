package eu.darken.mvpbakery.example.screens

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import eu.darken.mvpbakery.example.screens.counting.CountingFragment

@Module
class MainModule {

    @Provides
    @MainComponent.Scope
    internal fun provideStartFragment(): Class<out Fragment> {
        return CountingFragment::class.java
    }
}
