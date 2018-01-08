package com.fieldstaffapp.fieldstaffapp.background_service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class UpdateReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        try {
        	//Toast.makeText(context, "App update", Toast.LENGTH_SHORT).show();
            context.startService(new Intent(context,SMessageService.class));
            return;
        }
        catch (Exception exp) {
            return;
        }
    }
}