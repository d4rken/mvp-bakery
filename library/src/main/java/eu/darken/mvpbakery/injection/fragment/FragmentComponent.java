package eu.darken.mvpbakery.injection.fragment;

import android.app.Fragment;

import dagger.android.AndroidInjector;

public interface FragmentComponent<FragmentT extends Fragment> extends AndroidInjector<FragmentT> {

    abstract class Builder<FragmentT extends Fragment, ComponentT extends FragmentComponent<FragmentT>>
            extends AndroidInjector.Builder<FragmentT> {
        public abstract ComponentT build();
    }
}
