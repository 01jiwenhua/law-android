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
import com.shx.lawwh.common.LogGloble;
import com.shx.lawwh.libs.dialog.DialogManager;
import com.shx.lawwh.view.PDFView;

import java.io.File;

import static com.shx.lawwh.R.id.pdfView;

public class PdfViewActivity extends BaseActivity implements OnPageChangeListener
        , OnLoadCompleteListener, OnDrawListener, OnErrorListener {
    private PDFView mView;
    private String mUrl;
    private boolean isLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        getTopbar().setTitle("详细内容");
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
//        mUrl = "http://192.168.1.127:8080/files/7、SHT 3544-2009 石油化工对置式往复压缩机组施工及验收规范.pdf";
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
}
