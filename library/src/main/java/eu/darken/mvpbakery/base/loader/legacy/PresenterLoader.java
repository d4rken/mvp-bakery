package eu.darken.mvpbakery.base.loader.legacy;

import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;

import eu.darken.mvpbakery.base.Presenter;
import eu.darken.mvpbakery.base.PresenterFactory;


public class PresenterLoader<ViewT extends Presenter.View, PresenterT extends Presenter<ViewT>> extends Loader<PresenterT> {

    private PresenterFactory<PresenterT> objectFactory;
    private PresenterT objectToRetain;
    private Bundle savedState;

    public PresenterLoader(Context context, PresenterFactory<PresenterT> factory, @Nullable Bundle savedState) {
        super(context);
        this.savedState = savedState;
        this.objectFactory = factory;
    }

    public PresenterT getPresenter() {
        return objectToRetain;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (objectToRetain == null) forceLoad();
        else deliverResult(objectToRetain);
    }

    @Override
    public void forceLoad() {
        objectToRetain = objectFactory.createPresenter();

        objectFactory = null;
        savedState = null;

        deliverResult(objectToRetain);
    }

    @Override
    protected void onReset() {
        getPresenter().onDestroy();
        super.onReset();
    }

}
