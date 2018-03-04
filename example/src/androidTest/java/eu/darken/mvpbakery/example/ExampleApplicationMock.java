package eu.darken.mvpbakery.example;

import android.app.Activity;

import eu.darken.mvpbakery.injection.ManualInjector;

public class ExampleApplicationMock extends ExampleApplication {

    public void setActivityComponentSource(ManualInjector<Activity> injector) {
        this.manualInjector = injector;
    }
}
