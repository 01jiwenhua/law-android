package com.shx.lawwh.activity;

import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.shx.lawwh.R;
import com.shx.lawwh.base.BaseActivity;
import com.shx.lawwh.common.CommonValues;
import com.shx.lawwh.common.LogGloble;
import com.shx.lawwh.common.SystemConfig;
import com.shx.lawwh.entity.response.ResponseUserInfo;
import com.shx.lawwh.libs.dialog.DialogManager;
import com.shx.lawwh.libs.dialog.ToastUtil;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;
import com.shx.lawwh.utils.SharedPreferencesUtil;
import com.shx.lawwh.view.PDFView;

import java.io.File;

import static com.shx.lawwh.R.id.pdfView;

public class PdfViewActivity extends BaseActivity implements OnPageChangeListener
        , OnLoadCompleteListener, OnDrawListener, OnErrorListener {
    private PDFView mView;
    private String mUrl;
    private boolean isLoaded = false;
    private boolean isCollect= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        getTopbar().setTitle("详情见");
        getTopbar().setRightImageVisibility(View.VISIBLE);
        final String typeCode=getIntent().getStringExtra("typeCode");
        final int id=getIntent().getIntExtra("lawId",-1);
        int isFavorite=getIntent().getIntExtra("is_favorite",-1);
        if(isFavorite==1){
            isCollect=true;
            getTopbar().setRightImage(R.drawable.ic_collect);
        }else{
            isCollect=false;
            getTopbar().setRightImage(R.drawable.ic_uncollect);
        }
            final ResponseUserInfo userInfo= (ResponseUserInfo) SharedPreferencesUtil.readObject(this, CommonValues.USERINFO);
            getTopbar().setRightImageListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isCollect){
                        getTopbar().setRightImage(R.drawable.ic_uncollect);
                        RequestCenter.cancelFavorite(typeCode,userInfo.getId(),id,PdfViewActivity.this);
                        isCollect=!isCollect;
                    }else{
                        getTopbar().setRightImage(R.drawable.ic_collect);
                        RequestCenter.addFavorite(typeCode,userInfo.getId(),id,PdfViewActivity.this);
                        isCollect=!isCollect;
                    }

                }
        });
        getTopbar().setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mView = (PDFView) findViewById(pdfView);
        mView.setFitsSystemWindows(true);
        mView.setMinZoom(1.0f);
        mView.setMidZoom(1.5f);
        mView.setMaxZoom(2f);
        mUrl = getIntent().getStringExtra("URL");

        mUrl = String.format(SystemConfig.PDFURL, mUrl);
        int index = mUrl.lastIndexOf("/");
        String fileName = mUrl.substring(index);
        DialogManager.getInstance().showProgressDialogNotCancelbale(this);
        mView.fromUrl(mUrl, fileName, new PDFView.FileLoadingListener() {
            @Override
            public void onFileLoaded(File file) {
                isLoaded = true;
                Log.d("PDFView", "PDF File 加载完成======");
                mView.fromFile(file)
                        .enableSwipe(true) // allows to block changing pages using swipe
                        .defaultPage(0)
                        .onError(PdfViewActivity.this)
                        // allows to draw something on the current page, usually visible in the middle of the screen
                        .onDraw(PdfViewActivity.this)
                        // allows to draw something on all pages, separately for every page. Called only for visible pages
                        .onLoad(PdfViewActivity.this) // called after document is loaded and starts to be rendered
                        .onPageChange(PdfViewActivity.this)
                        .swipeHorizontal(false)
                        .enableAntialiasing(true)
                        .load();
            }

            @Override
            public void onFileLoadFail() {
                isLoaded = false;
                DialogManager.getInstance().dissMissProgressDialog();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!isLoaded) {
            //文件没下载完成并退出该页面
            mView.cancleDownload();
            int index = mUrl.lastIndexOf("/");
            String fileName = mUrl.substring(index);
            final String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/pdf/";
            final File file = new File(SDPath, fileName);
            if (file.exists()) {
                //文件存在
                file.delete();
            }
        }
        super.onBackPressed();
    }

    @Override
    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
    }

    @Override
    public void loadComplete(int nbPages) {
        DialogManager.getInstance().dissMissProgressDialog();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }

    @Override
    public void onError(Throwable t) {
        LogGloble.d("onError", "onError");
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
