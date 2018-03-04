package eu.darken.mvpbakery.base.loader.support;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import eu.darken.mvpbakery.base.Presenter;
import eu.darken.mvpbakery.base.PresenterFactory;
import eu.darken.mvpbakery.base.loader.LoaderFactory;


public class PresenterSupportLoaderFactory<ViewT extends Presenter.View, PresenterT extends Presenter<ViewT>> extends LoaderFactory<ViewT, PresenterT> {

    private final LoaderManager loaderManager;

    public PresenterSupportLoaderFactory(Context context, LoaderManager manager, int loaderId) {
        super(context, loaderId);
        this.loaderManager = manager;
    }

    @Override
    public void load(PresenterFactory<PresenterT> factory, final LoaderFactory.Callback<ViewT, PresenterT> callback) {
        Loader<PresenterT> loader = loaderManager.getLoader(getLoaderId());
        if (loader instanceof PresenterSupportLoader) {
            PresenterSupportLoader<ViewT, PresenterT> presenterLoader = (PresenterSupportLoader<ViewT, PresenterT>) loader;
            callback.onPresenterReady(presenterLoader.getPresenter());
        } else {
            loaderManager.initLoader(getLoaderId(), null, new LoaderManager.LoaderCallbacks<PresenterT>() {
                @NonNull
                @Override
                public Loader<PresenterT> onCreateLoader(int id, Bundle args) {
                    return new PresenterSupportLoader<>(getContext(), factory, args);
                }

                @Override
                public void onLoadFinished(@NonNull Loader<PresenterT> loader, PresenterT presenter) {
                    callback.onPresenterReady(presenter);
                }

                @Override
                public void onLoaderReset(@NonNull Loader<PresenterT> loader) {
                    callback.onPresenterDestroyed();
                }
            });
        }
    }

}
