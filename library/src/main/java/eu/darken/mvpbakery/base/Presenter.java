package eu.darken.mvpbakery.base;


import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.Nullable;

public interface Presenter<ViewT extends Presenter.View> {

    void onBindChange(@Nullable ViewT view);

    void onDestroy();

    interface View extends LifecycleOwner {

    }
}
