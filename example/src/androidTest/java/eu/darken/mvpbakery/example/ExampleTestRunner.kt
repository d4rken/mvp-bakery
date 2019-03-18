package eu.darken.mvpbakery.example

import android.app.Application
import android.content.Context

import androidx.test.runner.AndroidJUnitRunner


class ExampleTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, ExampleApplicationMock::class.java.name, context)
    }
}