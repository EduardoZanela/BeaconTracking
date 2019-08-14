package com.eduardozanela.beacondetector.interfaces;

import com.google.android.gms.nearby.messages.Distance;
import com.google.android.gms.nearby.messages.Message;

public interface BeaconFinderObserver {
    void onBeaconLost(Message message);
    void onBeaconFound(Message message);
    void onBeaconChangeDistance(Message message, Distance distance);
    void onError(Throwable throwable);
}
