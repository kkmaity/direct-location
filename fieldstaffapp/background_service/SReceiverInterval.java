/*
 * Decompiled with CFR 0_92.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  java.lang.Class
 *  java.lang.Exception
 */
package com.fieldstaffapp.fieldstaffapp.background_service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/*
 * Failed to analyse overrides
 */
public class SReceiverInterval extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) 
    {
        try 
        {
        //	Toast.makeText(context, "Receiver Interval", Toast.LENGTH_SHORT).show();
            context.startService(new Intent(context,SMessageService.class));
            return;
        }
        catch (Exception var3_3) {
            return;
        }
    }
}

