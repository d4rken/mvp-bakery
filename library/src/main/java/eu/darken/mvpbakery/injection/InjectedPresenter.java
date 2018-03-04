package eu.darken.mvpbakery.injection;

import android.app.Activity;
import android.app.Fragment;

import eu.darken.mvpbakery.base.Presenter;
import eu.darken.mvpbakery.base.PresenterFactory;
import eu.darken.mvpbakery.injection.activity.HasManualActivityInjector;
import eu.darken.mvpbakery.injection.fragment.HasManualFragmentInjector;
import eu.darken.mvpbakery.injection.fragment.support.HasManualSupportFragmentInjector;


public class InjectedPresenter<
        ViewT extends Presenter.View,
        PresenterT extends ComponentPresenter<ViewT, ComponentT>,
        ComponentT extends PresenterComponent<ViewT, PresenterT>>
        implements PresenterFactory<PresenterT> {

    private final Activity activity;
    private final android.support.v4.app.Fragment supportFragment;
    private final Fragment fragment;

    public InjectedPresenter(Activity source) {
        this.activity = source;
        this.supportFragment = null;
        this.fragment = null;
    }

    public InjectedPresenter(android.support.v4.app.Fragment source) {
        this.supportFragment = source;
        this.activity = null;
        this.fragment = null;
    }

    public InjectedPresenter(Fragment source) {
        this.fragment = source;
        this.activity = null;
        this.supportFragment = null;
    }

    @Override
    public PresenterT createPresenter() {
        ComponentT component;
        if (activity != null) {
            HasManualActivityInjector injectorSource = (HasManualActivityInjector) activity.getApplication();
            //noinspection unchecked
            component = (ComponentT) injectorSource.activityInjector().get(activity);
        } else if (supportFragment != null) {
            HasManualSupportFragmentInjector injectorSource = (HasManualSupportFragmentInjector) supportFragment.getActivity();
            //noinspection unchecked,ConstantConditions
            component = (ComponentT) injectorSource.supportFragmentInjector().get(supportFragment);
        } else if (fragment != null) {
            HasManualFragmentInjector injectorSource = (HasManualFragmentInjector) fragment.getActivity();
            //noinspection unchecked
            component = (ComponentT) injectorSource.fragmentInjector().get(fragment);
        } else {
            throw new RuntimeException("No injection source.");
        }

        final PresenterT presenter = component.getPresenter();
        presenter.setComponent(component);

        return presenter;
    }
}
