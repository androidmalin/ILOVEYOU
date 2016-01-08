package com.malin.love.wangyayun.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.jakewharton.rxbinding.view.RxView;
import com.malin.love.wangyayun.R;
import com.malin.love.wangyayun.constant.Constant;
import com.malin.love.wangyayun.factory.ImageNameFactory;
import com.malin.love.wangyayun.util.DeviceInfo;
import com.malin.love.wangyayun.util.ImageUtils;
import com.malin.love.wangyayun.view.FlowerView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 类描述:主页面
 * 创建人:
 * 创建时间:16-1-7
 * 备注:
 */
public class MainActivity extends Activity {
    private static final String JPG = ".jpg";
    private FlowerView mFlowerView;
    private Timer myTimer = null;
    private TimerTask mTask = null;
    private static final int SNOW_BLOCK = 1;
    private Handler mHandler = new Handler() {
        public void dispatchMessage(Message msg) {
            mFlowerView.inva();
        }

        ;
    };

    private ImageView mImageView;
    private int mCounter;
    private Bitmap mManyBitmapSuperposition;
    private Canvas mCanvas;

    private ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DeviceInfo.getInstance().initializeScreenInfo(this);
        setContentView(R.layout.main);
        initView();
    }


    private void initView() {
        mImageView = (ImageView) findViewById(R.id.image);
        mFlowerView = (FlowerView) findViewById(R.id.flowerview);

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mFlowerView.setWH(DeviceInfo.mScreenWidthForPortrait, DeviceInfo.mScreenHeightForPortrait, DeviceInfo.mDensity);
        mFlowerView.loadFlower();
        mFlowerView.addRect();

        myTimer = new Timer();
        mTask = new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = SNOW_BLOCK;
                mHandler.sendMessage(msg);
            }
        };
        rxJavaSolveMiZhiSuoJinAndNestedLoopAndCallbackHell();
        myTimer.schedule(mTask, 3000, 10);
        clickEvent();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFlowerView.recly();
        cancleTimer();
    }


    private void cancleTimer() {

        if (myTimer != null) {
            myTimer.cancel();
            myTimer = null;
        }

        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
    }

    /**
     * 就是循环在画布上画图,呈现一种整齐的线性分布:像方格
     * 所有绘制都绘制到了创建Canvas时传入的Bitmap上面
     *
     * @param bitmap:每张图片对应的Bitamp
     * @param mCounter:一个自增的整数从0开始
     */
    //实现思路:
    //1:产生和手机屏幕尺寸同样大小的Bitmap
    //2:以Bitmap对象创建一个画布，将内容都绘制在Bitmap上,这个Bitmap用来存储所有绘制在Canvas上的像素信息.
    //3:这里将所有图片压缩成了相同的尺寸均为正方形图(64px*64px)
    //4:计算获取绘制每个Bitmap的坐标,距离屏幕左边和上边的距离,距离左边的距离不断自增,距离顶部的距离循环自增
    //5:将Bitmap画到指定坐标
    private void createSingleImageFromMultipleImages(Bitmap bitmap, int mCounter) {
        if (mCounter == 0) {
            //1:产生和手机屏幕尺寸同样大小的Bitmap
            mManyBitmapSuperposition = Bitmap.createBitmap(DeviceInfo.mScreenWidthForPortrait, DeviceInfo.mScreenHeightForPortrait, bitmap.getConfig());

            //2:以Bitmap对象创建一个画布，则将内容都绘制在Bitmap上
            mCanvas = new Canvas(mManyBitmapSuperposition);
        }
        if (mCanvas != null) {
            int left;//距离左边的距离
            int top;//距离顶部的距离

            //3:这里将所有图片压缩成了相同的尺寸均为正方形图(64px*64px)
            int imageWidth = Constant.IMAGE_WITH;
            int imageHeight = Constant.IMAGE_HEIGHT;
            int number = DeviceInfo.mScreenHeightForPortrait / imageHeight;//手机竖屏模式下,垂直方向上绘制图片的个数

            //4:计算获取绘制每个Bitmap的坐标,距离屏幕左边和上边的距离,距离左边的距离不断自增,距离顶部的距离循环自增
            if (mCounter >= (mCounter / number) * number && mCounter < (((mCounter / number) + 1) * number)) {//[0,number)
                left = (mCounter / number) * imageWidth;
                top = (mCounter % number) * imageHeight;
                // Log.d(TAG,""+mCounter+" left="+left+" top="+top);

                //5:将Bitmap画到指定坐标
                mCanvas.drawBitmap(bitmap, left, top, null);
            }
        }
    }


    //-----------------------------------RxJava的实现--链式调用--十分简洁 -----------------------------------------------------------


    private void rxJavaSolveMiZhiSuoJinAndNestedLoopAndCallbackHell() {
        //1:被观察者:

        //2:数据转换

        //3:设置事件的产生发生在IO线程

        //4:设置事件的消费发生在主线程

        //5:观察者

        //6:订阅:被观察者被观察者订阅

        Observable.from(ImageNameFactory.getAssetImageFolderName())
                //assets下一个文件夹的名称,assets下一个文件夹中一张图片的路径
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String folderName) {
                        return Observable.from(ImageUtils.getAssetsImageNamePathList(getApplicationContext(), folderName));
                    }
                })
                        //过滤,筛选出jpg图片
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String imagePathNameAll) {
                        return imagePathNameAll.endsWith(JPG);
                    }
                })
                        //将图片路径转换为对应图片的Bitmap
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String imagePathName) {
                        return ImageUtils.getImageBitmapFromAssetsFolderThroughImagePathName(getApplicationContext(), imagePathName, DeviceInfo.mScreenWidthForPortrait, DeviceInfo.mScreenHeightForPortrait);
                    }
                })
                .map(new Func1<Bitmap, Void>() {
                    @Override
                    public Void call(Bitmap bitmap) {
                        createSingleImageFromMultipleImages(bitmap, mCounter);
                        mCounter++;
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())//设置事件的产生发生在IO线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//设置事件的消费发生在主线程
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        mImageView.setImageBitmap(mManyBitmapSuperposition);
                        mProgressBar.setVisibility(View.GONE);
                        showAllViews();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Void aVoid) {

                    }
                });
    }

    private void showAllViews() {
        mImageView.setVisibility(View.VISIBLE);
        mFlowerView.setVisibility(View.VISIBLE);
    }


    private void clickEvent() {

        RxView.clicks(mImageView)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        startActivity(new Intent(MainActivity.this, LoveWebViewActivity.class));
                    }
                });
    }
}