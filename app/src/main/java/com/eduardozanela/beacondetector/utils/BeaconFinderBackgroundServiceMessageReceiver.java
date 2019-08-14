package com.eduardozanela.beacondetector.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

public class BeaconFinderBackgroundServiceMessageReceiver extends BroadcastReceiver {

    public static String BEACON_ACTION = "beacon";

    public static String EVENT_FOUND = "found";
    public static String EVENT_LOST = "lost";

    @Override
    public void onReceive(final Context context, Intent intent) {

        Nearby.getMessagesClient(context).handleIntent(intent, new MessageListener() {
            @Override
            public void onFound(Message message) {
                Intent intent = new Intent();
                intent.setAction(BEACON_ACTION);
                intent.putExtra("message", new String(message.getContent()));
                intent.putExtra("event", EVENT_FOUND);
                System.out.println("BEACON MESSAGE TESTE: " + message.getContent());
                context.sendBroadcast(intent);
            }

            @Override
            public void onLost(Message message) {
                Intent intent = new Intent();
                intent.setAction(BEACON_ACTION);
                intent.putExtra("message", new String(message.getContent()));
                intent.putExtra("event", EVENT_LOST);
                context.sendBroadcast(intent);
            }
        });
    }
}
