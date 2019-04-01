package com.lzw.httpprocessor.processor;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 */
public class TokenHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        String token = "token-value";
        if (TextUtils.isEmpty(token)) {
            Request originalRequest = chain.request();
            return chain.proceed(originalRequest);
        }else {
            Request originalRequest = chain.request();
            Request updateRequest = originalRequest.newBuilder().header("token", token)
                    .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJlYzQ4NWZmNWY2MzE0MDA4YjQyODI2YTg1MGUwODQ5ZSIsInN1YiI6IjEzMjI0NDQ5ODg4IiwiaWF0IjoxNTU0MTA1MjkzLCJyb2xlcyI6Iue6quW3peWnlOWFmumjjuenkeWupOS6uuWRmCznuqrlt6Xlp5TmuIXljZXkuIrmiqUiLCJleHAiOjE1NTQxNzcyOTN9.8ePYUbp_Gs9GpjGCHV1OhKvRaF2Yfxme8mRo8QJfW0c")
                    .build();
            return chain.proceed(updateRequest);
        }
    }

}