package com.lzw.httpprocessor.http;

import com.lzw.httpprocessor.interfaces.ICallBack;
import com.lzw.httpprocessor.interfaces.IhttpProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * 代理类
 */
public class HttpHelper implements IhttpProcessor {

    private static IhttpProcessor mIhttpProcessor;
    private static HttpHelper _instance;
    public String url;
    public int method;
    public Map<String, Object> head;
    public Map<String, Object> mParams;
    public String json;
    public ICallBack callback;

	private HttpHelper(){
		mParams = new HashMap<>();
    }

    public static class Builder {

        private String url;
        private int method;
        private Map<String, Object> head;
        private Map<String, Object> mParams;
        private String json;
        private ICallBack callback;

        public Builder get(String url) {
            method = METHOD_GET;
            this.url = url;
            return this;
        }

        public Builder post(String url) {
            method = METHOD_POST;
            this.url = url;
            return this;
        }

        public Builder head(Map<String, Object> head) {
            this.head = head;
            return this;
        }

        public Builder addHead(String key, String value) {
            if(null == this.head) this.head = new HashMap<>();
            this.head.put(key, value);
            return this;
        }

        public Builder setmParams(Map<String, Object> mParams) {
            this.mParams = mParams;
            return this;
        }

        public Builder setJson(String json) {
            this.json = json;
            return this;
        }

        public Builder setCallback(ICallBack callback) {
            this.callback = callback;
            return this;
        }

        public HttpHelper build() {
            synchronized (HttpHelper.class){
                if(_instance == null){
                    _instance = new HttpHelper();
                }

                _instance.url = url;
                _instance.head = head;
                _instance.mParams = mParams;
                _instance.json = json;
                _instance.callback = callback;
            }

            return _instance;
        }
    }

    public static HttpHelper obtain(){
		synchronized (HttpHelper.class){
			if(_instance == null){
				_instance = new HttpHelper();
			}
		}
        return _instance;
    }

	public static void init(IhttpProcessor httpProcessor){
        mIhttpProcessor = httpProcessor;

    }

    @Override
    public void get(String url, Map<String, Object> params, ICallBack callback) {
        //final String finalUrl = appendParams(url,params);
		mIhttpProcessor.get(url,params,callback);
    }

    @Override
    public void post(String url, Map<String, Object> params, ICallBack callback) {
        //final String finalUrl = appendParams(url,params);
		mIhttpProcessor.post(url,params,callback);
    }

    @Override
    public void postJson(String url, String params, ICallBack callback) {
        mIhttpProcessor.postJson(url,params,callback);
    }

    @Override
    public void request(HttpHelper instance) {
        mIhttpProcessor.request(instance);
    }

    //拼接url
	private String appendParams(String url, Map<String, Object> params){
        return "";
	}

}
