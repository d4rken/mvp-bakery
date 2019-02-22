package eu.darken.mvpbakery.injection;


import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import eu.darken.mvpbakery.base.Presenter;

public abstract class ComponentPresenter<ViewT extends Presenter.View, ComponentT extends PresenterComponent<ViewT, ? extends ComponentPresenter>>
        implements Presenter<ViewT> {

    private ComponentT component;
    private ViewT view;

    @Nullable
    public ViewT getView() {
        return view;
    }

    @CallSuper
    @Override
    public void onBindChange(@Nullable ViewT view) {
        this.view = view;
    }

    @CallSuper
    @Override
    public void onDestroy() {

    }

    void setComponent(ComponentT component) {
        this.component = component;
    }

    public ComponentT getComponent() {
        return component;
    }

    public interface ViewAction<T extends Presenter.View> {
        void runOnView(T v);
    }

    public void onView(ViewAction<ViewT> a) {
        ViewT v = getView();
        if (v != null) a.runOnView(v);
    }
}
