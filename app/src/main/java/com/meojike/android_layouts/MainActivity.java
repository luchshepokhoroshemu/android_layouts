package com.meojike.android_layouts;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/** 4 фрагмента:
 Фрагмент будет содержать разметку LinearLayout с горизонтальной ориентацией
 Фрагмент будет содержать разметку с RelativeLayout
 Фрагмент будет содержать разметку LinearLayout с вертикальной ориентацией
 Фрагмент будет содержать разметку с ConstraintLayout
 1 и 3 фрагменты должны получать данные с Service с помощью BroadcastReceiver
 2 и 4 должны получать данные с сервиса с помощью присоединения к сервису (bind)
 2 и 3 фрагмент должны обмениваться данными раз в 3 секунды через activity */

public class MainActivity extends AppCompatActivity
        implements FragmentTwo.SendFragmentTwoData,
        FragmentThree.SendFragmentThreeData{
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(ServiceFragmentOne.newIntent(MainActivity.this));
        startService(ServiceFragmentThree.newIntent(MainActivity.this));
    }

    @Override
    public void sendFragmentTwoData(String data) {

        FragmentThree fragmentThree = (FragmentThree) getSupportFragmentManager().findFragmentById(R.id.fragment3);
        if(data != null && fragmentThree != null) {
            fragmentThree.setReceivedData(data);
        }

    }

    @Override
    public void sendFragmentThreeData(int data) {
        FragmentTwo fragmentTwo = (FragmentTwo) getSupportFragmentManager().findFragmentById(R.id.fragment2);
        if(data != 0 && fragmentTwo != null) {
            fragmentTwo.setReceivedData(data);
        }
    }

}