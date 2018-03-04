package eu.darken.mvpbakery.injection.activity;

import android.app.Activity;

import dagger.android.HasActivityInjector;
import eu.darken.mvpbakery.injection.ManualInjector;

public interface HasManualActivityInjector extends HasActivityInjector {
    @Override
    ManualInjector<Activity> activityInjector();
}
