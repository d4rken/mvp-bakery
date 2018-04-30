package eu.darken.mvpbakery.example.screens.counting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import eu.darken.mvpbakery.base.Presenter;
import eu.darken.mvpbakery.base.StateListener;
import eu.darken.mvpbakery.injection.ComponentPresenter;
import timber.log.Timber;


@CountingComponent.Scope
public class CountingPresenter extends ComponentPresenter<CountingPresenter.View, CountingComponent> implements StateListener {

    private final Counter counter;

    @Inject
    CountingPresenter(Counter counter) {
        this.counter = counter;
    }

    void onCountClick() {
        if (getView() != null) getView().showText(String.valueOf(counter.countUp()));
    }

    @Override
    public void onRestoreState(@Nullable Bundle bundle) {
        if (bundle != null) counter.setCounter(bundle.getInt("counter"));
    }

    @Override
    public void onBindChange(@Nullable View view) {
        super.onBindChange(view);
        if (view != null) view.showText(String.valueOf(counter.getCurrent()));
        onView(v -> v.showPresenterInfo(this));
    }

    @Override
    public void onSaveState(@NonNull Bundle bundle) {
        bundle.putInt("counter", counter.getCurrent());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy()");
    }

    public interface View extends Presenter.View {
        void showText(String text);

        void showPresenterInfo(CountingPresenter presenter);
    }
}
