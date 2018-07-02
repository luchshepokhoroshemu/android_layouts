package com.meojike.android_layouts;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class ServiceFragmentFour extends Service {
    public ServiceFragmentFour() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static final Intent newIntent(Context context) {
        return new Intent(context, ServiceFragmentFour.class);
    }
}
