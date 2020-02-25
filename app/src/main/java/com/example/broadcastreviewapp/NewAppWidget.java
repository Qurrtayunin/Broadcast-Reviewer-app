package com.example.broadcastreviewapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.broadcastreviewerapp.R;

import java.text.DateFormat;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    private static final String SHARED_PREF_FILE="com.example.broadcastapp1";
    private static final String COUNT_KEY="count";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        SharedPreferences prefs=context.getSharedPreferences(SHARED_PREF_FILE,0);
        int count=prefs.getInt(COUNT_KEY+appWidgetId,0);
        count++;
        String dateString= DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date());

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_update,dateString+"");
        views.setTextViewText(R.id.appwidget_update_label, "Last updated : ("+count+")");

        SharedPreferences.Editor prefEditor=prefs.edit();
        prefEditor.putInt(COUNT_KEY+appWidgetId,count);
        prefEditor.apply();

        Intent intentUpdate = new Intent(context,NewAppWidget.class);
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        int[] idArray = new int[]{appWidgetId};
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,idArray);

        PendingIntent pendingUpdate=PendingIntent.getBroadcast(context,appWidgetId,intentUpdate,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.button_update, pendingUpdate);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

        //tanggal
        CharSequence widgetText = "Clock";
        String dateresult= DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date());
        views.setTextViewText(R.id.dateresult, ""+dateresult);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

