package eu.darken.mvpbakery.injection.fragment;

import androidx.fragment.app.Fragment;
import dagger.android.support.HasSupportFragmentInjector;
import eu.darken.mvpbakery.injection.ManualInjector;

public interface HasManualFragmentInjector extends HasSupportFragmentInjector {
    @Override
    ManualInjector<Fragment> supportFragmentInjector();
}
