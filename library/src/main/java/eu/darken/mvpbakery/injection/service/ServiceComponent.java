package eu.darken.mvpbakery.injection.service;

import android.app.Service;

import dagger.android.AndroidInjector;

public interface ServiceComponent<ServiceT extends Service> extends AndroidInjector<ServiceT> {

    abstract class Builder<ServiceT extends Service, ComponentT extends ServiceComponent<ServiceT>>
            extends AndroidInjector.Builder<ServiceT> {
        public abstract ComponentT build();
    }
}
