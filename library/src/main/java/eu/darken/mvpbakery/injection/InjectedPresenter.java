package eu.darken.mvpbakery.injection;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import eu.darken.mvpbakery.base.Presenter;
import eu.darken.mvpbakery.base.PresenterFactory;
import eu.darken.mvpbakery.injection.activity.HasManualActivityInjector;
import eu.darken.mvpbakery.injection.fragment.HasManualFragmentInjector;


public class InjectedPresenter<
        ViewT extends Presenter.View,
        PresenterT extends ComponentPresenter<ViewT, ComponentT>,
        ComponentT extends PresenterComponent<ViewT, PresenterT>>
        implements PresenterFactory<PresenterT> {

    private final Activity activity;
    private final Fragment supportFragment;

    public InjectedPresenter(Activity source) {
        this.activity = source;
        this.supportFragment = null;
    }

    public InjectedPresenter(Fragment source) {
        this.supportFragment = source;
        this.activity = null;
    }

    @Override
    public FactoryResult<PresenterT> createPresenter() {
        ComponentT component;
        if (activity != null) {
            HasManualActivityInjector injectorSource = (HasManualActivityInjector) activity.getApplication();
            //noinspection unchecked
            component = (ComponentT) injectorSource.activityInjector().get(activity);
        } else if (supportFragment != null) {
            HasManualFragmentInjector injectorSource = (HasManualFragmentInjector) supportFragment.getActivity();
            try {
                //noinspection unchecked,ConstantConditions
                component = (ComponentT) injectorSource.supportFragmentInjector().get(supportFragment);
            } catch (NullPointerException e) {
                return FactoryResult.retry();
            }
        } else {
            throw new RuntimeException("No injection source.");
        }

        final PresenterT presenter = component.getPresenter();
        presenter.setComponent(component);

        return FactoryResult.forPresenter(presenter);
    }
}
