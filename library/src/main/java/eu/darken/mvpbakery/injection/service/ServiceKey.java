package eu.darken.mvpbakery.injection.service;

import android.app.Service;

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
public @interface ServiceKey {
    Class<? extends Service> value();
}