package com.test.openweather.error;

import android.support.annotation.NonNull;
import android.view.View;

import java.lang.ref.WeakReference;

import retrofit2.Call;

/**
 * Created by veon on 6/25/16.
 */
public abstract class SnackOnError<T> implements retrofit2.Callback<T> {
    private final WeakReference<View> viewWeakReference;

    protected SnackOnError(@NonNull View context) {
        this.viewWeakReference = new WeakReference<View>(context);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        View view = viewWeakReference.get();
        ErrorHandler.showSnack(view,t,call.toString());
    }
}
