# MVP-Bakery
[ ![Download](https://api.bintray.com/packages/darken/maven/mvp-bakery/images/download.svg) ](https://bintray.com/darken/maven/mvp-bakery/_latestVersion)
[![Coverage Status](https://coveralls.io/repos/github/d4rken/mvp-bakery/badge.svg)](https://coveralls.io/github/d4rken/mvp-bakery)
[![Build Status](https://travis-ci.org/d4rken/mvp-bakery.svg?branch=master)](https://travis-ci.org/d4rken/mvp-bakery)

MVP-Bakery helps you structure your Android app to implement MVP principles and deals with retaining the presenter across configuration (e.g. rotation) changes.

It is the successor to [OMMVPLib](https://github.com/d4rken/ommvplib)

MVP-Bakery can use a `Loader` or a `ViewModel` to store the `Presenter`.

Checkout the demo application!

## Quickstart
Add the library:
```groovy
implementation 'eu.darken.mvp-bakery:library:0.3.0'
```

### Without Dagger
The `Presenter` and the `View` that our `Activity` will implement.
```java
@MainComponent.Scope
public class MainPresenter extends Presenter<MainPresenter.View, MainComponent> {

    @Inject
    MainPresenter() {
    }

    @Override
    public void onBindChange(@Nullable View view) {
        super.onBindChange(view);
        onView(v -> doSomething());
    }

    public interface View extends Presenter.View {
        void doSomething();
    }
}

```

When MVP-Bakery is `attach`ed it will handle all the lifecycle events for you.
```java
public class MainActivity extends AppCompatActivity implements MainPresenter.View {

    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MVPBakery.<MainPresenter.View, MainPresenter>builder()
                .addPresenterCallback(new PresenterRetainer.Callback<MainPresenter.View, MainPresenter>() {
                    @Override
                    public void onPresenterCreated(MainPresenter presenter) {
                        
                    }

                    @Override
                    public void onPresenterDestroyed() {

                    }
                })
                .presenterFactory(new PresenterFactory<MainPresenter>() {
                    @Override
                    public MainPresenter createPresenter() {
                        return null;
                    }
                })
                .presenterRetainer(new ViewModelRetainer<>(this))
                .attach(this);
        setContentView(R.layout.activity_main);
    }
}
```

### With Dagger
The component for our injection.
```java
@MainComponent.Scope
@Subcomponent(modules = {AndroidSupportInjectionModule.class})
public interface MainComponent extends ActivityComponent<MainActivity>, PresenterComponent<MainPresenter.View, MainPresenter> {
    
    @Subcomponent.Builder
    abstract class Builder extends ActivityComponent.Builder<MainActivity, MainComponent> {}
    
    @javax.inject.Scope
    @Retention(RetentionPolicy.RUNTIME)
    @interface Scope {}
}
```

The `Presenter` that will be injected, including the `View` that our `Activity` will implement.

```java
@MainComponent.Scope
public class MainPresenter extends ComponentPresenter<MainPresenter.View, MainComponent> {

    @Inject
    MainPresenter() {
    }

    @Override
    public void onBindChange(@Nullable View view) {
        super.onBindChange(view);
        onView(v -> doSomething());
    }

    public interface View extends Presenter.View {
        void doSomething();
    }
}

```

Now we just need to attach the presenter.

```java
public class MainActivity extends AppCompatActivity implements MainPresenter.View {
    
    @Inject MainPresenter presenter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MVPBakery.<MainPresenter.View, MainPresenter>builder()
                .addPresenterCallback(new PresenterInjectionCallback<>(this))
                .presenterFactory(new InjectedPresenter<>(this))
                .presenterRetainer(new ViewModelRetainer<>(this))
                .attach(this);
        setContentView(R.layout.activity_main);
    }
}
```


## Acknowledgements
This library combines multiple concepts: 

* [tomorrow-mvp](https://github.com/michal-luszczuk/tomorrow-mvp) by Michał Łuszczuk
* [toegether-mvp](https://github.com/laenger/together-mvp) by Christian Langer
* [activities-multibinding-in-dagger-2](http://frogermcs.github.io/activities-multibinding-in-dagger-2/) by Miroslaw Stanek
* [Dagger2-MultiBinding-Android-Example](https://github.com/trevjonez/Dagger2-MultiBinding-Android-Example) by Trevor Jones
* [MCE3 Dagger 2 Talk](https://www.youtube.com/watch?v=iwjXqRlEevg) by Gregory Kick

Check out [@luszczuk's](https://twitter.com/luszczuk) (author of tomorrow-mvp) [comparison of Android MVP approaches](http://blog.propaneapps.com/android/mvp-for-android/).
