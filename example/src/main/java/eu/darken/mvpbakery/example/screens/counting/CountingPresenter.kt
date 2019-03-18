package eu.darken.mvpbakery.example.screens.counting

import android.os.Bundle
import eu.darken.mvpbakery.base.Presenter
import eu.darken.mvpbakery.base.StateListener
import eu.darken.mvpbakery.injection.ComponentPresenter
import timber.log.Timber
import javax.inject.Inject


@CountingComponent.Scope
class CountingPresenter @Inject
internal constructor(private val counter: Counter) : ComponentPresenter<CountingPresenter.View, CountingComponent>(), StateListener {

    fun onCountClick() {
        view?.showText(counter.countUp().toString())
    }

    override fun onRestoreState(inState: Bundle?) {
        if (inState != null) counter.setCounter(inState.getInt("counter"))
    }

    override fun onBindChange(view: View?) {
        super.onBindChange(view)
        view?.showText(counter.current.toString())
        withView { v -> v.showPresenterInfo(this) }
    }

    override fun onSaveState(outState: Bundle) {
        outState.putInt("counter", counter.current)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy()")
    }

    interface View : Presenter.View {
        fun showText(text: String)

        fun showPresenterInfo(presenter: CountingPresenter)
    }
}
