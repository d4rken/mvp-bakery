package eu.darken.mvpbakery.example.screens

import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import eu.darken.mvpbakery.example.screens.counting.CountingComponent
import eu.darken.mvpbakery.example.screens.counting.CountingFragment
import eu.darken.mvpbakery.example.screens.text.TextComponent
import eu.darken.mvpbakery.example.screens.text.TextFragment
import eu.darken.mvpbakery.injection.fragment.FragmentKey


@Module(subcomponents = [CountingComponent::class, TextComponent::class])
internal abstract class FragmentBinderModule {

    @Binds
    @IntoMap
    @FragmentKey(CountingFragment::class)
    internal abstract fun counting(impl: CountingComponent.Builder): AndroidInjector.Factory<out Fragment>

    @Binds
    @IntoMap
    @FragmentKey(TextFragment::class)
    internal abstract fun text(impl: TextComponent.Builder): AndroidInjector.Factory<out Fragment>
}
