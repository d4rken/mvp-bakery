package testhelper;

import junit.framework.Assert;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.test.rule.ActivityTestRule;
import eu.darken.mvpbakery.example.FragmentTestActivity;
import eu.darken.mvpbakery.injection.ManualInjector;

public class FragmentTestRule<FragmentT extends Fragment> extends ActivityTestRule<FragmentTestActivity> {

    private final Class<FragmentT> fragmentClass;
    private FragmentT fragment;
    ManualInjector<Fragment> manualInjector;


    public FragmentTestRule(final Class<FragmentT> fragmentClass) {
        super(FragmentTestActivity.class, true, false);
        this.fragmentClass = fragmentClass;
    }

    @Override
    protected void afterActivityLaunched() {
        super.afterActivityLaunched();

        getActivity().setManualInjector(manualInjector);

        getActivity().runOnUiThread(() -> {
            try {
                //Instantiate and insert the fragment into the container layout
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                fragment = fragmentClass.newInstance();
                //noinspection ResourceType
                transaction.replace(1, fragment);
                transaction.commitNow();
            } catch (InstantiationException | IllegalAccessException e) {
                Assert.fail(
                        String.format("%s: Could not insert %s into TestActivity: %s",
                                getClass().getSimpleName(),
                                fragmentClass.getSimpleName(),
                                e.getMessage())
                );
            }
        });
    }

    public FragmentT getFragment() {
        return fragment;
    }

    public void setManualInjector(ManualInjector<Fragment> manualInjector) {
        this.manualInjector = manualInjector;
    }
}