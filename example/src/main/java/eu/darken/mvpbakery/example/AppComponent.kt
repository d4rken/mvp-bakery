package eu.darken.mvpbakery.example


import android.app.Activity
import dagger.Component
import dagger.android.AndroidInjectionModule
import eu.darken.mvpbakery.injection.ComponentSource
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ActivityBinderModule::class, AndroidInjectionModule::class])
interface AppComponent {

    fun inject(application: ExampleApplication): ExampleApplication

    fun activityComponentSource(): ComponentSource<Activity>

    @Component.Builder
    interface Builder {
        fun appModule(module: AppModule): Builder

        fun build(): AppComponent
    }

}
