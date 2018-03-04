package eu.darken.mvpbakery.injection.service;


import android.app.Service;

import dagger.android.HasServiceInjector;
import eu.darken.mvpbakery.injection.ManualInjector;

public interface HasManualServiceInjector extends HasServiceInjector {
    @Override
    ManualInjector<Service> serviceInjector();
}
