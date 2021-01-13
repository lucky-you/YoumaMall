package com.zhowin.base_library.http;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.zhowin.base_library.utils.DateHelpUtils;
import com.zhowin.base_library.utils.GsonUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
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
                .addInterceptor(new RequestEncryptInterceptor())
                .addInterceptor(new LogInterceptor())//添加打印拦截器
                .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                .build();
    }

    /**
     * 加密
     */
    private static class EncryptInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request().newBuilder().addHeader("timestamp", DateHelpUtils.getCurrentTime()).build();
            Request request = chain.request();
            RequestBody requestBody = request.body();
            String url = request.url().toString();
            Log.e("xy", "url:" + url + "<---requestBody:" + requestBody.toString().length());
            String method = request.method();
            Charset charset = Charset.forName("UTF-8");
            Request.Builder builder = null;
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
                    HashMap<String, Object> paramMap = new HashMap<>();
                    for (int i = 0; i < formBody.size(); i++) {
//                        newFormBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                        paramMap.put(formBody.encodedName(i), formBody.encodedValue(i));
                    }
//                    newFormBuilder.add("timestamp", DateHelpUtils.getCurrentTime());

                    paramMap.put("timestamp", DateHelpUtils.getCurrentTime());
                    String token = request.header("token");
                    String paramJson = GsonUtils.toJson(paramMap);
                    Log.e("xy", "添加参数后数据 token:" + token + "<--paramJson:" + paramJson);

                    //对新的构造器进行加密处理
                    RNCryptorNative rncryptor = new RNCryptorNative();
                    String encryptedToken = new String(rncryptor.encrypt(token, ENCRYPTION_PASSWORD));
                    String encryptedParam = new String(rncryptor.encrypt(paramJson, ENCRYPTION_PASSWORD));
                    Log.e("xy", "加密后数据 encryptedToken:" + token + "<--encryptedParam:" + encryptedParam);

                    String decrypted = rncryptor.decrypt(encryptedParam, RetrofitFactory.ENCRYPTION_PASSWORD);
                    Log.e("xy", "解密后数据 decrypted:" + decrypted);

                    newFormBuilder.add("token", token);
                    newFormBuilder.add("param", encryptedParam);

//                    request = request.newBuilder().post(formBody).build();

                    RequestBody newFormBody = newFormBuilder.build();
                    builder = request.newBuilder();
                    builder.post(newFormBody);

                }
            }
//            return chain.proceed(request);
            return chain.proceed(builder.build());
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
            RequestBody requestBody = request.body();
            FormBody.Builder newFormBuilder = new FormBody.Builder();
            String url = request.url().toString();
            Log.e("xy", "请求url：" + url);
            if (requestBody instanceof FormBody) {
                FormBody formBody = (FormBody) requestBody;
                for (int i = 0; i < formBody.size(); i++) {
                    if (TextUtils.equals("param", formBody.name(i))) { //只对param参数做加密
                        String paramJson = formBody.value(i); //原始的json， 做加密
                        Log.e("xy", "加密前数据：" + paramJson);

                        RNCryptorNative rncryptor = new RNCryptorNative();
                        String encrypted = new String(rncryptor.encrypt(paramJson, ENCRYPTION_PASSWORD));
                        Log.e("xy", "加密后数据：" + encrypted);

//                        String decrypted = rncryptor.decrypt(encrypted, ENCRYPTION_PASSWORD);
//                        Log.e("xy", "解密后数据：" + decrypted);

                        newFormBuilder.add("param", encrypted); //加密之后添加
                    } else if (TextUtils.equals("token", formBody.name(i))) {//token 不用加密
                        newFormBuilder.add(formBody.name(i), formBody.value(i));
                    }
                }
            }
            RequestBody newFormBody = newFormBuilder.build();
            Request.Builder builder = request.newBuilder();
            builder.post(newFormBody);
            return chain.proceed(builder.build());
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
//                    System.out.println("request参数: " + params + "\n token:" + request.header("token"));
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
