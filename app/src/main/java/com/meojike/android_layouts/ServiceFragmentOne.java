package com.meojike.android_layouts;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

public class ServiceFragmentOne extends Service {

    private static final String TAG = "ServiceFragmentOne";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: SERVICEFRAGMENT1 ONCREATED");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Intent intent = new Intent(FragmentOne.FRAGMENT_ONE_FILTER);
                    Random randomInt = new Random();

                    intent.putExtra("firstColor", Color.argb(255, randomInt.nextInt(256), randomInt.nextInt(256), randomInt.nextInt(256)));
                    intent.putExtra("secondColor", Color.argb(255, randomInt.nextInt(256), randomInt.nextInt(256), randomInt.nextInt(256)));
                    intent.putExtra("thirdColor", Color.argb(255, randomInt.nextInt(256), randomInt.nextInt(256), randomInt.nextInt(256)));

                    sendBroadcast(intent);

                    Log.d(TAG, "run: we've sent a broadcast!");
                    try {
                        Thread.currentThread().sleep(3500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        stopSelf();
                    }
                }
            }

        }).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static final Intent newIntent(Context context) {
        return new Intent(context, ServiceFragmentOne.class);
    }
}
