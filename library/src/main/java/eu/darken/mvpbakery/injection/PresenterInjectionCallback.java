package eu.darken.mvpbakery.injection;

import dagger.android.AndroidInjector;
import eu.darken.mvpbakery.base.Presenter;
import eu.darken.mvpbakery.base.PresenterRetainer;

public class PresenterInjectionCallback<
        TargetT extends ViewT,
        ViewT extends Presenter.View,
        PresenterT extends ComponentPresenter<ViewT, ComponentT>,
        ComponentT extends PresenterComponent<ViewT, PresenterT> & AndroidInjector<TargetT>>
        implements PresenterRetainer.Callback<ViewT, PresenterT> {

    private final TargetT injectionTarget;

    public PresenterInjectionCallback(TargetT injectionTarget) {
        this.injectionTarget = injectionTarget;
    }

    @Override
    public void onPresenterAvailable(PresenterT presenter) {
        final ComponentT component = presenter.getComponent();
        component.inject(injectionTarget);
    }
}
