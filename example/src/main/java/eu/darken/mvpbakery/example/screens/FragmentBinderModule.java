package eu.darken.mvpbakery.example.screens;

import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import eu.darken.mvpbakery.example.screens.counting.CountingComponent;
import eu.darken.mvpbakery.example.screens.counting.CountingFragment;
import eu.darken.mvpbakery.example.screens.text.TextComponent;
import eu.darken.mvpbakery.example.screens.text.TextFragment;
import eu.darken.mvpbakery.injection.fragment.FragmentKey;


@Module(subcomponents = {
        CountingComponent.class,
        TextComponent.class
})
abstract class FragmentBinderModule {

    @Binds
    @IntoMap
    @FragmentKey(CountingFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> counting(CountingComponent.Builder impl);

    @Binds
    @IntoMap
    @FragmentKey(TextFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> text(TextComponent.Builder impl);
}
