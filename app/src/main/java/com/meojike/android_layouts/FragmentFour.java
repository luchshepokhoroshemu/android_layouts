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
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentFour extends Fragment {

    private static final String TAG = "FragmentFour";

    public static final int MSG_REGISTER_CLIENT  = 0xFEED0;
    public static final int MSG_UNREGISTER_CLIENT = 0xDEAD0;

    private TextView rotatingTextView;

    private ServiceConnection serviceConnectionFragmentFour;

    private Messenger fragmentFourMessenger;
    private Messenger serviceFourMessenger;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_four, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initTextView(view);
        setServiceConnection();
        setFragmentMessenger();
    }

    @Override
    public void onResume() {
        super.onResume();
        getContext().bindService(ServiceFragmentFour.newIntent(getContext()), serviceConnectionFragmentFour, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onPause() {
        super.onPause();
        Message message = Message.obtain(null, MSG_UNREGISTER_CLIENT);
        try {
            serviceFourMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        getContext().unbindService(serviceConnectionFragmentFour);
    }

    private void initTextView(View view) {
        rotatingTextView = view.findViewById(R.id.ffTextView);
    }

    private void setServiceConnection() {
        serviceConnectionFragmentFour = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                serviceFourMessenger = new Messenger(iBinder);
                Message message = Message.obtain(null, MSG_REGISTER_CLIENT);
                message.replyTo = fragmentFourMessenger;

                try {
                    serviceFourMessenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                    serviceFourMessenger = null;
            }
        };
    }

    private void setFragmentMessenger() {
        Log.d(TAG, "setFragmentMessenger: setting fragment four messenger");

        fragmentFourMessenger = new Messenger(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if(rotatingTextView != null && msg != null) {
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) rotatingTextView.getLayoutParams();
                    params.circleAngle = (float) msg.obj;
                    rotatingTextView.setLayoutParams(params);
                }
            }
        });

    }


}
