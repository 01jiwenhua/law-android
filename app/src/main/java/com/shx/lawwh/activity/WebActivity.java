package com.shx.lawwh.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shx.lawwh.R;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.common.CommonValues;
import com.shx.lawwh.common.LogGloble;
import com.shx.lawwh.common.SystemConfig;
import com.shx.lawwh.entity.response.ResponseUserInfo;
import com.shx.lawwh.libs.dialog.ToastUtil;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;
import com.shx.lawwh.utils.SharedPreferencesUtil;


public class WebActivity extends BaseActivity {
    private WebView webView;
    private String url;
    /**
     * 用来控制字体大小
     */
    int fontSize = 1;
    private boolean isCollect= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        getTopbar().setTitle("详情");
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getTopbar().setRightImageVisibility(View.VISIBLE);
        final String typeCode=getIntent().getStringExtra("typeCode");
        final int id=getIntent().getIntExtra("lawId",-1);
        final ResponseUserInfo userInfo= (ResponseUserInfo) SharedPreferencesUtil.readObject(this, CommonValues.USERINFO);
        int isFavorite=getIntent().getIntExtra("is_favorite",-1);
        //先检查是否收藏过
        if(isFavorite==1){
            isCollect=true;
            getTopbar().setRightImage(R.drawable.ic_collect);
        }else{
            isCollect=false;
            getTopbar().setRightImage(R.drawable.ic_uncollect);
        }
        getTopbar().setRightImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCollect){
                    getTopbar().setRightImage(R.drawable.ic_uncollect);
                    RequestCenter.cancelFavorite(typeCode,userInfo.getId(),id,WebActivity.this);
                    isCollect=!isCollect;
                }else{
                    getTopbar().setRightImage(R.drawable.ic_collect);
                    RequestCenter.addFavorite(typeCode,userInfo.getId(),id,WebActivity.this);
                    isCollect=!isCollect;
                }

            }
        });
        webView = (WebView) findViewById(R.id.webView);
        url = getIntent().getStringExtra("URL");
        LogGloble.d("web",url);
//        url="http://192.168.1.127:8080/files/11.html";
        if (!url.startsWith("http")) {
//            if (url.startsWith("0")) {
//                url = url.substring(url.indexOf("0") + 1);
//            }
            url = String.format(SystemConfig.URL, url);

            LogGloble.d("url", url + "==");
        }
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.loadUrl(url);
    }

    private void init() {
        WebSettings settings = webView.getSettings();
//        settings.setUseWideViewPort(true);
//        settings.setLoadWithOverviewMode(true);
//        settings.setSupportZoom(true);
//        settings.setAllowFileAccess(true);
//        // 设置是否可缩放
//        settings.setBuiltInZoomControls(true);
//        settings.setJavaScriptEnabled(true);
//        settings.setDisplayZoomControls(false); //隐藏webview缩放按钮
//        settings.setSupportZoom(true);  //支持缩放
//        webView.requestFocus();
//        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
//        if (settings.getTextSize() == WebSettings.TextSize.SMALLEST) {
//            fontSize = 50 * 2;
//        } else if (settings.getTextSize() == WebSettings.TextSize.SMALLER) {
//            fontSize = 75 * 2;
//        } else if (settings.getTextSize() == WebSettings.TextSize.NORMAL) {
//            fontSize = 100 * 2;
//        } else if (settings.getTextSize() == WebSettings.TextSize.LARGER) {
//            fontSize = 150 * 2;
//        } else if (settings.getTextSize() == WebSettings.TextSize.LARGEST) {
//            fontSize = 200 * 2;
//        }
//        settings.setTextZoom(fontSize);
        initSettings(settings);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

//    void initSettings(WebSettings webSettings) {
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
//        // 开启 DOM storage API 功能
//        webSettings.setDomStorageEnabled(true);
//        //开启 database storage API 功能
//        webSettings.setDatabaseEnabled(true);
//        String cacheDirPath = getFilesDir().getAbsolutePath()+"webCache";
//        //      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
//        Log.i("WEB", "cacheDirPath="+cacheDirPath);
//        //设置数据库缓存路径
//        webSettings.setDatabasePath(cacheDirPath);
//        //设置  Application Caches 缓存目录
//        webSettings.setAppCachePath(cacheDirPath);
//        //开启 Application Caches 功能
//        webSettings.setAppCacheEnabled(true);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webSettings.setUseWideViewPort(true);//关键点
//
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//
//        webSettings.setDisplayZoomControls(false);
//        webSettings.setAllowFileAccess(true); // 允许访问文件
//        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
//        webSettings.setSupportZoom(true); // 支持缩放
//
//
//        webSettings.setLoadWithOverviewMode(true);
//
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        int mDensity = metrics.densityDpi;
//        LogGloble.d("maomao", "densityDpi = " + mDensity);
//        if (mDensity == 240) {
//            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        } else if (mDensity == 160) {
//            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
//        } else if (mDensity == 120) {
//            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
//        } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
//            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        } else if (mDensity == DisplayMetrics.DENSITY_TV) {
//            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        } else {
//            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
//        }
//        webSettings.setDefaultFontSize(20);
//
///**
// * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
// * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
// */
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//    }
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
void initSettings(WebSettings webSettings) {
    webSettings.setJavaScriptEnabled(true);
    webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
    webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
    // 开启 DOM storage API 功能
    webSettings.setDomStorageEnabled(true);
    //开启 database storage API 功能
    webSettings.setDatabaseEnabled(false);
    String cacheDirPath = getFilesDir().getAbsolutePath()+"webCache";
    //      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
    Log.i("WEB", "cacheDirPath="+cacheDirPath);
    //设置数据库缓存路径
    webSettings.setDatabasePath(cacheDirPath);
    //设置  Application Caches 缓存目录
    webSettings.setAppCachePath(cacheDirPath);
    //开启 Application Caches 功能
    webSettings.setAppCacheEnabled(true);
    webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    webSettings.setUseWideViewPort(true);//关键点

    webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

    webSettings.setDisplayZoomControls(false);
    webSettings.setAllowFileAccess(true); // 允许访问文件
    webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
    webSettings.setSupportZoom(true); // 支持缩放


    webSettings.setLoadWithOverviewMode(true);

    DisplayMetrics metrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(metrics);
    int mDensity = metrics.densityDpi;
    LogGloble.d("maomao", "densityDpi = " + mDensity);
    if (mDensity == 240) {
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
    } else if (mDensity == 160) {
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
    } else if (mDensity == 120) {
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
    } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
    } else if (mDensity == DisplayMetrics.DENSITY_TV) {
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
    }else {
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
    }
    webSettings.setDefaultFontSize(20);


/**
 * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
 * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
 */
    webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
}

    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();//返回上一页面
                return true;
            } else {
                onBackPressed();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        if(requestUrl.equals(RequestCenter.ADD_FAVORITE)) {
            ToastUtil.getInstance().toastInCenter(this,"收藏成功!");
        }else if(requestUrl.equals(RequestCenter.CANCEL_FAVORITE)){
            ToastUtil.getInstance().toastInCenter(this,"取消收藏！");
        }
        return super.doSuccess(respose, requestUrl);
    }
}
