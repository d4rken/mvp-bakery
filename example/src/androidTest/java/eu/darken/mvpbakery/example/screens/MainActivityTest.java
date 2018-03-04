package eu.darken.mvpbakery.example.screens;


import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import dagger.android.AndroidInjector;
import eu.darken.mvpbakery.example.ExampleApplicationMock;
import eu.darken.mvpbakery.example.R;
import eu.darken.mvpbakery.example.screens.counting.CountingComponent;
import eu.darken.mvpbakery.example.screens.counting.CountingFragment;
import eu.darken.mvpbakery.example.screens.counting.CountingPresenter;
import eu.darken.mvpbakery.injection.ComponentSource;
import eu.darken.mvpbakery.injection.ManualInjector;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class, true, false);

    ExampleApplicationMock app;


    @Mock MainPresenter mainPresenter;
    @Mock MainComponent mainComponent;

    @Mock ComponentSource<Fragment> fragmentInjector;
    @Mock CountingPresenter countingPresenter;
    @Mock CountingComponent countingComponent;

    @Before
    public void setUp() {
        app = (ExampleApplicationMock) InstrumentationRegistry.getTargetContext().getApplicationContext();
        app.setActivityComponentSource(new ActivityInjector());

        doAnswer(invocation -> {
            MainActivity mainActivity = invocation.getArgument(0);
            mainActivity.componentSource = fragmentInjector;
            return null;
        }).when(mainComponent).inject(any());
        when(mainComponent.getPresenter()).thenReturn(mainPresenter);
        when(mainPresenter.getComponent()).thenReturn(mainComponent);


        doAnswer(invocation -> {
            // Nothing to inject atm
            return null;
        }).when(countingComponent).inject(any());
        when(fragmentInjector.get(any())).then(invocation -> countingComponent);
        when(countingComponent.getPresenter()).thenReturn(countingPresenter);
        when(countingPresenter.getComponent()).thenReturn(countingComponent);
    }

    public class ActivityInjector implements ManualInjector<Activity> {

        @Override
        public AndroidInjector get(Activity instance) {
            return mainComponent;
        }

        @Override
        public void inject(Activity instance) {
            mainComponent.inject((MainActivity) instance);
        }

    }

    @Test
    public void checkFragmentShowing() throws Throwable {
        activityRule.launchActivity(null);

        activityRule.runOnUiThread(() -> activityRule.getActivity().showFragment(CountingFragment.class));
        onView(withId(R.id.fragment_button)).check(matches(withText("+1")));
    }

    @Test
    public void checkCounter() throws Throwable {
        activityRule.launchActivity(null);

        onView(withId(R.id.bindcounter)).check(matches(withText("Activity Rotation Count: 0")));

        activityRule.runOnUiThread(() -> activityRule.getActivity().showBinderCounter(1));
        onView(withId(R.id.bindcounter)).check(matches(withText("Activity Rotation Count: 1")));

        activityRule.runOnUiThread(() -> activityRule.getActivity().showBinderCounter(2));
        onView(withId(R.id.bindcounter)).check(matches(withText("Activity Rotation Count: 2")));
    }
}
