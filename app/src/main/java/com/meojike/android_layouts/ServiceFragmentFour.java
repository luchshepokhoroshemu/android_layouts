package com.meojike.android_layouts;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.meojike.android_layouts.FragmentFour.MSG_REGISTER_CLIENT;
import static com.meojike.android_layouts.FragmentFour.MSG_UNREGISTER_CLIENT;

public class ServiceFragmentFour extends Service {
    private static final String TAG = "ServiceFragmentFour";

    private float angle;

    private List<Messenger> clientMessengers = new ArrayList<>();
    private Messenger serviceFourMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage: handling service 4 message");
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
        super.onCreate();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    angle += 0.5;

                    Message message = new Message();
                    message.obj = angle;

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
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: binded fourth fragment");
        return serviceFourMessenger.getBinder();
    }

    public static final Intent newIntent(Context context) {
        return new Intent(context, ServiceFragmentFour.class);
    }
}
