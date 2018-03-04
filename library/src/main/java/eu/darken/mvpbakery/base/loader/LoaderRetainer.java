package eu.darken.mvpbakery.base.loader;

import android.app.Activity;
import android.app.Fragment;
import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import eu.darken.mvpbakery.base.Presenter;
import eu.darken.mvpbakery.base.PresenterFactory;
import eu.darken.mvpbakery.base.PresenterRetainer;
import eu.darken.mvpbakery.base.StateForwarder;
import eu.darken.mvpbakery.base.StateListener;
import eu.darken.mvpbakery.base.loader.legacy.PresenterLoaderFactory;
import eu.darken.mvpbakery.base.loader.support.PresenterSupportLoaderFactory;


public class LoaderRetainer<ViewT extends Presenter.View, PresenterT extends Presenter<ViewT>> implements PresenterRetainer<ViewT, PresenterT> {
    private final int loaderId = 2017;
    final LoaderFactory<ViewT, PresenterT> loaderFactory;
    PresenterFactory<PresenterT> presenterFactory;
    @Nullable PresenterT presenter;
    @Nullable StateForwarder stateForwarder;

    public LoaderRetainer(android.support.v4.app.Fragment supportFragment) {
        loaderFactory = new PresenterSupportLoaderFactory<>(supportFragment.getContext(), supportFragment.getLoaderManager(), loaderId);
    }

    public LoaderRetainer(AppCompatActivity appCompatActivity) {
        loaderFactory = new PresenterSupportLoaderFactory<>(appCompatActivity, appCompatActivity.getSupportLoaderManager(), loaderId);
    }

    public LoaderRetainer(Activity activity) {
        loaderFactory = new PresenterLoaderFactory<>(activity, activity.getLoaderManager(), loaderId);
    }

    public LoaderRetainer(Fragment fragment) {
        loaderFactory = new PresenterLoaderFactory<>(fragment.getActivity(), fragment.getLoaderManager(), loaderId);
    }

    @Nullable
    @Override
    public PresenterT getPresenter() {
        return presenter;
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
        lifecycleOwner.getLifecycle().addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onCreate(@NonNull LifecycleOwner owner) {
                loaderFactory.load(presenterFactory, new LoaderFactory.Callback<ViewT, PresenterT>() {
                    @Override
                    public void onPresenterReady(PresenterT presenter) {
                        LoaderRetainer.this.presenter = presenter;
                        callback.onPresenterCreated(presenter);
                        if (stateForwarder != null && stateForwarder.hasRestoreEvent() && getPresenter() instanceof StateListener) {
                            ((StateListener) getPresenter()).onRestoreState(stateForwarder.getInState());
                        }
                    }

                    @Override
                    public void onPresenterDestroyed() {
                        LoaderRetainer.this.presenter = null;
                        callback.onPresenterDestroyed();
                    }
                });
            }

            @Override
            public void onResume(@NonNull LifecycleOwner owner) {
                //noinspection unchecked,ConstantConditions
                getPresenter().onBindChange((ViewT) owner);
            }

            @Override
            public void onPause(@NonNull LifecycleOwner owner) {
                //noinspection ConstantConditions
                getPresenter().onBindChange(null);
            }
        });
    }

}
