package eu.darken.mvpbakery.example.screens;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import dagger.Subcomponent;
import dagger.android.support.AndroidSupportInjectionModule;
import eu.darken.mvpbakery.injection.PresenterComponent;
import eu.darken.mvpbakery.injection.activity.ActivityComponent;

@MainComponent.Scope
@Subcomponent(modules = {
        FragmentBinderModule.class,
        AndroidSupportInjectionModule.class,
        MainModule.class
})
public interface MainComponent extends ActivityComponent<MainActivity>, PresenterComponent<MainPresenter.View, MainPresenter> {

    @Subcomponent.Builder
    abstract class Builder extends ActivityComponent.Builder<MainActivity, MainComponent> {}

    @javax.inject.Scope
    @Retention(RetentionPolicy.RUNTIME)
    @interface Scope {}
}
