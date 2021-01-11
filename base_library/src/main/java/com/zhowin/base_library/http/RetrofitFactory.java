package com.zhowin.base_library.http;

import android.content.SyncAdapterType;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.GsonBuilder;
import com.zhowin.base_library.utils.DateHelpUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import javax.crypto.spec.SecretKeySpec;

import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tgio.rncryptor.RNCryptorNative;

public class RetrofitFactory {


    public static final int DEFAULT_TIME = 30;
    public static RetrofitFactory httpUtils;
    private OkHttpClient okHttpClient;
    public static final String ENCRYPTION_PASSWORD = "q1KtBUuhjNf6eMGHa0dhoqrjZQhnyEY7";


    private RetrofitFactory() {
        okHttpClient = initOkHttp();
    }

    public static RetrofitFactory getInstance() {
        if (httpUtils == null) {
            httpUtils = new RetrofitFactory();
        }
        return httpUtils;
    }


    /**
     * 初始化Retrofit
     */
    @NonNull
    public Retrofit initRetrofit(String url) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 初始化okhttp
     */
    @NonNull
    private OkHttpClient initOkHttp() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient().newBuilder()
                .readTimeout(DEFAULT_TIME, TimeUnit.SECONDS)//设置读取超时时间
                .connectTimeout(DEFAULT_TIME, TimeUnit.SECONDS)//设置请求超时时间
                .writeTimeout(DEFAULT_TIME, TimeUnit.SECONDS)//设置写入超时时间
                .addNetworkInterceptor(httpLoggingInterceptor)
//                .addInterceptor(new EncryptInterceptor())
                .addInterceptor(new LogInterceptor())//添加打印拦截器
//                .addInterceptor(new ResponseDecryptInterceptor())
                .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                .build();
    }

    /**
     * 加密
     */
    private static class EncryptInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            RequestBody requestBody = request.body();
            String method = request.method();
            Charset charset = Charset.forName("UTF-8");
            if ("POST".equals(method)) {
                if (requestBody instanceof FormBody) {
                    MediaType contentType = requestBody.contentType();
                    if (contentType != null) {
                        charset = contentType.charset(charset);
                        /*如果是二进制上传  则不进行加密*/
                        if (contentType.type().toLowerCase().equals("multipart")) {
                            return chain.proceed(request);
                        }
                    }
                    //把原来的参数添加到新的构造器，并增加时间参数
                    FormBody.Builder newFormBuilder = new FormBody.Builder();
                    FormBody formBody = (FormBody) requestBody;
                    for (int i = 0; i < formBody.size(); i++) {
                        newFormBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                    }
                    formBody = newFormBuilder
                            .addEncoded("timestamp", DateHelpUtils.getCurrentTime())
                            .build();
                    request = request.newBuilder().post(formBody).build();
                    String token = request.header("token");
                    RequestBody body = request.body();
                    Log.e("xy", "添加参数后数据 token:" + token + "\n body:" + body.toString());

                    //对新的构造器进行加密处理
                    RNCryptorNative rncryptor = new RNCryptorNative();
                    String encryptedToken = new String(rncryptor.encrypt(token, ENCRYPTION_PASSWORD));
                    String encryptedBody = new String(rncryptor.encrypt(body.toString(), ENCRYPTION_PASSWORD));

                    //构建新的requestBuilder
                    Request.Builder newRequestBuilder = request.newBuilder();
                    newRequestBuilder.addHeader("token",encryptedToken);
                    RequestBody newRequestBody = RequestBody.create(contentType, encryptedBody);

                    newRequestBuilder.post(newRequestBody);
                    request = newRequestBuilder.build();
                }
            }
            return chain.proceed(request);
        }
    }


    /**
     * 加密
     */
    private class RequestEncryptInterceptor implements Interceptor {

        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {

            Request request = chain.request();
            Charset charset = Charset.forName("UTF-8");
            String method = request.method().toLowerCase().trim();
            HttpUrl url = request.url();
            /*如果请求方式是Get或者Delete，此时请求数据是拼接在请求地址后面的*/
            if (method.equals("get") || method.equals("delete")) {
                /*如果有请求数据 则加密*/
                if (url.encodedQuery() != null) {
                    try {
                        String queryparamNames = request.url().encodedQuery();
                        RNCryptorNative rncryptor = new RNCryptorNative();
                        String encrypted = new String(rncryptor.encrypt(queryparamNames, ENCRYPTION_PASSWORD));
                        //拼接加密后的url，参数字段自己跟后台商量，这里我用param，后台拿到数据先对param进行解密，解密后的数据就是请求的数据
                        String newUrl = "&param=" + encrypted;
                        //构建新的请求
                        request = request.newBuilder().url(newUrl).build();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return chain.proceed(request);
                    }
                }
            } else {
                //不是Get和Delete请求时，则请求数据在请求体中
                RequestBody requestBody = request.body();
                /*判断请求体是否为空  不为空则执行以下操作*/
                if (requestBody != null) {
                    MediaType contentType = requestBody.contentType();
                    if (contentType != null) {
                        charset = contentType.charset(charset);
                        /*如果是二进制上传  则不进行加密*/
                        if (contentType.type().toLowerCase().equals("multipart")) {
                            return chain.proceed(request);
                        }
                    }
                    /*获取请求的数据*/
                    try {
                        String requestToken = request.header("token");
                        request.url().newBuilder()
                                .setEncodedQueryParameter("timestamp", DateHelpUtils.getCurrentTime());

                        Buffer buffer = new Buffer();
                        requestBody.writeTo(buffer);
                        String requestData = URLDecoder.decode(buffer.readString(charset).trim(), "utf-8");

                        String params = buffer.readString(charset);
                        System.out.println("加密前数据request参数: " + params + "\n token:" + requestToken);
                        buffer.clone();

                        RNCryptorNative rncryptor = new RNCryptorNative();
                        String encryptData = new String(rncryptor.encrypt(requestData, ENCRYPTION_PASSWORD));
                        System.out.println("加密后数据：" + encryptData);
                        /*构建新的请求体*/
                        RequestBody newRequestBody = RequestBody.create(contentType, encryptData);
                        /*构建新的requestBuilder*/
                        Request.Builder newRequestBuilder = request.newBuilder();
                        //根据请求方式构建相应的请求
                        switch (method) {
                            case "post":
                                newRequestBuilder.post(newRequestBody);
                                break;
                            case "put":
                                newRequestBuilder.put(newRequestBody);
                                break;
                        }
                        request = newRequestBuilder
                                .build();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return chain.proceed(request);
                    }
                }
            }
            return chain.proceed(request);
        }
    }

    /**
     * 解密
     */
    private class ResponseDecryptInterceptor implements Interceptor {

        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                //开始解密
                try {
                    BufferedSource source = responseBody.source();
                    source.request(Long.MAX_VALUE);
                    Buffer buffer = source.buffer();
                    Charset charset = Charset.forName("UTF-8");
                    MediaType contentType = responseBody.contentType();
                    if (contentType != null) {
                        charset = contentType.charset(charset);
                    }
                    String bodyString = buffer.clone().readString(charset);
//                    System.out.println("bodyString:" + bodyString);
                    RNCryptorNative rncryptor = new RNCryptorNative();
                    String decrypted = rncryptor.decrypt(bodyString, RetrofitFactory.ENCRYPTION_PASSWORD);
                    System.out.println("解密后：decrypted:" + decrypted);
                    /*将解密后的明文返回*/
                    ResponseBody newResponseBody = ResponseBody.create(contentType, decrypted.trim());
                    response = response.newBuilder().body(newResponseBody).build();
                } catch (Exception e) {
                    return response;
                }
            } else {
                System.out.println("响应体为空");
            }
            return response;
        }
    }

    /**
     * 日志
     */
    public class LogInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String method = request.method();
            if ("POST".equals(method)) {
                Buffer buffer = new Buffer();
                try {
                    request.body().writeTo(buffer);
                    Charset charset = Charset.forName("UTF-8");
                    MediaType contentType = request.body().contentType();
                    if (contentType != null) {
                        charset = contentType.charset(charset);
                    }
                    String params = buffer.readString(charset);
                    System.out.println("request参数: " + params + "\n token:" + request.header("token"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("request  url:" + request.url().toString());
            Response response = chain.proceed(chain.request());
            MediaType mediaType = response.body().contentType();
            String content = response.body().string();
//            System.out.println("request  response:" + content);
            return response.newBuilder()
                    .body(ResponseBody.create(mediaType, content))
                    .build();
        }
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
