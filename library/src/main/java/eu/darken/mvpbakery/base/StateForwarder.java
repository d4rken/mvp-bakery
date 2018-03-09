package eu.darken.mvpbakery.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

public class StateForwarder {
    @Nullable private Listener internalCallback;
    private Bundle inState;
    private Bundle outState;
    private boolean isRestoreConsumed = false;

    public void setListener(@Nullable Listener internalCallback) {
        this.internalCallback = internalCallback;
        if (internalCallback == null) return;
        if (inState != null) {
            isRestoreConsumed = internalCallback.onCreate(inState);
            if (isRestoreConsumed) inState = null;
        }
        if (outState != null) {
            internalCallback.onSaveInstanceState(outState);
            outState = null;
        }
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        isRestoreConsumed = internalCallback != null && internalCallback.onCreate(savedInstanceState);
        if (!isRestoreConsumed) this.inState = savedInstanceState;
    }

    public void onSaveInstanceState(Bundle outState) {
        if (internalCallback != null) {
            internalCallback.onSaveInstanceState(outState);
        } else {
            this.outState = outState;
        }
    }

    @Nullable
    public Bundle getInState() {
        return inState;
    }

    public boolean hasRestoreEvent() {
        return !isRestoreConsumed;
    }

    public interface Listener {

        /**
         * Call directly after {@link android.app.Activity#onCreate(Bundle)} or {@link android.app.Fragment#onCreate(Bundle)}
         *
         * @return true if the instance state was delivered or false if it should be persisted
         */
        boolean onCreate(@Nullable Bundle savedInstanceState);

        /**
         * Call before {@link android.app.Activity#onSaveInstanceState(Bundle)} or {@link android.app.Fragment#onSaveInstanceState(Bundle)}
         */
        void onSaveInstanceState(Bundle outState);
    }
}
