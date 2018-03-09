package eu.darken.mvpbakery.example.screens.counting;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import dagger.Subcomponent;
import eu.darken.mvpbakery.injection.PresenterComponent;
import eu.darken.mvpbakery.injection.fragment.FragmentComponent;

@CountingComponent.Scope
@Subcomponent(modules = {CountingModule.class})
public interface CountingComponent extends FragmentComponent<CountingFragment>, PresenterComponent<CountingPresenter.View, CountingPresenter> {

    @Subcomponent.Builder
    abstract class Builder extends FragmentComponent.Builder<CountingFragment, CountingComponent> {
        public abstract Builder module(CountingModule module);
    }

    @javax.inject.Scope
    @Retention(RetentionPolicy.RUNTIME)
    @interface Scope {
    }
}
