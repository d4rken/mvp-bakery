package eu.darken.mvpbakery.example

import android.app.Activity

import eu.darken.mvpbakery.injection.ManualInjector

class ExampleApplicationMock : ExampleApplication() {

    fun setActivityComponentSource(injector: ManualInjector<Activity>) {
        this.manualInjector = injector
    }
}
