package com.w3prog.easynote.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class eventBroadReseivers extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if("android.intent.action.BOOT_COMPLETED"
                .equals(intent.getAction()))
            context.startService(
                    new Intent(context, EventService.class));
    }
}
