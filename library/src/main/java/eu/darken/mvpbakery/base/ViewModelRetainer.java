package eu.darken.mvpbakery.base;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class ViewModelRetainer<ViewT extends Presenter.View, PresenterT extends Presenter<ViewT>> implements PresenterRetainer<ViewT, PresenterT> {
    final ContainerRepo repo;
    @Nullable StateForwarder stateForwarder;
    Container<ViewT, PresenterT> container;
    PresenterFactory<PresenterT> presenterFactory;

    public ViewModelRetainer(AppCompatActivity appCompatActivity) {
        repo = ViewModelProviders.of(appCompatActivity, ContainerRepo.FACTORY).get(ContainerRepo.class);
    }

    public ViewModelRetainer(Fragment supportFragment) {
        repo = ViewModelProviders.of(supportFragment, ContainerRepo.FACTORY).get(ContainerRepo.class);
    }

    static class ContainerRepo extends ViewModel {
        private final Map<Object, Container> containerMap = new HashMap<>();

        public <T> T get(Object key) {
            //noinspection unchecked
            return (T) containerMap.get(key);
        }

        public void put(Object key, Container item) {
            containerMap.put(key, item);
        }

        @Override
        protected void onCleared() {
            for (Map.Entry<Object, Container> entry : containerMap.entrySet()) {
                entry.getValue().destroy();
            }
            containerMap.clear();
            super.onCleared();
        }

        static final ViewModelProvider.Factory FACTORY = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                try {
                    return modelClass.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @Override
    public void setPresenterFactory(PresenterFactory<PresenterT> presenterFactory) {
        this.presenterFactory = presenterFactory;
    }

    @Override
    public void setStateForwarder(@Nullable StateForwarder stateForwarder) {
        this.stateForwarder = stateForwarder;
        if (stateForwarder != null) this.stateForwarder.setListener(new DefaultStateListener(this));
    }

    @Override
    public void attach(LifecycleOwner lifecycleOwner, Callback<ViewT, PresenterT> callback) {
        final Object key = getKey(lifecycleOwner);

        container = repo.get(key);

        if (container == null || container.getObserver() == null) {
            final LifecycleObserver observer = new DefaultLifecycleObserver() {
                boolean delayedInit = false;

                void tryInitialization(boolean isDelayedInit) {
                    if (container == null) {
                        final PresenterFactory.FactoryResult<PresenterT> result = presenterFactory.createPresenter();
                        if (result.retry) {
                            if (isDelayedInit) {
                                throw new IllegalStateException("No presenter after final init attempt.");
                            } else {
                                delayedInit = true;
                                return;
                            }
                        }

                        container = new Container<>(result.presenter);
                        repo.put(key, container);
                    }

                    container.setObserver(this);

                    if (stateForwarder != null && stateForwarder.hasRestoreEvent() && getPresenter() instanceof StateListener) {
                        ((StateListener) getPresenter()).onRestoreState(stateForwarder.getInState());
                    }

                    callback.onPresenterAvailable(getPresenter());
                }

                @Override
                public void onCreate(@NonNull LifecycleOwner owner) {
                    tryInitialization(false);
                }

                @Override
                public void onStart(@NonNull LifecycleOwner owner) {
                    if (delayedInit) {
                        delayedInit = false;
                        tryInitialization(true);
                    }
                    //noinspection unchecked, ConstantConditions
                    getPresenter().onBindChange((ViewT) owner);
                }

                @Override
                public void onResume(@NonNull LifecycleOwner owner) {

                }

                @Override
                public void onPause(@NonNull LifecycleOwner owner) {

                }

                @Override
                public void onStop(@NonNull LifecycleOwner owner) {
                    //noinspection ConstantConditions
                    getPresenter().onBindChange(null);
                }

                @Override
                public void onDestroy(@NonNull LifecycleOwner owner) {
                    if (stateForwarder != null) stateForwarder.setListener(null);
                    container.setObserver(null);
                    owner.getLifecycle().removeObserver(this);
                }
            };
            lifecycleOwner.getLifecycle().addObserver(observer);
        }
    }

    static String getKey(LifecycleOwner owner) {
        return owner.getClass().getCanonicalName() + ".MVPBakery.Container." + "Default";
    }

    @Nullable
    @Override
    public PresenterT getPresenter() {
        if (container == null) return null;
        return container.getPresenter();
    }

    static class Container<ViewT extends Presenter.View, PresenterT extends Presenter<ViewT>> {
        private final PresenterT presenter;
        @Nullable private LifecycleObserver observer;

        Container(PresenterT presenter) {
            this.presenter = presenter;
        }

        public PresenterT getPresenter() {
            return presenter;
        }

        public void destroy() {
            presenter.onDestroy();
        }

        @Nullable
        public LifecycleObserver getObserver() {
            return observer;
        }

        public void setObserver(@Nullable LifecycleObserver observer) {
            this.observer = observer;
        }
    }
}
