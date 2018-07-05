package com.meojike.android_layouts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentThree extends Fragment {
    private static final String TAG = "FragmentThree";

    public static final String FRAGMENT_THREE_FILTER = "FRAGMENT_THREE_FILTER";

    private TextView firstText;
    private TextView secondText;
    private TextView thirdText;

    private BroadcastReceiver broadcastReceiver;
    private IntentFilter intentFilter;

    private Handler handler = new Handler();

    SendFragmentThreeData sendDataToFragmentTwo = (SendFragmentThreeData) getActivity();

    private String colorStringForExport;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if(firstText != null && secondText != null && thirdText != null) {
                    firstText.setText(Integer.toHexString(intent.getIntExtra("firstColor", 0)));
                    colorStringForExport = Integer.toHexString(intent.getIntExtra("secondColor", 0));
                    thirdText.setText(Integer.toHexString(intent.getIntExtra("thirdColor", 0)));
                }
            }
        };

        return inflater.inflate(R.layout.fragment_three, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        firstText = view.findViewById(R.id.tfTextView);
        secondText = view.findViewById(R.id.tfTextView2);
        thirdText = view.findViewById(R.id.tfTextView3);

        intentFilter = new IntentFilter(FRAGMENT_THREE_FILTER);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if(colorStringForExport != null && colorStringForExport.length() != 1) {
                        String color = "#" + colorStringForExport;

                        while(color.length() != 9) {
                            color += "f";
                        }
                        sendDataToFragmentTwo.sendFragmentThreeData(Color.parseColor(color));
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    @Override
    public void onResume() {
        super.onResume();

        getContext().registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();

        getContext().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        sendDataToFragmentTwo = (SendFragmentThreeData) getActivity();
    }

    public void setReceivedData(final String receivedData)
    {

        handler.post(new Runnable() {
            @Override
            public void run() {
                secondText.setText(receivedData);
            }
        });
    }

    public interface SendFragmentThreeData {
        void sendFragmentThreeData(int data);
    }
}
