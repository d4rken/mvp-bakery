package eu.darken.mvpbakery.base;


import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.Nullable;

public interface PresenterRetainer<ViewT extends Presenter.View, PresenterT extends Presenter<ViewT>> {
    @Nullable
    PresenterT getPresenter();

    void setStateForwarder(@Nullable StateForwarder stateForwarder);

    void setPresenterFactory(PresenterFactory<PresenterT> presenterFactory);

    void attach(LifecycleOwner lifecycleOwner, Callback<ViewT, PresenterT> callback);

    interface Callback<ViewT extends Presenter.View, PresenterT extends Presenter<ViewT>> {
        void onPresenterCreated(PresenterT presenter);

        void onPresenterDestroyed();
    }

}
