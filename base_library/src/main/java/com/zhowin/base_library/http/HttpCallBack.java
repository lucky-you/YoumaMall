package com.zhowin.base_library.http;



public abstract class HttpCallBack<T> {

    public abstract void onSuccess(T t);

    public abstract void onFail(int errorCode, String errorMsg);


}
