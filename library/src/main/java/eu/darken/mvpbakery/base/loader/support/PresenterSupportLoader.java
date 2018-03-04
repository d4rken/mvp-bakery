package eu.darken.mvpbakery.base.loader.support;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;

import eu.darken.mvpbakery.base.Presenter;
import eu.darken.mvpbakery.base.PresenterFactory;


public class PresenterSupportLoader<ViewT extends Presenter.View, PresenterT extends Presenter<ViewT>> extends Loader<PresenterT> {

    private PresenterFactory<PresenterT> presenterFactory;
    private PresenterT objectToRetain;
    private Bundle savedState;

    public PresenterSupportLoader(Context context, PresenterFactory<PresenterT> factory, @Nullable Bundle savedState) {
        super(context);
        this.savedState = savedState;
        this.presenterFactory = factory;
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
        objectToRetain = presenterFactory.createPresenter();

        presenterFactory = null;
        savedState = null;

        deliverResult(objectToRetain);
    }

    @Override
    protected void onReset() {
        getPresenter().onDestroy();
        super.onReset();
    }
}
