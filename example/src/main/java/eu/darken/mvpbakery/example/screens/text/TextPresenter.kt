package eu.darken.mvpbakery.example.screens.text

import eu.darken.mvpbakery.base.Presenter
import eu.darken.mvpbakery.injection.ComponentPresenter
import javax.inject.Inject


@TextComponent.Scope
class TextPresenter @Inject
internal constructor() : ComponentPresenter<TextPresenter.View, TextComponent>() {

    private var text: String? = null

    override fun onBindChange(view: View?) {
        super.onBindChange(view)
        withView { v -> v.showText(text) }
        withView { v -> v.showPresenterInfo(this) }
    }

    internal fun onTextInput(text: String) {
        this.text = text
    }

    interface View : Presenter.View {
        fun showText(text: String?)

        fun showPresenterInfo(presenter: TextPresenter)
    }
}
