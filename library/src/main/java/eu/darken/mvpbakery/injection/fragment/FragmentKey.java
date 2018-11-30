package eu.darken.mvpbakery.injection.fragment;

import android.support.v4.app.Fragment;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import dagger.MapKey;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(METHOD)
@Retention(RUNTIME)
@MapKey
public @interface FragmentKey {
    Class<? extends Fragment> value();
}