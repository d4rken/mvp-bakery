package eu.darken.mvpbakery.example;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import eu.darken.mvpbakery.example.screens.MainActivity;
import eu.darken.mvpbakery.example.screens.MainComponent;
import eu.darken.mvpbakery.injection.activity.ActivityKey;

@Module(subcomponents = {MainComponent.class})
abstract class ActivityBinderModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends android.app.Activity> mainActivity(MainComponent.Builder impl);
}