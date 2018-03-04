package eu.darken.mvpbakery.base.loader.legacy;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import eu.darken.mvpbakery.base.Presenter;
import eu.darken.mvpbakery.base.PresenterFactory;
import eu.darken.mvpbakery.base.loader.LoaderFactory;


public class PresenterLoaderFactory<ViewT extends Presenter.View, PresenterT extends Presenter<ViewT>> extends LoaderFactory<ViewT, PresenterT> {
    private final LoaderManager loaderManager;

    public PresenterLoaderFactory(Context context, LoaderManager manager, int loaderId) {
        super(context, loaderId);
        this.loaderManager = manager;
    }

    @Override
    public void load(PresenterFactory<PresenterT> factory, final LoaderFactory.Callback<ViewT, PresenterT> callback) {
        Loader<PresenterT> loader = loaderManager.getLoader(getLoaderId());
        if (loader instanceof PresenterLoader) {
            PresenterLoader<ViewT, PresenterT> presenterLoader = (PresenterLoader<ViewT, PresenterT>) loader;
            callback.onPresenterReady(presenterLoader.getPresenter());
        } else {
            loaderManager.initLoader(getLoaderId(), null, new LoaderManager.LoaderCallbacks<PresenterT>() {
                @Override
                public Loader<PresenterT> onCreateLoader(int id, Bundle args) {
                    return new PresenterLoader<>(getContext(), factory, args);
                }

                @Override
                public void onLoadFinished(Loader<PresenterT> loader, PresenterT presenter) {
                    callback.onPresenterReady(presenter);
                }

                @Override
                public void onLoaderReset(Loader<PresenterT> loader) {
                    callback.onPresenterDestroyed();
                }
            });
        }
    }
}
