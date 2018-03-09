package eu.darken.mvpbakery.injection.fragment;

import android.support.v4.app.Fragment;

import dagger.android.support.HasSupportFragmentInjector;
import eu.darken.mvpbakery.injection.ManualInjector;

public interface HasManualFragmentInjector extends HasSupportFragmentInjector {
    @Override
    ManualInjector<Fragment> supportFragmentInjector();
}
