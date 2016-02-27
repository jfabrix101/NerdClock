package it.frusso.nerdclock;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

/**
 * WidgetProvider class
 *
 * Il metodo onUpdate diesgna il widget ed avvia il servizio che allo scadere di ogni
 * minuto ridisegna il widget.
 *
 * Created by jfabrix101 on 25/02/16.
 */
public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Call the service for update the widget (remotely)
        context.startService(new Intent(context, RefreshWidgetService.class));

        // Start the service for paint the widget at each minute
        context.startService(new Intent(context, TimeTickService.class));
    }


}
