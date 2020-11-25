package com.zhowin.base_library.http;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {


    public static final int DEFAULT_TIME = 30;
    public static RetrofitFactory httpUtils;
    private OkHttpClient okHttpClient;


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
                .addInterceptor(new LogInterceptor())//添加打印拦截器
//                .addNetworkInterceptor(httpLoggingInterceptor)
                .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                .build();
    }

    public class LogInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            RequestBody body = request.body();
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
                    System.out.println("request参数: " + params + "\n Authorization:" + request.header("Authorization"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("request  url:" + request.url().toString());
            Response response = chain.proceed(chain.request());
            MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            System.out.println("request  response:" + content);
            return response.newBuilder()
                    .body(ResponseBody.create(mediaType, content))
                    .build();
        }
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
