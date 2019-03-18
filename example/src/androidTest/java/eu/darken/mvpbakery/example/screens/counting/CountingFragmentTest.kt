package eu.darken.mvpbakery.example.screens.counting

import androidx.fragment.app.Fragment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import dagger.android.AndroidInjector
import eu.darken.mvpbakery.example.R
import eu.darken.mvpbakery.injection.ManualInjector
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import testhelper.FragmentTestRule

class CountingFragmentTest {
    @get:Rule
    var fragmentRule = FragmentTestRule(CountingFragment::class.java)

    @MockK
    lateinit var presenter: CountingPresenter

    private val component = object : CountingComponent {
        override val presenter: CountingPresenter
            get() = this@CountingFragmentTest.presenter

        override fun inject(overviewFragment: CountingFragment) = Unit
    }

    private val injector = object : ManualInjector<Fragment> {
        @Suppress("UNCHECKED_CAST")
        override fun get(instance: Fragment): AndroidInjector<Fragment> {
            return component as AndroidInjector<Fragment>
        }

        override fun inject(fragment: Fragment) = Unit
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        fragmentRule.manualInjector = injector
        every { presenter.component } returns component
    }

    @After
    fun tearDown() {

    }

    @Test
    @Throws(Throwable::class)
    fun testShowText() {
        fragmentRule.launchActivity(null)
        onView(withId(R.id.fragment_button)).check(matches(withText("+1")))

        fragmentRule.runOnUiThread { fragmentRule.fragment.showText("Straw") }
        onView(withId(R.id.fragment_text)).check(matches(withText("Straw")))
        fragmentRule.runOnUiThread { fragmentRule.fragment.showText("berry") }
        onView(withId(R.id.fragment_text)).check(matches(withText("berry")))
    }

    @Test
    fun testCount() {
        fragmentRule.launchActivity(null)
        onView(withId(R.id.fragment_button)).check(matches(withText("+1")))
        onView(withId(R.id.fragment_button)).perform(click())
        verify { presenter.onCountClick() }
    }

}
