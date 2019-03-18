package eu.darken.mvpbakery.example.screens.counting

import dagger.Subcomponent
import eu.darken.mvpbakery.injection.PresenterComponent
import eu.darken.mvpbakery.injection.fragment.FragmentComponent

@CountingComponent.Scope
@Subcomponent(modules = [CountingModule::class])
interface CountingComponent : FragmentComponent<CountingFragment>, PresenterComponent<CountingPresenter, CountingComponent> {

    @Subcomponent.Builder
    abstract class Builder : FragmentComponent.Builder<CountingFragment, CountingComponent>() {
        abstract fun module(module: CountingModule): Builder
    }

    @javax.inject.Scope
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Scope
}
