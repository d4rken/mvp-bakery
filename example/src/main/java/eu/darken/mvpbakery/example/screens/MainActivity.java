package eu.darken.mvpbakery.example.screens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.darken.mvpbakery.base.MVPBakery;
import eu.darken.mvpbakery.base.StateForwarder;
import eu.darken.mvpbakery.base.ViewModelRetainer;
import eu.darken.mvpbakery.example.R;
import eu.darken.mvpbakery.injection.ComponentSource;
import eu.darken.mvpbakery.injection.InjectedPresenter;
import eu.darken.mvpbakery.injection.ManualInjector;
import eu.darken.mvpbakery.injection.PresenterInjectionCallback;
import eu.darken.mvpbakery.injection.fragment.support.HasManualSupportFragmentInjector;


public class MainActivity extends AppCompatActivity implements MainPresenter.View, HasManualSupportFragmentInjector {

    @Inject ComponentSource<Fragment> componentSource;
    @Inject MainPresenter presenter;
    @BindView(R.id.container) ViewGroup container;
    @BindView(R.id.bindcounter) TextView bindCounter;

    private final StateForwarder stateForwarder = new StateForwarder();

    @Override
    public ManualInjector<Fragment> supportFragmentInjector() {
        return componentSource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stateForwarder.onCreate(savedInstanceState);
        MVPBakery.<MainPresenter.View, MainPresenter>builder()
                .stateForwarder(stateForwarder)
                .presenterFactory(new InjectedPresenter<>(this))
                .presenterRetainer(new ViewModelRetainer<>(this))
                .addPresenterCallback(new PresenterInjectionCallback<>(this))
                .attach(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        stateForwarder.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showFragment(Class<? extends Fragment> fragmentClass) {
        final Fragment oldFragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (!fragmentClass.isInstance(oldFragment)) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (oldFragment != null) transaction = transaction.detach(oldFragment);

            Fragment newFragment = getSupportFragmentManager().findFragmentByTag(fragmentClass.getName());
            if (newFragment != null) {
                transaction = transaction.attach(newFragment);
            } else {
                newFragment = Fragment.instantiate(this, fragmentClass.getName());
                transaction = transaction.add(R.id.container, newFragment, fragmentClass.getName());
            }
            transaction.commit();
        }
    }

    @Override
    public void showBinderCounter(int count) {
        bindCounter.setText(String.format(Locale.US, "Activity Rotation Count: %d", count));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.switch_fragment:
                presenter.switchFragments();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
