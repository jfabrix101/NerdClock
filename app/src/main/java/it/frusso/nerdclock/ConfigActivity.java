package it.frusso.nerdclock;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class ConfigActivity extends AppCompatActivity {

    protected SharedPreferences pref = null;
    protected SettingsFragment settingsFragment = null;
    int widgetid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        setContentView(R.layout.activity_config);

        widgetid = -1;
        try {
            widgetid = getIntent().getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);

        } catch (Throwable e) {
            finish();
            return;
        }


        Helper.log("Config Activity for widgetId " + widgetid);
        settingsFragment = new SettingsFragment();
        settingsFragment.setWidgetId(widgetid);

        if (getFragmentManager().findFragmentById(R.id.content_frame) == null) {
            getFragmentManager().beginTransaction().replace(R.id.content_frame, settingsFragment).commit();
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            saveConfigurationForWidget();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed() {
        saveConfigurationForWidget();
        super.onBackPressed();
    }



    // Once the widget has been drawed, (whid default settings),
    // the config activity is displayd. If the the user change something, this
    // method must be called, this will save the configuration for this widget and will
    // ask to all widget to be repainted.

    public void saveConfigurationForWidget() {


        Helper.log("Saving settings for widgetId " + widgetid);

        // Return intent for widget - For configure widget model
        if (widgetid > 0) {
            Intent result = new Intent();
            result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetid);
            setResult(RESULT_OK, result);
            // IF OK, ask to all widget to refresh
            startService(new Intent(this, RefreshWidgetService.class));
        }



        finish();

    }


    /**
     * Fragment per le configurazioni del widget
     */
    public static class SettingsFragment extends PreferenceFragment {

        Preference mClockTypePreference;

        SharedPreferences pref = null;
        private int widgetId = -1;


        public void setWidgetId(int widgetId) {
            this.widgetId = widgetId;

        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.config_preference);

            pref = getActivity().getSharedPreferences("settings_" + widgetId, Context.MODE_PRIVATE);

            mClockTypePreference = findPreference("mainClockType");
            if (mClockTypePreference != null) {
                mClockTypePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object o) {
                        if (pref != null) {
                            pref.edit()
                                    .putString("mainClockType", o.toString())
                                    .commit();
                        }
                        return false;
                    }
                });
            }
        }
    }
}
