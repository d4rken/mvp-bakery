package eu.darken.mvpbakery.base;

public interface PresenterFactory<PresenterT extends Presenter> {
    PresenterT createPresenter();
}
