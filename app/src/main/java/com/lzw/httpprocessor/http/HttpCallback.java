package com.lzw.httpprocessor.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzw.httpprocessor.interfaces.ICallBack;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Result 是javabean对象
 */

public abstract class  HttpCallback<Result> implements ICallBack {

    @Override
    public void onSuccess(String result) {
        Gson gson = new Gson();
        Class<?> cls = analysisClazzInfo(getClass());
//        Result objResult = (Result)gson.fromJson(result,cls);
//        onSuccess(objResult);
        Result obj = (Result) JSON.parseObject(result, new TypeReference<Result>(){});
        onSuccess(obj);
    }

    public abstract void onSuccess(Result result);

    public Class<?> analysisClazzInfo(Object object){
//        Type mGenericSuperclass;
//        Type genericSuperclass = object.getClass().getGenericSuperclass();
//        if (genericSuperclass instanceof ParameterizedType) {
//            mGenericSuperclass = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
//        } else {
//            mGenericSuperclass = Object.class;
//        }
//        return mGenericSuperclass;
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }


    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型
     * @param clazz
     * @return 返回第一个类型
     */
    public static Class getSuperClassGenricType(Object clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型
     * @param clazz
     */
    public static Class getSuperClassGenricType(Object clazz, int index)
            throws IndexOutOfBoundsException {
        Type genType = clazz.getClass().getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }
}
