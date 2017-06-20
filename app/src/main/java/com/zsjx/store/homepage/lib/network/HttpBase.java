package com.zsjx.store.homepage.lib.network;

import android.content.Context;

import com.zsjx.store.homepage.lib.network.persistentcookiejar.PersistentCookieJar;
import com.zsjx.store.homepage.lib.network.persistentcookiejar.cache.SetCookieCache;
import com.zsjx.store.homepage.lib.network.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.zsjx.store.homepage.lib.tools.DataUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public abstract class HttpBase {
    public static Observable.Transformer IO_UI = new ClassicIOTransformer();
    private static HttpLoggingInterceptor BODY_LOGGER = new HttpLoggingInterceptor();
    private static HttpLoggingInterceptor BASIC_LOGGER = new HttpLoggingInterceptor();
    private static OkHttpClient client;
    private static OkHttpClient glideClient;
    public static PlainTextConverterFactory TEXT = PlainTextConverterFactory.create();
    public static GsonConverterFactory GSON = GsonConverterFactory.create(DataUtil.gson);
    PersistentCookieJar cookieJar;
    SharedPrefsCookiePersistor spcp;
    public Context context;

    public HttpBase(Context context) {
        this.context = context;
        spcp = new SharedPrefsCookiePersistor(context);
        cookieJar = new PersistentCookieJar(new SetCookieCache(), spcp);
        initHttp();
    }

    void initHttp() {
        initApiClient();
        initGlideClient();
    }

    public void setDebug(boolean debug) {
        if (debug) {
            BODY_LOGGER.setLevel(HttpLoggingInterceptor.Level.BODY);
            BASIC_LOGGER.setLevel(HttpLoggingInterceptor.Level.BASIC);
        } else {
            BODY_LOGGER.setLevel(HttpLoggingInterceptor.Level.NONE);
            BASIC_LOGGER.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
    }

    void initApiClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(getConnectTimeOutSeconds(), TimeUnit.SECONDS);
        builder.readTimeout(getReadTimeOutSeconds(), TimeUnit.SECONDS);
        builder.writeTimeout(getWriteTimeOutSeconds(), TimeUnit.SECONDS);

        builder.addInterceptor(BODY_LOGGER);
        builder.cookieJar(cookieJar);
        HttpsUtils.setUnsafe(builder);
        client = builder.build();
    }

    public static final int GLIDE_TIME_OUT_SECONDS = 10;

    void initGlideClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(GLIDE_TIME_OUT_SECONDS, TimeUnit.SECONDS);
        builder.readTimeout(GLIDE_TIME_OUT_SECONDS, TimeUnit.SECONDS);
        builder.writeTimeout(GLIDE_TIME_OUT_SECONDS, TimeUnit.SECONDS);
        builder.addInterceptor(BASIC_LOGGER);
        HttpsUtils.setUnsafe(builder);
        glideClient = builder.build();
    }

    /**
     * @return 连接超时（ >= 5000ms）
     */
    public abstract int getConnectTimeOutSeconds();

    /**
     * @return 下载超时
     */
    public abstract int getReadTimeOutSeconds();

    /**
     * @return 上传超时
     */
    public abstract int getWriteTimeOutSeconds();

    /**
     * @return 默认的主机地址
     */
    public abstract String getDefaultBaseUrl();

    public static OkHttpClient getClient() {
        return client;
    }

    public static OkHttpClient getGlideClient() {
        return glideClient;
    }

    public PersistentCookieJar getCookieJar() {
        return cookieJar;
    }

    public SharedPrefsCookiePersistor getSpcp() {
        return spcp;
    }
}
