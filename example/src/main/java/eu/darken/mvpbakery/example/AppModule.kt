package eu.darken.mvpbakery.example

import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: ExampleApplication) {

    @Provides
    fun provideApplication(): ExampleApplication {
        return application
    }

}
