package eu.darken.mvpbakery.example.screens;

import android.os.Bundle;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
        if (savedInstanceState != null) doInit = savedInstanceState.getBoolean("doInit");
        if (savedInstanceState != null) bindCounter = savedInstanceState.getInt("counter");
        if (savedInstanceState != null) fragmentNo = savedInstanceState.getInt("fragmentNo");
    }

    @Override
    public void onSaveState(@NonNull Bundle outState) {
        outState.putBoolean("doInit", doInit);
        outState.putInt("counter", bindCounter);
        outState.putInt("fragmentNo", fragmentNo);
    }

    @Override
    public void onBindChange(@Nullable View view) {
        super.onBindChange(view);
        onView(v -> v.showBinderCounter(++bindCounter));
        onView(v -> {
            if (doInit) {
                doInit = false;
                v.addFragment(startingFragment);
            } else {
                int oldFrags = fragmentNo;
                fragmentNo = 0;
                for (int i = 0; i < oldFrags; i++) {
                    addNewFragment();
                }
            }
        });
    }

    private void addFragment(int pos) {
        switch (pos % 2) {
            case 0:
                onView(v -> v.addFragment(CountingFragment.class));
                break;
            case 1:
                onView(v -> v.addFragment(TextFragment.class));
                break;
        }
    }

    public void addNewFragment() {
        addFragment(fragmentNo++);
    }

    public interface View extends Presenter.View {
        void showBinderCounter(int count);

        void addFragment(Class<? extends Fragment> fragmentClass);
    }
}
