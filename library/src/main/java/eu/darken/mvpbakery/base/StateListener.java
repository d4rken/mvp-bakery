package eu.darken.mvpbakery.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

public interface StateListener {
    void onRestoreState(@Nullable Bundle inState);

    void onSaveState(Bundle outState);
}
