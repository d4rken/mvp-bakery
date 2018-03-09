package eu.darken.mvpbakery.example.screens.text;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import dagger.Subcomponent;
import eu.darken.mvpbakery.injection.PresenterComponent;
import eu.darken.mvpbakery.injection.fragment.support.SupportFragmentComponent;

@TextComponent.Scope
@Subcomponent()
public interface TextComponent extends SupportFragmentComponent<TextFragment>, PresenterComponent<TextPresenter.View, TextPresenter> {

    @Subcomponent.Builder
    abstract class Builder extends SupportFragmentComponent.Builder<TextFragment, TextComponent> {

    }

    @javax.inject.Scope
    @Retention(RetentionPolicy.RUNTIME)
    @interface Scope {
    }
}
