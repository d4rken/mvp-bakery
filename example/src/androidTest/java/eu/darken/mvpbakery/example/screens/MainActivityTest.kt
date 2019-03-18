package eu.darken.mvpbakery.example.screens


import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import dagger.android.AndroidInjector
import eu.darken.mvpbakery.example.ExampleApplicationMock
import eu.darken.mvpbakery.example.R
import eu.darken.mvpbakery.example.screens.counting.CountingComponent
import eu.darken.mvpbakery.example.screens.counting.CountingFragment
import eu.darken.mvpbakery.example.screens.counting.CountingPresenter
import eu.darken.mvpbakery.injection.ComponentSource
import eu.darken.mvpbakery.injection.ManualInjector
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    private lateinit var app: ExampleApplicationMock

    @MockK
    lateinit var mainPresenter: MainPresenter
    @MockK
    lateinit var mainComponent: MainComponent

    @MockK
    lateinit var fragmentInjector: ComponentSource<Fragment>
    @MockK
    lateinit var countingPresenter: CountingPresenter
    @MockK
    lateinit var countingComponent: CountingComponent

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        app = ApplicationProvider.getApplicationContext()
        app.setActivityComponentSource(ActivityInjector())

        every { mainComponent.inject(any()) } answers {
            val mainActivity = firstArg<MainActivity>()
            mainActivity.componentSource = fragmentInjector
            mainActivity.presenter = mainPresenter
        }

        every { mainComponent.presenter } returns (mainPresenter)
        every { mainPresenter.component } returns (mainComponent)

        every { countingComponent.inject(any()) } answers { }
        @Suppress("UNCHECKED_CAST")
        every { fragmentInjector[any()] } returns (countingComponent as AndroidInjector<Fragment>)
        every { countingComponent.presenter } returns (countingPresenter)
        every { countingPresenter.component } returns (countingComponent)
    }

    inner class ActivityInjector : ManualInjector<Activity> {
        @Suppress("UNCHECKED_CAST")
        override fun get(instance: Activity): AndroidInjector<Activity> = mainComponent as AndroidInjector<Activity>

        override fun inject(instance: Activity) = mainComponent.inject(instance as MainActivity)
    }

    @Test
    fun checkFragmentShowing() {
        activityRule.launchActivity(null)

        activityRule.runOnUiThread { activityRule.activity.addFragment(CountingFragment::class.java) }

        onView(withId(R.id.fragment_button)).check(matches(withText("+1")))
    }

    @Test
    fun checkCounter() {
        activityRule.launchActivity(null)

        onView(withId(R.id.bindcounter)).check(matches(withText("Activity Rotation Count: 0")))

        activityRule.runOnUiThread { activityRule.activity.showBinderCounter(1) }
        onView(withId(R.id.bindcounter)).check(matches(withText("Activity Rotation Count: 1")))

        activityRule.runOnUiThread { activityRule.activity.showBinderCounter(2) }
        onView(withId(R.id.bindcounter)).check(matches(withText("Activity Rotation Count: 2")))
    }
}
