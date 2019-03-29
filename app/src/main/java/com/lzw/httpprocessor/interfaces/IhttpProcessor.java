package com.lzw.httpprocessor.interfaces;

import com.lzw.httpprocessor.http.HttpHelper;

import java.util.Map;

public interface IhttpProcessor {

    static final int METHOD_GET = 1;
    static final int METHOD_POST = 2;

    //GET请求
    void get(String url, Map<String, Object> params, ICallBack callback);

    //POST请求
    void post(String url, Map<String, Object> params, ICallBack callback);

    //POST Json请求
    void postJson(String url, String params, final ICallBack callback);

    void request(HttpHelper instance);
}
