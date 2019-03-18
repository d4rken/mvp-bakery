package eu.darken.mvpbakery.example.screens.text

import dagger.Subcomponent
import eu.darken.mvpbakery.injection.PresenterComponent
import eu.darken.mvpbakery.injection.fragment.FragmentComponent

@TextComponent.Scope
@Subcomponent
interface TextComponent : FragmentComponent<TextFragment>, PresenterComponent<TextPresenter, TextComponent> {

    @Subcomponent.Builder
    abstract class Builder : FragmentComponent.Builder<TextFragment, TextComponent>()

    @javax.inject.Scope
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Scope
}
