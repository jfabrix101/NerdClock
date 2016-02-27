package it.frusso.nerdclock;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import java.util.Date;

/**
 * Created by jfabrix101 on 25/02/16.
 */
public class TimeTickService extends Service {

    private BroadcastReceiver tickTimeReceiver = null;

    @Override
    public IBinder onBind(Intent arg0) { return null; }


    @Override
    public void onCreate() {
        super.onCreate();

        // Create a receiver for events at each minute
        tickTimeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                try {
                    // Refresh all widgets
                    context.startService(new Intent(context, RefreshWidgetService.class));
                } catch (Exception e) {
                }

            }
        };

        // Listener for ACTION_TIME_TICK events
        IntentFilter intentFilterTickTime = new IntentFilter(Intent.ACTION_TIME_TICK);
        registerReceiver(tickTimeReceiver, intentFilterTickTime);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(tickTimeReceiver);
    }
}
