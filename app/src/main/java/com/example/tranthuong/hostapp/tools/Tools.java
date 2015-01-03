package com.example.tranthuong.hostapp.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.example.tranthuong.hostapp.R;

import java.util.List;

/**
 * Created by TRANTHUONG.
 */
public class Tools
{

    public static boolean isIntentAvailable(Context context, Intent intent)
    {
        final PackageManager packageManager = context.getPackageManager();
        List list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public static void showAlert(Context context, String msg)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        //alertDialog.setTitle("Title");
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE ,
                context.getString(R.string.ok_title),
                new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Add your code for the button here.
            }
        });
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.show();
    }
}
