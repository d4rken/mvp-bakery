package eu.darken.mvpbakery.injection.broadcastreceiver;

import android.content.BroadcastReceiver;

import dagger.android.AndroidInjector;

public interface BroadcastReceiverComponent<ReceiverT extends BroadcastReceiver> extends AndroidInjector<ReceiverT> {

    abstract class Builder<ReceiverT extends BroadcastReceiver, ComponentT extends BroadcastReceiverComponent<ReceiverT>>
            extends AndroidInjector.Builder<ReceiverT> {
        public abstract ComponentT build();
    }
}
