package eu.darken.mvpbakery.injection;

import dagger.android.AndroidInjector;

public interface ManualInjector<T> extends AndroidInjector<T> {

    AndroidInjector<T> get(T instance);
}
