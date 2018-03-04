package eu.darken.mvpbakery.injection.fragment.support;

import android.support.v4.app.Fragment;

import dagger.android.AndroidInjector;

public interface SupportFragmentComponent<FragmentT extends Fragment> extends AndroidInjector<FragmentT> {

    abstract class Builder<FragmentT extends Fragment, ComponentT extends SupportFragmentComponent<FragmentT>>
            extends AndroidInjector.Builder<FragmentT> {
        public abstract ComponentT build();
    }
}
