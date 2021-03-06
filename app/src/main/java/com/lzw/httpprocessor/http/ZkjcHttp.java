package com.lzw.httpprocessor.http;

import com.lzw.httpprocessor.interfaces.ICallBack;
import com.lzw.httpprocessor.interfaces.IhttpProcessor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 代理类
 */
public class ZkjcHttp {

    private static IhttpProcessor mIhttpProcessor;
    private static ZkjcHttp _instance;
    public Map<String, Object> mParams;
    public String json;
    public ICallBack callback;
    public Map<String, Object> mRequestParams;

	private ZkjcHttp(){
        if(null == mParams) mParams = new HashMap<>();
		if(null == mRequestParams) mRequestParams = new HashMap<>();
    }

    public static class Builder {

        private String url;
        private int method;
        private Map<String, Object> head;
        private Map<String, Object> mParams;
        private String json;
        private ICallBack callback;
        private Map<String, Object> mRequestParams;
        private List<String> mFiles;

        public Builder get(String url) {
//            method = METHOD_GET;
            if(null == mRequestParams) mRequestParams = new HashMap<>();
            mRequestParams.put("method", "METHOD_GET");
            this.url = url;
            return this;
        }

        public Builder post(String url) {
//            method = METHOD_POST;
            if(null == mRequestParams) mRequestParams = new HashMap<>();
            mRequestParams.put("method", "METHOD_POST");
            this.url = url;
            return this;
        }

        public Builder head(Map<String, Object> head) {
            if(null == this.mParams) this.mParams = new HashMap<>();
            Iterator<Map.Entry<String, Object>> iter = mParams.entrySet().iterator();
            while(iter.hasNext()) {
                Map.Entry<String, Object> next = iter.next();
                this.mParams.put("head$" + next.getKey(), next.getValue());
            }
            return this;
        }

        public Builder addHead(String key, String value) {
            if(null == this.mParams) this.mParams = new HashMap<>();
            this.mParams.put("head$" + key, value);
            return this;
        }

        public Builder addParam(String key, String value) {
            if(null == this.mParams) this.mParams = new HashMap<>();
            this.mParams.put(key, value);
            return this;
        }

        public Builder setParams(Map<String, Object> mParams) {
            this.mParams = mParams;
            return this;
        }

        public Builder setFiles(Map<String, Object> mParams) {
            if(null == this.mParams) this.mParams = new HashMap<>();
            Iterator<Map.Entry<String, Object>> iter = mParams.entrySet().iterator();
            while(iter.hasNext()) {
                Map.Entry<String, Object> next = iter.next();
                this.mParams.put("file$" + next.getKey(), next.getValue());
            }
            return this;
        }

        public Builder addFile(String key, String value) {
            if(null == this.mParams) this.mParams = new HashMap<>();
            this.mParams.put("file$" + key, value);
            return this;
        }

        public Builder setJsonContent(String json) {
            this.json = json;
            return this;
        }

        public Builder setCallback(ICallBack callback) {
            this.callback = callback;
            return this;
        }

        public ZkjcHttp build() {
            synchronized (ZkjcHttp.class){
                if(_instance == null){
                    _instance = new ZkjcHttp();
                }

                mRequestParams.put("url", url);
//                mRequestParams.put("method", method);
                mRequestParams.put("mParams", mParams);
                mRequestParams.put("json", json);
                mRequestParams.put("callback", callback);
                _instance.mRequestParams = mRequestParams;
//                _instance.url = url;
//                _instance.method = method;
//                _instance.head = head;
//                _instance.mParams = mParams;
//                _instance.json = json;
//                _instance.callback = callback;
            }

            return _instance;
        }

        public ZkjcHttp request() {
            synchronized (ZkjcHttp.class) {
                if (_instance == null) {
                    _instance = new ZkjcHttp();
                }

                mRequestParams.put("url", url);
//                mRequestParams.put("method", method);
                mRequestParams.put("mParams", mParams);
                mRequestParams.put("json", json);
                mRequestParams.put("callback", callback);
                mIhttpProcessor.request(mRequestParams);
            }

            return _instance;
        }

    }

    public static ZkjcHttp obtain(){
		synchronized (ZkjcHttp.class){
			if(_instance == null){
				_instance = new ZkjcHttp();
			}
		}
        return _instance;
    }

	public static void init(IhttpProcessor httpProcessor){
        mIhttpProcessor = httpProcessor;

    }

    public void get(String url, Map<String, Object> params, ICallBack callback) {
        //final String finalUrl = appendParams(url,params);
		mIhttpProcessor.get(url,params,callback);
    }

    public void post(String url, Map<String, Object> params, ICallBack callback) {
        //final String finalUrl = appendParams(url,params);
		mIhttpProcessor.post(url,params,callback);
    }

    public void request() {
        mIhttpProcessor.request(mRequestParams);
    }

    //拼接url
	private String appendParams(String url, Map<String, Object> params){
        return "";
	}

}
