package com.lzw.httpprocessor.processor;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.Log;

import com.lzw.httpprocessor.BuildConfig;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoggerInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        // 拦截请求，获取到该次请求的request
        Request request = chain.request();
        // 执行本次网络请求操作，返回response信息
        Response response = chain.proceed(request);
        if (BuildConfig.DEBUG) {
            for (String key : request.headers().toMultimap().keySet()) {
                Log.e("zp_test", "header: {" + key + " : " + request.headers().toMultimap().get(key) + "}");
            }
            Log.e("zp_test", "url: " + request.url().uri().toString());
            ResponseBody responseBody = response.body();
        }

        return response.newBuilder()
                // 增加一个缓存头信息，缓存时间为60s
                .header("cache-control", "public, max-age=60")
                // 移除pragma头信息
                .removeHeader("pragma")
                .build();
    }

}