package eu.darken.mvpbakery.injection.fragment;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import androidx.fragment.app.Fragment;
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