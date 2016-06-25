package com.test.openweather.error;

import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

/**
 * Created by veon on 6/25/16.
 */
public class ErrorHandler {
    public static void showSnack(View view, Throwable t, String extra) {
        Log.e("onFailure", extra, t);
        if (view == null) return;

        Snackbar.make(view, t.getLocalizedMessage(), Snackbar.LENGTH_LONG)
                .show();
    }
}
