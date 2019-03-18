package eu.darken.mvpbakery.example.screens.counting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import eu.darken.mvpbakery.base.MVPBakery
import eu.darken.mvpbakery.base.StateForwarder
import eu.darken.mvpbakery.base.ViewModelRetainer
import eu.darken.mvpbakery.example.R
import eu.darken.mvpbakery.injection.InjectedPresenter
import eu.darken.mvpbakery.injection.PresenterInjectionCallback

class CountingFragment : Fragment(), CountingPresenter.View {

    @BindView(R.id.fragment_text)
    lateinit var textView: TextView
    @BindView(R.id.presenter_info)
    lateinit var presenterInfo: TextView

    private lateinit var stateForwarder: StateForwarder
    private lateinit var mvpBakery: MVPBakery<CountingPresenter.View, CountingPresenter>
    private var unbinder: Unbinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stateForwarder = StateForwarder()
        stateForwarder.onCreate(savedInstanceState)
        mvpBakery = MVPBakery.builder<CountingPresenter.View, CountingPresenter>()
                .stateForwarder(stateForwarder)
                .presenterFactory(InjectedPresenter(this))
                .presenterRetainer(ViewModelRetainer(this))
                .addPresenterCallback(PresenterInjectionCallback(this))
                .attach(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_counting, container, false)
        unbinder = ButterKnife.bind(this, view)
        return view
    }

    override fun onDestroyView() {
        unbinder!!.unbind()
        super.onDestroyView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        stateForwarder.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun showText(text: String) {
        textView.text = text
    }

    @OnClick(R.id.fragment_button)
    internal fun onCountClick() {
        mvpBakery.presenter!!.onCountClick()
    }

    override fun showPresenterInfo(presenter: CountingPresenter) {
        presenterInfo.text = presenter.toString()
    }
}
