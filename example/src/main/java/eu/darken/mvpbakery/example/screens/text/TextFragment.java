package eu.darken.mvpbakery.example.screens.text;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.darken.mvpbakery.base.MVPBakery;
import eu.darken.mvpbakery.base.StateForwarder;
import eu.darken.mvpbakery.base.ViewModelRetainer;
import eu.darken.mvpbakery.example.R;
import eu.darken.mvpbakery.injection.InjectedPresenter;
import eu.darken.mvpbakery.injection.PresenterInjectionCallback;

public class TextFragment extends Fragment implements TextPresenter.View {

    @BindView(R.id.text_input) EditText textInput;
    @BindView(R.id.presenter_info) TextView presenterInfo;

    private final StateForwarder stateForwarder = new StateForwarder();
    @Inject TextPresenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_text, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        stateForwarder.onCreate(savedInstanceState);
        MVPBakery.<TextPresenter.View, TextPresenter>builder()
                .stateForwarder(stateForwarder)
                .presenterFactory(new InjectedPresenter<>(this))
                .presenterRetainer(new ViewModelRetainer<>(this))
                .addPresenterCallback(new PresenterInjectionCallback<>(this))
                .attach(this);
        textInput.setSaveEnabled(false);
        textInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.onTextInput(s.toString());
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        stateForwarder.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showText(String text) {
        textInput.setText(text);
    }

    @Override
    public void showPresenterInfo(TextPresenter presenter) {
        presenterInfo.setText(presenter.toString());
    }
}
