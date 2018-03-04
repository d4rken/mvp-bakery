package eu.darken.mvpbakery.example.screens;

import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;
import eu.darken.mvpbakery.example.screens.counting.CountingFragment;

@Module
public class MainModule {

    @Provides
    @MainComponent.Scope
    Class<? extends Fragment> provideStartFragment() {
        return CountingFragment.class;
    }
}
