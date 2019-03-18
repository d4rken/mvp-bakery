package eu.darken.mvpbakery.example.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import eu.darken.mvpbakery.base.Presenter
import eu.darken.mvpbakery.base.StateListener
import eu.darken.mvpbakery.example.screens.counting.CountingFragment
import eu.darken.mvpbakery.example.screens.text.TextFragment
import eu.darken.mvpbakery.injection.ComponentPresenter
import javax.inject.Inject


@MainComponent.Scope
class MainPresenter @Inject
internal constructor(private val startingFragment: Class<out Fragment>) : ComponentPresenter<MainPresenter.View, MainComponent>(), StateListener {
    private var doInit = true
    private var bindCounter = 0
    private var fragmentNo = 1

    override fun onRestoreState(inState: Bundle?) {
        if (inState != null) doInit = inState.getBoolean("doInit")
        if (inState != null) bindCounter = inState.getInt("counter")
        if (inState != null) fragmentNo = inState.getInt("fragmentNo")
    }

    override fun onSaveState(outState: Bundle) {
        outState.putBoolean("doInit", doInit)
        outState.putInt("counter", bindCounter)
        outState.putInt("fragmentNo", fragmentNo)
    }

    override fun onBindChange(view: View?) {
        super.onBindChange(view)
        withView { v -> v.showBinderCounter(++bindCounter) }
        withView { v ->
            if (doInit) {
                doInit = false
                v.addFragment(startingFragment)
            } else {
                val oldFrags = fragmentNo
                fragmentNo = 0
                for (i in 0 until oldFrags) {
                    addNewFragment()
                }
            }
        }
    }

    private fun addFragment(pos: Int) {
        when (pos % 2) {
            0 -> withView { v -> v.addFragment(CountingFragment::class.java) }
            1 -> withView { v -> v.addFragment(TextFragment::class.java) }
        }
    }

    fun addNewFragment() {
        addFragment(fragmentNo++)
    }

    interface View : Presenter.View {
        fun showBinderCounter(count: Int)

        fun addFragment(fragmentClass: Class<out Fragment>)
    }
}
