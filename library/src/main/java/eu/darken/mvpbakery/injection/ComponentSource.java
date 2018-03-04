package eu.darken.mvpbakery.injection;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import dagger.android.AndroidInjector;

import static dagger.internal.Preconditions.checkNotNull;

public class ComponentSource<T> implements ManualInjector<T> {
    private final Map<Class<? extends T>, Provider<Factory<? extends T>>> injectorFactories;

    @Inject
    public ComponentSource(Map<Class<? extends T>, Provider<Factory<? extends T>>> injectorFactories) {
        this.injectorFactories = injectorFactories;
    }

    @Override
    public void inject(T instance) {
        get(instance).inject(instance);
    }

    @Override
    public AndroidInjector<T> get(T instance) {
        //noinspection SuspiciousMethodCalls
        Provider<AndroidInjector.Factory<? extends T>> factoryProvider = injectorFactories.get(instance.getClass());
        if (factoryProvider == null) {
            throw new IllegalArgumentException("No injector available for " + instance);
        }

        @SuppressWarnings("unchecked")
        AndroidInjector.Factory<T> factory = (AndroidInjector.Factory<T>) factoryProvider.get();

        return checkNotNull(
                factory.create(instance),
                "%s.create(I) should not return null.",
                factory.getClass().getCanonicalName());
    }
}
