package com.betterlyf.app.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.os.Build;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class AppApplication extends Application {

    private static AppApplication mInstance;

    public static AppApplication get() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // FacebookSdk.sdkInitialize(getApplicationContext());
        mInstance = this;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.betterlyf.app",  // replace with your unique package name
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = null;
                try {
                    md = MessageDigest.getInstance("SHA");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static synchronized AppApplication getInstance() {
        return mInstance;
    }

    public void setFontFamily(TextView txt) {
        if (txt != null) {
            Typeface face = Typeface.createFromAsset(getAssets(),
                    "fonts/MarkaziText.ttf");
            txt.setTypeface(face);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);

    }
}
