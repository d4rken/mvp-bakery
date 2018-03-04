package eu.darken.mvpbakery.base.loader;

import android.content.Context;

import eu.darken.mvpbakery.base.Presenter;
import eu.darken.mvpbakery.base.PresenterFactory;


public abstract class LoaderFactory<ViewT extends Presenter.View, PresenterT extends Presenter<ViewT>> {

    private final int loaderId;
    private final Context context;

    protected LoaderFactory(Context context, int loaderId) {
        this.context = context;
        this.loaderId = loaderId;
    }

    public Context getContext() {
        return context;
    }

    public int getLoaderId() {
        return loaderId;
    }

    public abstract void load(PresenterFactory<PresenterT> factory, final LoaderFactory.Callback<ViewT, PresenterT> callback);

    public interface Callback<ViewT extends Presenter.View, PresenterT extends Presenter<ViewT>> {
        void onPresenterReady(PresenterT presenter);

        void onPresenterDestroyed();
    }

}
