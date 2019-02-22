package eu.darken.mvpbakery.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

public interface StateListener {
    void onRestoreState(@Nullable Bundle inState);

    void onSaveState(Bundle outState);
}
