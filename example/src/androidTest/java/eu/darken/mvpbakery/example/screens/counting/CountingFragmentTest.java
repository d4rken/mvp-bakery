package eu.darken.mvpbakery.example.screens.counting;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjector;
import eu.darken.mvpbakery.example.R;
import eu.darken.mvpbakery.injection.ManualInjector;
import testhelper.FragmentTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CountingFragmentTest {
    @Rule public FragmentTestRule<CountingFragment> fragmentRule = new FragmentTestRule<>(CountingFragment.class);

    @Mock CountingPresenter presenter;

    private CountingComponent component = new CountingComponent() {
        @Override
        public CountingPresenter getPresenter() {
            return presenter;
        }

        @Override
        public void inject(CountingFragment overviewFragment) {

        }
    };

    private ManualInjector<Fragment> injector = new ManualInjector<Fragment>() {
        @Override
        public AndroidInjector get(Fragment fragment) {
            return component;
        }

        @Override
        public void inject(Fragment fragment) {

        }
    };

    @Before
    public void setUp() {
        doAnswer(invocationOnMock -> null).when(presenter).onBindChange(any());
        when(presenter.getComponent()).thenReturn(component);
        fragmentRule.setManualInjector(injector);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testShowText() throws Throwable {
        fragmentRule.launchActivity(null);
        onView(withId(R.id.fragment_button)).check(matches(withText("+1")));

        fragmentRule.runOnUiThread(() -> fragmentRule.getFragment().showText("Straw"));
        onView(withId(R.id.fragment_text)).check(matches(withText("Straw")));
        fragmentRule.runOnUiThread(() -> fragmentRule.getFragment().showText("berry"));
        onView(withId(R.id.fragment_text)).check(matches(withText("berry")));
    }

    @Test
    public void testCount() {
        fragmentRule.launchActivity(null);
        onView(withId(R.id.fragment_button)).check(matches(withText("+1")));

        onView(withId(R.id.fragment_button)).perform(click());
        verify(presenter).onCountClick();
    }

}
