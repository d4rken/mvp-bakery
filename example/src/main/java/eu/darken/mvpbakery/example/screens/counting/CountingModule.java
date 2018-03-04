package eu.darken.mvpbakery.example.screens.counting;

import dagger.Module;
import dagger.Provides;

@Module
public class CountingModule {

    @Provides
    @CountingComponent.Scope
    Counter provideCounter() {
        return new Counter(-1);
    }

}
