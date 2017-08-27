package com.example.olden.cryptoexchange;


import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.olden.cryptoexchange.data.network.api.ApiModule;

public class CryptoExchangeApplication extends Application {

    public static final String BASE_URL = "https://www.cryptocompare.com/";
    private AppComponent appComponent;

    @NonNull
    public static CryptoExchangeApplication get(@NonNull Context context) {
        return (CryptoExchangeApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = prepareApplicationComponent().build();

    }

    protected DaggerAppComponent.Builder prepareApplicationComponent() {
        return DaggerAppComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiModule(new ApiModule(BASE_URL));
    }

    @NonNull
    public AppComponent get() {return appComponent;}
}
