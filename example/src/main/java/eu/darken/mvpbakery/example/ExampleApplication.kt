package eu.darken.mvpbakery.example

import android.app.Activity
import android.app.Application
import android.os.StrictMode

import com.squareup.leakcanary.LeakCanary

import eu.darken.mvpbakery.injection.ManualInjector
import eu.darken.mvpbakery.injection.activity.HasManualActivityInjector
import timber.log.Timber


open class ExampleApplication : Application(), HasManualActivityInjector {

    lateinit var appComponent: AppComponent
    lateinit var manualInjector: ManualInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
        // Normal app init code...
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder() //
                .detectAll() //
                .penaltyLog() //
                .build())
        Timber.plant(Timber.DebugTree())
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

        manualInjector = appComponent.activityComponentSource()
    }

    override fun activityInjector(): ManualInjector<Activity> {
        return manualInjector
    }
}
