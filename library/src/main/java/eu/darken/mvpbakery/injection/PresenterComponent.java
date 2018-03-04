package eu.darken.mvpbakery.injection;

import eu.darken.mvpbakery.base.Presenter;

public interface PresenterComponent<
        ViewT extends Presenter.View,
        PresenterT extends ComponentPresenter<ViewT, ? extends PresenterComponent>> {

    PresenterT getPresenter();
}
