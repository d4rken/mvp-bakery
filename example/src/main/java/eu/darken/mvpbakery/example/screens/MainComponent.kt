package eu.darken.mvpbakery.example.screens


import dagger.Subcomponent
import dagger.android.support.AndroidSupportInjectionModule
import eu.darken.mvpbakery.injection.PresenterComponent
import eu.darken.mvpbakery.injection.activity.ActivityComponent

@MainComponent.Scope
@Subcomponent(modules = [FragmentBinderModule::class, AndroidSupportInjectionModule::class, MainModule::class])
interface MainComponent : ActivityComponent<MainActivity>, PresenterComponent<MainPresenter, MainComponent> {

    @Subcomponent.Builder
    abstract class Builder : ActivityComponent.Builder<MainActivity, MainComponent>()

    @javax.inject.Scope
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Scope
}
