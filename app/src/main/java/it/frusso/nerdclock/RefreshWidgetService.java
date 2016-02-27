package it.frusso.nerdclock;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import java.util.GregorianCalendar;
import java.util.List;

/**
 * Service to repaint the wisget
 *
 * Created by jfabrix101 on 25/02/2016.
 */
public class RefreshWidgetService extends IntentService {

    public RefreshWidgetService() {
        super("RefreshWidgetService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Context context = this;

        Class widgetClass = WidgetProvider.class;
        ComponentName thisWidget = new ComponentName(context, widgetClass);

        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        int[] ids = manager.getAppWidgetIds(thisWidget);

        for (int i=0; i<ids.length; i++) {
            int appWidgetId = ids[i];
            int widgetFavoriteView = R.layout.widget;
            RemoteViews views = new RemoteViews(context.getPackageName(), widgetFavoriteView);
            paintWidget(context, views, appWidgetId);
            // Tell the AppWidgetManager to perform an update on the current app widget
            manager.updateAppWidget(appWidgetId, views);
        }
    }

    public void paintWidget(final Context context, RemoteViews rv, int appWidgetId) {

        long now = System.currentTimeMillis();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(now);
        int year = gc.get(GregorianCalendar.YEAR);
        int month = 1 + gc.get(GregorianCalendar.MONTH);
        int day = gc.get(GregorianCalendar.DAY_OF_MONTH);
        int hour = gc.get(GregorianCalendar.HOUR_OF_DAY);
        int minutes = gc.get(GregorianCalendar.MINUTE);

        SharedPreferences pref = getSharedPreferences("settings_" + appWidgetId, MODE_PRIVATE);
        String mode = pref.getString("mainClockType", null);
        if (mode == null) mode = getString(R.string.mainClockType_MILLIS);

        String row1 = null;
        if (mode.equals(getString(R.string.mainClockType_BINARY)))
            row1 = Helper.getAsBinary(year, month, day, hour, minutes);
        if (mode.equals(getString(R.string.mainClockType_HEX)))
            row1 = Helper.getAsHex(year, month, day, hour, minutes);
        if (mode.equals(getString(R.string.mainClockType_MORSE)))
            row1 = Helper.getAsMorse(year, month, day, hour, minutes);

        if (row1 == null) row1 = "" + System.currentTimeMillis();
        String row2 = Helper.getSecondRow();

        rv.setTextViewText(R.id.row1, row1);
        rv.setTextViewText(R.id.row2, row2);
    }
}
