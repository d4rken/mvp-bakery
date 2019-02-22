package eu.darken.mvpbakery.base;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

public interface PresenterRetainer<ViewT extends Presenter.View, PresenterT extends Presenter<ViewT>> {
    @Nullable
    PresenterT getPresenter();

    void setStateForwarder(@Nullable StateForwarder stateForwarder);

    void setPresenterFactory(PresenterFactory<PresenterT> presenterFactory);

    void attach(LifecycleOwner lifecycleOwner, Callback<ViewT, PresenterT> callback);

    interface Callback<ViewT extends Presenter.View, PresenterT extends Presenter<ViewT>> {
        void onPresenterAvailable(PresenterT presenter);
    }

    class DefaultStateListener implements StateForwarder.Listener {
        private final PresenterRetainer retainer;

        public DefaultStateListener(PresenterRetainer retainer) {this.retainer = retainer;}

        @Override
        public boolean onCreate(@Nullable Bundle savedInstanceState) {
            if (retainer.getPresenter() instanceof StateListener) {
                ((StateListener) retainer.getPresenter()).onRestoreState(savedInstanceState);
                return true;
            }
            return false;
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            if (retainer.getPresenter() instanceof StateListener) {
                ((StateListener) retainer.getPresenter()).onSaveState(outState);
            }
        }
    }
}
