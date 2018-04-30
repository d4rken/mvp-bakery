package eu.darken.mvpbakery.example.screens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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
import eu.darken.mvpbakery.injection.fragment.HasManualFragmentInjector;


public class MainActivity extends AppCompatActivity implements MainPresenter.View, HasManualFragmentInjector {

    @Inject ComponentSource<Fragment> componentSource;
    @Inject MainPresenter presenter;
    @BindView(R.id.container) ViewPager viewPager;
    @BindView(R.id.bindcounter) TextView bindCounter;
    @BindView(R.id.pageinfo) TextView pageInfo;

    private final StateForwarder stateForwarder = new StateForwarder();
    private PagerAdapter pagerAdapter;

    @Override
    public ManualInjector<Fragment> supportFragmentInjector() {
        return componentSource;
    }

    @Override
    protected void onCreate(Bundle si) {
        super.onCreate(si);
        stateForwarder.onCreate(si);
        MVPBakery.<MainPresenter.View, MainPresenter>builder()
                .stateForwarder(stateForwarder)
                .presenterFactory(new InjectedPresenter<>(this))
                .presenterRetainer(new ViewModelRetainer<>(this))
                .addPresenterCallback(new PresenterInjectionCallback<>(this))
                .attach(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), si != null ? si.getInt("pages", 0) : 0);
        viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                final Fragment fragment = pagerAdapter.getItem(position);
                pageInfo.setText(fragment.toString() + "\n");
                pageInfo.append(String.valueOf(position + 1) + "/" + pagerAdapter.getCount());

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("pages", pagerAdapter.getCount());
        stateForwarder.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showFragment(Class<? extends Fragment> fragmentClass) {
        pagerAdapter.addPage(true);
        viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
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
            case R.id.add_fragment:
                presenter.addFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
