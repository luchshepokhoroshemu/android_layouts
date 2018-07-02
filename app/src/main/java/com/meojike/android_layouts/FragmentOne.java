package com.meojike.android_layouts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FragmentOne extends Fragment {
    private static final String TAG = "FragmentOne";

    public static final String FRAGMENT_ONE_FILTER = "FRAGMENT_ONE_FILTER";

    private ImageView firstImage;
    private ImageView secondImage;
    private ImageView thirdImage;


    private BroadcastReceiver broadcastReceiver;
    private IntentFilter intentFilter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "onReceive: yay we've received incoming intent!");

                if(firstImage != null && secondImage != null && thirdImage != null) {
                    firstImage.setColorFilter(intent.getIntExtra("firstColor", 1));
                    secondImage.setColorFilter(intent.getIntExtra("secondColor", 1));
                    thirdImage.setColorFilter(intent.getIntExtra("thirdColor", 1));
                } else {
                    Log.d(TAG, "onReceive: oh null");
                }

                Log.d(TAG, "onReceive: " + intent.getIntExtra("firstColor", 0x000000) + " " + intent.getIntExtra("secondColor", 0x000000) + " " + intent.getIntExtra("thirdColor", 0x000000));

            }
        };

        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firstImage = view.findViewById(R.id.ffImageView);
        secondImage = view.findViewById(R.id.ffImageView2);
        thirdImage = view.findViewById(R.id.ffImageView3);


        intentFilter = new IntentFilter(FRAGMENT_ONE_FILTER);
        
    }

    @Override
    public void onResume() {
        super.onResume();

        getContext().registerReceiver(broadcastReceiver, intentFilter);
        Log.d(TAG, "onResume: registered broadcastReceiver");
    }

    @Override
    public void onPause() {
        super.onPause();

        getContext().unregisterReceiver(broadcastReceiver);
        Log.d(TAG, "onPause: unregistered broadcast receiver");
    }
}
