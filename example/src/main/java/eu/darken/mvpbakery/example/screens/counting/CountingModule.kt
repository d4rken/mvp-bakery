package eu.darken.mvpbakery.example.screens.counting

import dagger.Module
import dagger.Provides

@Module
class CountingModule {

    @Provides
    @CountingComponent.Scope
    internal fun provideCounter(): Counter = Counter(-1)

}
