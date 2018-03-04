package eu.darken.mvpbakery.injection.fragment;


import android.app.Fragment;

import dagger.android.HasFragmentInjector;
import eu.darken.mvpbakery.injection.ManualInjector;

public interface HasManualFragmentInjector extends HasFragmentInjector {
    @Override
    ManualInjector<Fragment> fragmentInjector();
}
