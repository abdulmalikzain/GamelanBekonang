package com.gamelanbekonang.splash;


import android.app.Activity;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gamelanbekonang.R;

public class PermissionHelper {
    private Activity mActivity;
    private final int REQUEST_PERMISSION = 99;
    private String TAG = "PermissionHelper";
    private PermissionListener listener;

    public PermissionHelper(Activity activity){
        mActivity = activity;
    }

    public void permissionListener (PermissionListener permissionListener){
        listener = permissionListener;
    }


    public boolean checkAndRequestPermissions(){//1. Call this to check permission. (Call this affected loop for check permission until user Approved it)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionPhone = ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CALL_PHONE);
            int permissionSMS   = ContextCompat.checkSelfPermission(mActivity, Manifest.permission.SEND_SMS);

            List<String> listPermissionsNeeded = new ArrayList<>();

            if (permissionPhone!= PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(mActivity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_PERMISSION);
                return false;
            }

        }

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        listener.onPermissionCheckDone();

        return true;
    }

    public void onRequestCallBack(int RequestCode,String[] permissions ,int[] grantResults){//2. call this inside onRequestPermissionsResult
        switch (RequestCode) {
            case REQUEST_PERMISSION: {
                Map<String, Integer> perms = new HashMap<>();
                perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.SEND_SMS, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        Log.e(TAG, "permission granted");

                        checkAndRequestPermissions();
                    } else {
                        Log.e(TAG, "Some permissions are not granted ask again ");
                        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.SEND_SMS)
                                || ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.CALL_PHONE)){
                            showDialogOK(mActivity.getString(R.string.permission_dialog),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                            }
                                        }
                                    });
                        }
                        else {
                            Toast.makeText(mActivity, R.string.go_to_permissions_settings, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", mActivity.getPackageName(), null);
                            intent.setData(uri);
                            mActivity.startActivity(intent);
                        }
                    }
                }
            }
        }
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(mActivity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .create()
                .show();
    }

    public interface PermissionListener{
        void onPermissionCheckDone();
    }
}
