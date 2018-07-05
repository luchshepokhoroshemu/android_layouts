package com.meojike.android_layouts;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

public class ServiceFragmentThree extends Service {

    private static final String TAG = "ServiceFragmentThree";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: SERVICE THREE ONCREATED");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Intent intent = new Intent(FragmentThree.FRAGMENT_THREE_FILTER);
                    Random randomInt = new Random();

                    intent.putExtra("firstColor", Color.argb(randomInt.nextInt(256), randomInt.nextInt(256), randomInt.nextInt(256), randomInt.nextInt(256)));
                    intent.putExtra("secondColor", Color.argb(randomInt.nextInt(256), randomInt.nextInt(256), randomInt.nextInt(256), randomInt.nextInt(256)));
                    intent.putExtra("thirdColor", Color.argb(randomInt.nextInt(256), randomInt.nextInt(256), randomInt.nextInt(256), randomInt.nextInt(256)));

                    sendBroadcast(intent);

                    Log.d(TAG, "run: broadcast for third fragment was sent");
                    try {
                        Thread.currentThread().sleep(1000);
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
        return new Intent(context, ServiceFragmentThree.class);
    }
}
