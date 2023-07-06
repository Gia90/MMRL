package com.dergoogler.mmrl;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.dergoogler.core.NativeFs;
import com.dergoogler.core.NativeOS;
import com.dergoogler.core.NativeStorage;
import com.dergoogler.core.NativeShell;
import com.dergoogler.core.NativeBuildConfig;

import org.apache.cordova.CordovaActivity;

public class MainActivity extends CordovaActivity {
    @Override
    @SuppressLint("SetJavaScriptEnabled")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appView = findViewById(R.id.mmrl_view);
        super.init();

        WebView wv = (WebView) appView.getEngine().getView();
        // enable Cordova apps to be started in the background
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean("cdvStartInBackground", false)) {
            moveTaskToBack(true);
        }

        // Set by <content src="index.html" /> in config.xml
        if (BuildConfig.DEBUG) {
            loadUrl("http://192.168.1.110:9000");
        } else {
            loadUrl(launchUrl);
        }

        WebSettings webViewSettings = wv.getSettings();
        // Options
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.setAllowFileAccess(true);
        webViewSettings.setAllowContentAccess(true);
        webViewSettings.setAllowFileAccessFromFileURLs(true);
        webViewSettings.setAllowUniversalAccessFromFileURLs(true);
        webViewSettings.setDatabaseEnabled(true);
        webViewSettings.setDomStorageEnabled(false);
        webViewSettings.setUserAgentString("MMRL");
        webViewSettings.setAllowFileAccessFromFileURLs(false);
        webViewSettings.setAllowUniversalAccessFromFileURLs(false);
        webViewSettings.setAllowFileAccess(false);
        webViewSettings.setAllowContentAccess(false);

        // Core
        wv.addJavascriptInterface(new NativeFs(this), "__fs__");
        wv.addJavascriptInterface(new NativeShell(wv), "__shell__");
        wv.addJavascriptInterface(new NativeBuildConfig(), "__buildconfig__");
        wv.addJavascriptInterface(new NativeOS(this), "__os__");
        wv.addJavascriptInterface(new NativeStorage(this), "__nativeStorage__");

    }
}
