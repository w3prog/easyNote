package com.w3prog.easynote.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by w3prog on 31.07.14.
 */
public class eventBroadReseivers extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //TODO здесь добавлю код для автозапуска сервиса
        context.startService(new Intent(context, EventService.class));
    }
}
