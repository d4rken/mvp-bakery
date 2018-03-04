package eu.darken.mvpbakery.injection.activity;

import android.app.Activity;

import dagger.android.AndroidInjector;

public interface ActivityComponent<ActivityT extends Activity> extends AndroidInjector<ActivityT> {

    abstract class Builder<ActivityT extends Activity, ComponentT extends ActivityComponent<ActivityT>> extends AndroidInjector.Builder<ActivityT> {
        public abstract ComponentT build();
    }
}
