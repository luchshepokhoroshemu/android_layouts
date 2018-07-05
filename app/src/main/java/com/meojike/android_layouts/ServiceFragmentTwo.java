package com.meojike.android_layouts;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.meojike.android_layouts.FragmentTwo.MSG_REGISTER_CLIENT;
import static com.meojike.android_layouts.FragmentTwo.MSG_UNREGISTER_CLIENT;


public class ServiceFragmentTwo extends Service {

    private static final String TAG = "ServiceFragmentTwo";

    private List<Messenger> clientMessengers = new ArrayList<>();

    private Messenger serviceTwoMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage: setting service messenger");
            switch (msg.what) {
                case MSG_REGISTER_CLIENT:
                    clientMessengers.add(msg.replyTo);
                    break;

                case MSG_UNREGISTER_CLIENT:
                    clientMessengers.remove(msg.replyTo);
                    break;
            }
        }
    });

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: inside oncreate fragment 2");
        super.onCreate();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    Log.d(TAG, "run: yay running service for fragment 2");
                    long currentTimeMillis = System.currentTimeMillis();

                    Message message = new Message();
                    message.obj = currentTimeMillis;

                    if(!clientMessengers.isEmpty()) {
                        Iterator<Messenger> clientsIterator = clientMessengers.iterator();
                        while (clientsIterator.hasNext()) {
                            try {
                                clientsIterator.next().send(message);
                                stopSelf();
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: binded something");
        return serviceTwoMessenger.getBinder();
    }

    public static final Intent newIntent(Context context) {
        return new Intent(context, ServiceFragmentTwo.class);
    }


}
