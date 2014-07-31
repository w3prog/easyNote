package com.w3prog.easynote.model;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.ArrayList;

/**
 * Created by w3prog on 31.07.14.
 */
public class EventService extends Service {

    ArrayList<Event> events;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        //
        //  Загрузить данные из коллекции
        //      Запрос селект на базу данных
        //      Определение заявок активных заявок можно получить здесь же через это запрос банальным фильтром
        //
        //  Получить заявку на оповещения с датой
        //      Вопрос где они будут храниться и как их обрабатывать
        //  Ожидать времени для выполнения заявки
        //      Вечный цикл или как??
        //

        return super.onStartCommand(intent, flags, startId);
    }
}
