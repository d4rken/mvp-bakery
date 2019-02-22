package eu.darken.mvpbakery.base;


import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

public interface Presenter<ViewT extends Presenter.View> {

    void onBindChange(@Nullable ViewT view);

    void onDestroy();

    interface View extends LifecycleOwner {

    }
}
