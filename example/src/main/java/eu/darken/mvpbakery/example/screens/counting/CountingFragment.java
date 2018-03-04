package eu.darken.mvpbakery.example.screens.counting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.darken.mvpbakery.base.MVPBakery;
import eu.darken.mvpbakery.base.StateForwarder;
import eu.darken.mvpbakery.base.loader.LoaderRetainer;
import eu.darken.mvpbakery.example.R;
import eu.darken.mvpbakery.injection.InjectedPresenter;
import eu.darken.mvpbakery.injection.PresenterInjectionCallback;

public class CountingFragment extends Fragment implements CountingPresenter.View {

    @BindView(R.id.fragment_text) TextView textView;

    private final StateForwarder stateForwarder = new StateForwarder();
    private MVPBakery<CountingPresenter.View, CountingPresenter> mvpBakery;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_counting, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        stateForwarder.onCreate(savedInstanceState);
        mvpBakery = MVPBakery.<CountingPresenter.View, CountingPresenter>builder()
                .stateForwarder(stateForwarder)
                .presenterFactory(new InjectedPresenter<>(this))
                .presenterRetainer(new LoaderRetainer<>(this))
                .addPresenterCallback(new PresenterInjectionCallback<>(this))
                .attach(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        stateForwarder.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showText(String text) {
        textView.setText(text);
    }

    @OnClick(R.id.fragment_button)
    void onCountClick() {
        mvpBakery.getPresenter().onCountClick();
    }
}
