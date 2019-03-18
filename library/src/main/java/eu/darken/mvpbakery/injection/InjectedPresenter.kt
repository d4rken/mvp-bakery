package eu.darken.mvpbakery.injection

import android.app.Activity

import androidx.fragment.app.Fragment
import eu.darken.mvpbakery.base.Presenter
import eu.darken.mvpbakery.base.PresenterFactory
import eu.darken.mvpbakery.injection.activity.HasManualActivityInjector
import eu.darken.mvpbakery.injection.fragment.HasManualFragmentInjector


class InjectedPresenter<ViewT : Presenter.View, PresenterT : ComponentPresenter<ViewT, ComponentT>, ComponentT : PresenterComponent<PresenterT, ComponentT>> : PresenterFactory<PresenterT> {
    private val activity: Activity?
    private val supportFragment: Fragment?

    constructor(source: Activity) {
        this.activity = source
        this.supportFragment = null
    }

    constructor(source: Fragment) {
        this.supportFragment = source
        this.activity = null
    }

    override fun createPresenter(): PresenterFactory.FactoryResult<PresenterT> {
        val component: ComponentT
        when {
            activity != null -> {
                val injectorSource = activity.application as HasManualActivityInjector
                @Suppress("UNCHECKED_CAST")
                component = injectorSource.activityInjector()[activity] as ComponentT
            }
            supportFragment != null -> {
                val injectorSource = supportFragment.activity as HasManualFragmentInjector
                try {
                    @Suppress("UNCHECKED_CAST")
                    component = injectorSource.supportFragmentInjector()[supportFragment] as ComponentT
                } catch (e: UninitializedPropertyAccessException) {
                    return PresenterFactory.FactoryResult.retry()
                }

            }
            else -> throw RuntimeException("No injection source.")
        }

        val presenter = component.presenter
        presenter.component = component

        return PresenterFactory.FactoryResult.forPresenter(presenter)
    }
}
