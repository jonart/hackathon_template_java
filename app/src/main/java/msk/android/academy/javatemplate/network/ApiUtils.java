package msk.android.academy.javatemplate.network;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import msk.android.academy.javatemplate.BuildConfig;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtils {

    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;
    private static Gson gson;
    private static Api api;

    public static OkHttpClient getBasicAuthClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.writeTimeout(20, TimeUnit.SECONDS);
            builder.callTimeout(20, TimeUnit.SECONDS);
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.connectTimeout(20, TimeUnit.SECONDS);

            if (!BuildConfig.BUILD_TYPE.contains("release")) {
                builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
            }

            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    public static Retrofit getRetrofitWithConverter() {
        if (gson == null) {
            gson = new Gson();
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                //need for interceptors
                .client(getBasicAuthClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
//        if (!isNeeded){
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(BuildConfig.SERVER_URL)
//                    //need for interceptors
////                    .client(getBasicAuthClient("", "", false))
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .build();
//        }

        return retrofit;
    }

    public static Api getApiService() {
        api = getRetrofitWithConverter().create(Api.class);
        return api;
    }

}
