package eu.darken.mvpbakery.example;

import android.app.Activity;
import android.app.Application;

import eu.darken.mvpbakery.injection.ManualInjector;
import eu.darken.mvpbakery.injection.activity.HasManualActivityInjector;
import timber.log.Timber;


public class ExampleApplication extends Application implements HasManualActivityInjector {

    AppComponent appComponent;
    ManualInjector<Activity> manualInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        manualInjector = appComponent.activityComponentSource();
    }

    @Override
    public ManualInjector<Activity> activityInjector() {
        return manualInjector;
    }
}
