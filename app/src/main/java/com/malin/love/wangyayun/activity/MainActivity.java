package com.malin.love.wangyayun.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.malin.love.wangyayun.R;
import com.malin.love.wangyayun.factory.ImageNameFactory;
import com.malin.love.wangyayun.util.DeviceInfo;
import com.malin.love.wangyayun.util.ImageUtils;
import com.malin.love.wangyayun.util.UnBindResourceUtil;
import com.malin.love.wangyayun.view.whitesnow.SnowView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends Activity {
    private static final String JPG = ".jpg";
    private ImageView mImageView;//图片
    private ProgressBar mProgressBar;
    private SnowView mWhiteSnowView;//白色的雪花
    private View mRootView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DeviceInfo.getInstance().initializeScreenInfo(this);
        setContentView(R.layout.main);
        initView();
    }


    private void initView() {
        mRootView = findViewById(R.id.root_fragment_layout);
        mWhiteSnowView = (SnowView) findViewById(R.id.whiteSnowView);
        mImageView = (ImageView) findViewById(R.id.image);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        rxJavaSolveMiZhiSuoJinAndNestedLoopAndCallbackHell();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        UnBindResourceUtil.unBindDrawables(mRootView);
        UnBindResourceUtil.unBindDrawables(mRootView);
    }


    private void rxJavaSolveMiZhiSuoJinAndNestedLoopAndCallbackHell() {
        Observable.from(ImageNameFactory.getAssetImageFolderName())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    public void call() {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                })
                .flatMap(new Func1<String, Observable<String>>() {
                    public Observable<String> call(String folderName) {
                        return Observable.from(ImageUtils.getAssetsImageNamePathList(getApplicationContext(), folderName));
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    public Boolean call(String imagePathNameAll) {
                        return Boolean.valueOf(imagePathNameAll.endsWith(JPG));
                    }
                })
                .map(new Func1<String, Bitmap>() {
                    public Bitmap call(String imagePathName) {
                        return ImageUtils.getImageBitmapFromAssetsFolderThroughImagePathName(getApplicationContext(), imagePathName, DeviceInfo.mScreenWidthForPortrait, DeviceInfo.mScreenHeightForPortrait);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        mImageView.setImageBitmap(bitmap);
                        showAllViews();
                        delayShowAll(6000L);
                    }
                });
    }

    private void showAllViews() {
        mImageView.setVisibility(View.VISIBLE);
        mWhiteSnowView.setVisibility(View.VISIBLE);
    }

    private void delayShowAll(long time) {
        Observable.timer(time, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    public void onCompleted() {
                        delayDo();
                    }

                    public void onError(Throwable e) {
                    }

                    public void onNext(Long aLong) {
                    }
                });
    }

    private void delayDo() {
        startActivity(new Intent(MainActivity.this, HeartActivity.class));
    }

}
