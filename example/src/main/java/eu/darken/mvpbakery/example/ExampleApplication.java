package eu.darken.mvpbakery.example;

import android.app.Activity;
import android.app.Application;
import android.os.StrictMode;

import com.squareup.leakcanary.LeakCanary;

import eu.darken.mvpbakery.injection.ManualInjector;
import eu.darken.mvpbakery.injection.activity.HasManualActivityInjector;
import timber.log.Timber;


public class ExampleApplication extends Application implements HasManualActivityInjector {

    AppComponent appComponent;
    ManualInjector<Activity> manualInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                .detectAll() //
                .penaltyLog() //
                .penaltyDeath() //
                .build());
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
