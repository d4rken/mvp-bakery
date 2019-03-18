package eu.darken.mvpbakery.example.screens.text

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import butterknife.BindView
import butterknife.ButterKnife
import eu.darken.mvpbakery.base.MVPBakery
import eu.darken.mvpbakery.base.StateForwarder
import eu.darken.mvpbakery.base.ViewModelRetainer
import eu.darken.mvpbakery.example.R
import eu.darken.mvpbakery.injection.InjectedPresenter
import eu.darken.mvpbakery.injection.PresenterInjectionCallback
import javax.inject.Inject

class TextFragment : Fragment(), TextPresenter.View {

    @BindView(R.id.text_input)
    lateinit var textInput: EditText
    @BindView(R.id.presenter_info)
    lateinit var presenterInfo: TextView

    private val stateForwarder = StateForwarder()
    @Inject
    lateinit var presenter: TextPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_text, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        stateForwarder.onCreate(savedInstanceState)
        MVPBakery.builder<TextPresenter.View, TextPresenter>()
                .stateForwarder(stateForwarder)
                .presenterFactory(InjectedPresenter(this))
                .presenterRetainer(ViewModelRetainer(this))
                .addPresenterCallback(PresenterInjectionCallback(this))
                .attach(this)
        textInput.isSaveEnabled = false
        textInput.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit

            override fun afterTextChanged(s: Editable) = presenter.onTextInput(s.toString())
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        stateForwarder.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun showText(text: String?) = textInput.setText(text)

    override fun showPresenterInfo(presenter: TextPresenter) {
        presenterInfo.text = presenter.toString()
    }
}
