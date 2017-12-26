package com.shx.lawwh.libs.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.shx.lawwh.base.BaseApplication;
import com.shx.lawwh.common.LogGloble;
import com.shx.lawwh.common.SystemConfig;
import com.shx.lawwh.libs.dialog.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created by 邵鸿轩 on 2017/7/4.
 */

public class HttpManager {
    private static HttpManager mHttpManager;
    private String TAG = "httplog";

    private HttpManager() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor(TAG))
                .connectTimeout(3 * 10000L, TimeUnit.MILLISECONDS)
                .readTimeout(3 * 10000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);


    }

    public static HttpManager getInstance() {
        if (mHttpManager == null) {
            synchronized (HttpManager.class) {
                mHttpManager = new HttpManager();

            }
        }
        return mHttpManager;
    }

    public void doDownloadFile(String url, String fileName, FileCallBack fileCallBack) {
        OkHttpUtils.get().tag("download").url(url).build().execute(fileCallBack);
    }

    public void doPost(final ZCRequest request, final HttpCallBack callBack) {
        if (!isNetworkAvailable(BaseApplication.getContext())) {
            ToastUtil.getInstance().toastInCenter(BaseApplication.getContext(), "网络异常");
            return;
        }
        String url = SystemConfig.BASEURL + request.getUrl();

        String requestStr = MyJSON.toJSONString(request.getParams());
        LogGloble.d("http", requestStr);
        OkHttpUtils
                .post()
                .url(url)
                .addParams("data", requestStr)
                .addHeader("charset", "utf-8")
                .tag(this)
                .build()
                .execute(new Callback() {
                    @NonNull
                    @Override
                    public Object parseNetworkResponse(@NonNull okhttp3.Response response, int id) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, @NonNull Exception e, int id) {
                        HttpTrowable httpTrowable = new HttpTrowable(e.getMessage(), "999999");
                        callBack.doFaild(httpTrowable, request.getUrl());
                    }

                    @Override
                    public void onResponse(Object result, int id) {
                        LogGloble.d("http：", "url--" + request.getUrl() + "--result--" + result);
                        ZCResponse response = null;
                        try {
                            response = MyJSON.parseObject((String) result, ZCResponse.class);
                            if (!httpCalllBackPreFilter(response, request.getUrl())) {
                                callBack.doSuccess(response, request.getUrl());
                            } else {
                                HttpTrowable httpTrowable = new HttpTrowable(response.getMessage(), response.getMessageCode());
                                callBack.doFaild(httpTrowable, request.getUrl());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            HttpTrowable httpTrowable = new HttpTrowable(e.getMessage(), "99999");
                            callBack.doFaild(httpTrowable, request.getUrl());
                        }
                    }
                });
    }

    /**
     * 统一过滤
     *
     * @param response
     * @param method
     * @return
     */
    public static boolean httpCalllBackPreFilter(@NonNull ZCResponse response, String method) {
        if (response.getMessageCode().equals("10000")) {
            return false;
        }
        ToastUtil.getInstance().toastInCenter(BaseApplication.getContext(), response.getMessage());
        return true;
    }

    public void doCancleDownloadFile() {
        OkHttpUtils.getInstance().cancelTag("download");
    }

    // 检测网络
    public static boolean isNetworkAvailable(@NonNull Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        NetworkInfo netWorkInfo = info[i];
                        if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            return true;
                        } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
