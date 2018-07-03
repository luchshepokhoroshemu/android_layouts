package com.meojike.android_layouts;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;


public class FragmentTwo extends Fragment {
    private static final String TAG = "FragmentTwo";

    public static final int MSG_REGISTER_CLIENT  = 0xFEED;
    public static final int MSG_UNREGISTER_CLIENT = 0xDEAD;

    private TextView textViewOne;
    private TextView textViewTwo;
    private TextView textViewThree;
    private TextView textViewFour;
    private TextView textViewFive;
    private TextView textViewSix;
    private TextView textViewSeven;
    private TextView textViewEight;
    private TextView textViewNine;

    private ServiceConnection serviceConnectionFragmentTwo;

    private Messenger serviceTwoMessenger;
    private Messenger fragmentTwoMessenger;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_two, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: initiating buttons and fragments and service");

        initButtons(view);
        setFragmentMessenger();
        setServiceConnection();

    }

    @Override
    public void onResume() {
        super.onResume();
        getContext().bindService(ServiceFragmentTwo.newIntent(getContext()), serviceConnectionFragmentTwo, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onPause() {
        super.onPause();
        Message message = Message.obtain(null, MSG_UNREGISTER_CLIENT);
        try {
            serviceTwoMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        getContext().unbindService(serviceConnectionFragmentTwo);
    }

    private void setFragmentMessenger() {
        Log.d(TAG, "setFragmentMessenger: setting");
        fragmentTwoMessenger = new Messenger(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if(textViewOne != null && msg != null) {
                    Log.d(TAG, "handleMessage: supposed to set something here");
                    Date date = new Date(new Timestamp(System.currentTimeMillis()).getTime());
                    SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
                    SimpleDateFormat day = new SimpleDateFormat("dd-MM-yyyy");
                    time.setTimeZone(TimeZone.getTimeZone("GMT+3"));
                    day.setTimeZone(TimeZone.getTimeZone("GMT+3"));

                    textViewOne.setText(msg.obj.toString());
                    textViewTwo.setText(msg.obj.toString());
                    textViewThree.setText(msg.obj.toString());
                    textViewFour.setText(msg.obj.toString());

                    textViewSix.setText(day.format(date));
                    textViewSeven.setText(day.format(date));
                    textViewEight.setText(day.format(date));
                    textViewNine.setText(day.format(date));

                    textViewFive.setText(time.format(date));

                }
            }
        });
    }

    private void setServiceConnection() {
        Log.d(TAG, "setServiceConnection: setting service connection");
        serviceConnectionFragmentTwo = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                serviceTwoMessenger = new Messenger(service);
                Message message = Message.obtain(null, MSG_REGISTER_CLIENT);
                message.replyTo = fragmentTwoMessenger;

                try {
                    serviceTwoMessenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                serviceTwoMessenger = null;
            }
        };
    }

    private void initButtons(View view) {
        textViewOne = view.findViewById(R.id.sfTextView);
        textViewTwo = view.findViewById(R.id.sfTextView2);
        textViewThree = view.findViewById(R.id.sfTextView3);
        textViewFour = view.findViewById(R.id.sfTextView4);
        textViewFive = view.findViewById(R.id.sfTextView5);
        textViewSix = view.findViewById(R.id.sfTextView6);
        textViewSeven = view.findViewById(R.id.sfTextView7);
        textViewEight = view.findViewById(R.id.sfTextView8);
        textViewNine = view.findViewById(R.id.sfTextView9);
    }
}
