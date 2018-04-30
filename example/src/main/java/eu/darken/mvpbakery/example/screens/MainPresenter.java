package eu.darken.mvpbakery.example.screens;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import eu.darken.mvpbakery.base.Presenter;
import eu.darken.mvpbakery.base.StateListener;
import eu.darken.mvpbakery.example.screens.counting.CountingFragment;
import eu.darken.mvpbakery.example.screens.text.TextFragment;
import eu.darken.mvpbakery.injection.ComponentPresenter;


@MainComponent.Scope
public class MainPresenter extends ComponentPresenter<MainPresenter.View, MainComponent> implements StateListener {

    private final Class<? extends Fragment> startingFragment;
    private boolean doInit = true;
    private int bindCounter = 0;
    private int fragmentNo = 1;

    @Inject
    MainPresenter(Class<? extends Fragment> startingFragment) {
        this.startingFragment = startingFragment;
    }

    @Override
    public void onRestoreState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) bindCounter = savedInstanceState.getInt("counter");
    }

    @Override
    public void onSaveState(@NonNull Bundle outState) {
        outState.putInt("counter", bindCounter);
    }

    @Override
    public void onBindChange(@Nullable View view) {
        super.onBindChange(view);
        onView(v -> v.showBinderCounter(++bindCounter));
        onView(v -> {
            if (doInit) {
                doInit = false;
                v.showFragment(startingFragment);
            }
        });
    }

    public void addFragment() {
        fragmentNo = fragmentNo == 1 ? 2 : 1;

        switch (fragmentNo) {
            case 1:
                onView(v -> v.showFragment(CountingFragment.class));
                break;
            case 2:
                onView(v -> v.showFragment(TextFragment.class));
                break;
        }
    }

    public interface View extends Presenter.View {
        void showBinderCounter(int count);

        void showFragment(Class<? extends Fragment> fragmentClass);
    }
}
