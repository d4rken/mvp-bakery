package eu.darken.mvpbakery.example.screens;

import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;
import eu.darken.mvpbakery.example.screens.counting.CountingComponent;
import eu.darken.mvpbakery.example.screens.counting.CountingFragment;


@Module(subcomponents = {CountingComponent.class})
public abstract class FragmentBinderModule {

    @Binds
    @IntoMap
    @FragmentKey(CountingFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> counting(CountingComponent.Builder impl);
}
