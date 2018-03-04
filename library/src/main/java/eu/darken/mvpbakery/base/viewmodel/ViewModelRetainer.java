package eu.darken.mvpbakery.base.viewmodel;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import eu.darken.mvpbakery.base.Presenter;
import eu.darken.mvpbakery.base.PresenterFactory;
import eu.darken.mvpbakery.base.PresenterRetainer;
import eu.darken.mvpbakery.base.StateForwarder;
import eu.darken.mvpbakery.base.StateListener;

public class ViewModelRetainer<ViewT extends Presenter.View, PresenterT extends Presenter<ViewT>> implements PresenterRetainer<ViewT, PresenterT> {
    final ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            final Container<ViewT, PresenterT> container = new Container<>(presenterFactory.createPresenter());
            //noinspection unchecked
            return (T) container;
        }
    };
    final ViewModelProvider modelProvider;
    PresenterFactory<PresenterT> presenterFactory;
    @Nullable PresenterT presenter;
    @Nullable StateForwarder stateForwarder;

    public ViewModelRetainer(AppCompatActivity appCompatActivity) {
        modelProvider = ViewModelProviders.of(appCompatActivity, factory);
    }

    public ViewModelRetainer(Fragment supportFragment) {
        modelProvider = ViewModelProviders.of(supportFragment, factory);
    }

    @Override
    public void setPresenterFactory(PresenterFactory<PresenterT> presenterFactory) {
        this.presenterFactory = presenterFactory;
    }

    @Override
    public void setStateForwarder(@Nullable StateForwarder stateForwarder) {
        if (stateForwarder == null) return;
        this.stateForwarder = stateForwarder;
        this.stateForwarder.setListener(new StateForwarder.Listener() {
            @Override
            public boolean onCreate(@Nullable Bundle savedInstanceState) {
                if (getPresenter() instanceof StateListener) {
                    ((StateListener) getPresenter()).onRestoreState(savedInstanceState);
                    return true;
                }
                return false;
            }

            @Override
            public void onSaveInstanceState(Bundle outState) {
                if (getPresenter() instanceof StateListener) {
                    ((StateListener) getPresenter()).onSaveState(outState);
                }
            }
        });
    }

    @Override
    public void attach(LifecycleOwner lifecycleOwner, Callback<ViewT, PresenterT> callback) {
        Callback<ViewT, PresenterT> wrappedCallback = new Callback<ViewT, PresenterT>() {
            @Override
            public void onPresenterCreated(PresenterT presenter) {
                ViewModelRetainer.this.presenter = presenter;
                callback.onPresenterCreated(presenter);
            }

            @Override
            public void onPresenterDestroyed() {
                ViewModelRetainer.this.presenter = null;
                callback.onPresenterDestroyed();
            }
        };


        lifecycleOwner.getLifecycle().addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onCreate(@NonNull LifecycleOwner owner) {
                //noinspection unchecked
                Container<ViewT, PresenterT> container = modelProvider.get(getKey(lifecycleOwner), Container.class);
                container.setCallback(wrappedCallback);
                if (stateForwarder != null && stateForwarder.hasRestoreEvent() && getPresenter() instanceof StateListener) {
                    ((StateListener) getPresenter()).onRestoreState(stateForwarder.getInState());
                }
            }

            @Override
            public void onStart(@NonNull LifecycleOwner owner) {
                //noinspection unchecked, ConstantConditions
                getPresenter().onBindChange((ViewT) owner);
            }

            @Override
            public void onResume(@NonNull LifecycleOwner owner) {

            }

            @Override
            public void onPause(@NonNull LifecycleOwner owner) {

            }

            @Override
            public void onStop(@NonNull LifecycleOwner owner) {
                //noinspection ConstantConditions
                getPresenter().onBindChange(null);
            }
        });
    }

    static String getKey(LifecycleOwner owner) {
        return owner.getClass().getCanonicalName() + ".MVPBakery." + "Default";
    }

    @Nullable
    @Override
    public PresenterT getPresenter() {
        return presenter;
    }

    static class Container<ViewT extends Presenter.View, PresenterT extends Presenter<ViewT>> extends ViewModel {
        final PresenterT presenter;
        Callback<ViewT, PresenterT> callback;

        Container(PresenterT presenter) {
            this.presenter = presenter;
        }

        public void setCallback(Callback<ViewT, PresenterT> callback) {
            this.callback = callback;
            this.callback.onPresenterCreated(presenter);
        }

        @Override
        protected void onCleared() {
            presenter.onDestroy();
            callback.onPresenterDestroyed();
            super.onCleared();
        }
    }
}
