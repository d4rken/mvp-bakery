package eu.darken.mvpbakery.example.screens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.darken.mvpbakery.base.MVPBakery;
import eu.darken.mvpbakery.base.StateForwarder;
import eu.darken.mvpbakery.base.viewmodel.ViewModelRetainer;
import eu.darken.mvpbakery.example.R;
import eu.darken.mvpbakery.injection.ComponentSource;
import eu.darken.mvpbakery.injection.InjectedPresenter;
import eu.darken.mvpbakery.injection.ManualInjector;
import eu.darken.mvpbakery.injection.PresenterInjectionCallback;
import eu.darken.mvpbakery.injection.fragment.support.HasManualSupportFragmentInjector;


public class MainActivity extends AppCompatActivity implements MainPresenter.View, HasManualSupportFragmentInjector {

    @Inject ComponentSource<Fragment> componentSource;

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
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment == null) {
            fragment = Fragment.instantiate(this, fragmentClass.getName());
            getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commitNow();
        }
    }

    @Override
    public void showBinderCounter(int count) {
        bindCounter.setText(String.format(Locale.US, "Activity Rotation Count: %d", count));
    }
}
