package eu.darken.mvpbakery.base;

import android.app.Activity;
import android.app.Fragment;
import android.arch.lifecycle.LifecycleOwner;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import eu.darken.mvpbakery.injection.InjectedPresenter;

public class MVPBakery<ViewT extends Presenter.View, PresenterT extends Presenter<ViewT>> {
    final PresenterRetainer<ViewT, PresenterT> presenterRetainer;
    final StateForwarder stateForwarder;
    final PresenterFactory<PresenterT> presenterFactory;
    final List<PresenterRetainer.Callback<ViewT, PresenterT>> presenterCallbacks;


    MVPBakery(Builder<ViewT, PresenterT> builder) {
        this.presenterRetainer = builder.presenterRetainer;
        this.stateForwarder = builder.stateForwarder;
        this.presenterCallbacks = builder.presenterCallbacks;
        this.presenterFactory = builder.presenterFactory;
    }

    public void attach(LifecycleOwner lifecycleOwner) {
        if (stateForwarder != null) this.presenterRetainer.setStateForwarder(stateForwarder);
        this.presenterRetainer.setPresenterFactory(presenterFactory);
        this.presenterRetainer.attach(lifecycleOwner, new PresenterRetainer.Callback<ViewT, PresenterT>() {
            @Override
            public void onPresenterCreated(PresenterT presenter) {
                for (PresenterRetainer.Callback<ViewT, PresenterT> c : presenterCallbacks) c.onPresenterCreated(presenter);
            }

            @Override
            public void onPresenterDestroyed() {
                for (PresenterRetainer.Callback<ViewT, PresenterT> c : presenterCallbacks) c.onPresenterDestroyed();
            }
        });
    }

    @Nullable
    public PresenterT getPresenter() {
        return presenterRetainer.getPresenter();
    }

    public static <ViewT extends Presenter.View, PresenterT extends Presenter<ViewT>> Builder<ViewT, PresenterT> builder() {
        return new Builder<>();
    }

    public static class Builder<ViewT extends Presenter.View & LifecycleOwner, PresenterT extends Presenter<ViewT>> {
        PresenterFactory<PresenterT> presenterFactory;
        PresenterRetainer<ViewT, PresenterT> presenterRetainer;
        StateForwarder stateForwarder;
        final List<PresenterRetainer.Callback<ViewT, PresenterT>> presenterCallbacks = new ArrayList<>();

        Builder() {
        }

        /**
         * If you want the presenter to be able to store data via {@link Activity#onSaveInstanceState(Bundle)} then you need to call this.
         *
         * @param stateForwarder pass a {@link StateForwarder object} that you have to call on in onCreate()/onSaveInstance()
         */
        public Builder<ViewT, PresenterT> stateForwarder(StateForwarder stateForwarder) {
            this.stateForwarder = stateForwarder;
            return this;
        }

        public Builder<ViewT, PresenterT> addPresenterCallback(PresenterRetainer.Callback<ViewT, PresenterT> callback) {
            presenterCallbacks.add(callback);
            return this;
        }

        /**
         * For injection you probably want to pass a {@link eu.darken.mvpbakery.injection.PresenterInjectionCallback}
         */
        public Builder<ViewT, PresenterT> presenterRetainer(PresenterRetainer<ViewT, PresenterT> presenterRetainer) {
            this.presenterRetainer = presenterRetainer;
            return this;
        }

        /**
         * For injection pass an {@link InjectedPresenter}
         */
        public Builder<ViewT, PresenterT> presenterFactory(PresenterFactory<PresenterT> presenterFactory) {
            this.presenterFactory = presenterFactory;
            return this;
        }

        public MVPBakery<ViewT, PresenterT> build() {
            return new MVPBakery<>(this);
        }

        /**
         * @param lifecycleOwner Your {@link AppCompatActivity}, {@link Fragment} or {@link android.support.v4.app.Fragment}
         */
        public MVPBakery<ViewT, PresenterT> attach(ViewT lifecycleOwner) {
            final MVPBakery<ViewT, PresenterT> lib = build();
            lib.attach(lifecycleOwner);
            return lib;
        }
    }

}