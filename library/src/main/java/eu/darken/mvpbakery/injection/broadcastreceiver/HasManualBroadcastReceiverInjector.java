package eu.darken.mvpbakery.injection.broadcastreceiver;


import android.content.BroadcastReceiver;

import dagger.android.HasBroadcastReceiverInjector;
import eu.darken.mvpbakery.injection.ManualInjector;

public interface HasManualBroadcastReceiverInjector extends HasBroadcastReceiverInjector {
    @Override
    ManualInjector<BroadcastReceiver> broadcastReceiverInjector();
}
