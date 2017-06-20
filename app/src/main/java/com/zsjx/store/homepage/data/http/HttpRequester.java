package com.zsjx.store.homepage.data.http;

import android.content.Context;

import com.zsjx.store.homepage.app.StoreApp;
import com.zsjx.store.homepage.lib.network.HttpBase;

import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class HttpRequester extends HttpBase {
    private static HttpRequester requester = new HttpRequester(StoreApp.get());

    private HttpRequester(Context context) {
        super(context);
    }

    public static HttpRequester get() {
        return requester;
    }

    @Override
    public String getDefaultBaseUrl() {
        return null;
    }

    @Override
    public int getConnectTimeOutSeconds() {
        return 10;
    }

    @Override
    public int getReadTimeOutSeconds() {
        return 10;
    }

    @Override
    public int getWriteTimeOutSeconds() {
        return 10;
    }

    Retrofit retrofit;

    public Retrofit retrofit() {
        if (retrofit == null) {
            retrofit = newRetrofit(null, null);
        }
        return retrofit;
    }

    Retrofit rxGsonRtrofit;

    public Retrofit rxGson() {
        if (rxGsonRtrofit == null) {
            rxGsonRtrofit = newRetrofit(GSON, RxJavaCallAdapterFactory.create());
        }
        return rxGsonRtrofit;
    }

    Retrofit gsonRetrofit;

    public Retrofit gsonRetrofit() {
        if (gsonRetrofit == null) {
            gsonRetrofit = newRetrofit(GSON, null);
        }
        return gsonRetrofit;
    }

    Retrofit textRetrofit;

    public Retrofit textRetrofit() {
        if (textRetrofit == null) {
            textRetrofit = newRetrofit(TEXT, null);
        }
        return textRetrofit;
    }

    Retrofit rxTextRetrofit;

    public Retrofit rxTextRetrofit() {
        if (rxTextRetrofit == null) {
            rxTextRetrofit = newRetrofit(TEXT, RxJavaCallAdapterFactory.create());
        }
        return rxTextRetrofit;
    }

    public Retrofit newRetrofit(Converter.Factory converterFactory, CallAdapter.Factory callFactory) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(getClient());
        builder.baseUrl(getDefaultBaseUrl());
        if (converterFactory != null) {
            builder.addConverterFactory(converterFactory);
        }
        if (callFactory != null) {
            builder.addCallAdapterFactory(callFactory);
        }
        return builder.build();
    }

}
