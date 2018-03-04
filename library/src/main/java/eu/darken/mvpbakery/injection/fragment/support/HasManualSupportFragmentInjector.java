package eu.darken.mvpbakery.injection.fragment.support;

import android.support.v4.app.Fragment;

import dagger.android.support.HasSupportFragmentInjector;
import eu.darken.mvpbakery.injection.ManualInjector;

public interface HasManualSupportFragmentInjector extends HasSupportFragmentInjector {
    @Override
    ManualInjector<Fragment> supportFragmentInjector();
}
