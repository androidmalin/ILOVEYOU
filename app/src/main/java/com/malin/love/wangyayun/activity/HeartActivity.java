package com.malin.love.wangyayun.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.malin.love.wangyayun.R;
import com.malin.love.wangyayun.util.DeviceInfo;
import com.malin.love.wangyayun.util.UnBindResourceUtil;
import com.malin.love.wangyayun.view.bluesnow.FlowerView;
import com.malin.love.wangyayun.view.typewriter.TypeTextView;
import com.malin.love.wangyayun.view.typewriter.TypeTextView.OnTypeViewListener;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import tyrantgit.widget.HeartLayout;

public class HeartActivity extends Activity {
    private static final String LOVE = "林真心，虽然你又矮又笨，还喜欢别的男生。即使这样，我还是，很喜欢你。--马琳";
    private static final int SNOW_BLOCK = 1;
    public static final String URL = "file:///android_asset/index.html";
    private FlowerView mBlueSnowView;//蓝色的雪花
    private Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            mBlueSnowView.inva();
        }
    };
    private HeartLayout mHeartLayout;//垂直方向的漂浮的红心
    private Random mRandom = new Random();
    private Random mRandom2 = new Random();
    private TimerTask mTask = null;
    private TypeTextView mTypeTextView;//打字机
    private WebSettings mWebSettings;
    private WebView mWebView;
    private Timer myTimer = null;
    private FrameLayout mWebViwFrameLayout = null;
    private View mRootView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DeviceInfo.getInstance().initializeScreenInfo(this);
        setContentView(R.layout.heart_layout);
        initView();
        initWebView();
    }


    private void initWebView() {
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setBuiltInZoomControls(false);
        mWebSettings.setSupportZoom(false);
        mWebView.setHapticFeedbackEnabled(false);
    }

    private void initView() {
        mRootView = findViewById(R.id.root_layout_heart_layout);
        mWebViwFrameLayout = (FrameLayout) findViewById(R.id.fl_webView_layout);
        mWebView = new WebView(getApplicationContext());
        mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setVisibility(View.GONE);

        FrameLayout.LayoutParams fp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        fp.gravity = Gravity.CENTER;
        mWebViwFrameLayout.addView(mWebView);

        mHeartLayout = (HeartLayout) findViewById(R.id.heart_o_red_layout);
        mTypeTextView = (TypeTextView) findViewById(R.id.typeTextView);
        mBlueSnowView = (FlowerView) findViewById(R.id.flowerview);
        mBlueSnowView.setWH(DeviceInfo.mScreenWidthForPortrait, DeviceInfo.mScreenHeightForPortrait, DeviceInfo.mDensity);
        mBlueSnowView.loadFlower();
        mBlueSnowView.addRect();
        myTimer = new Timer();
        mTask = new TimerTask() {
            public void run() {
                Message msg = new Message();
                msg.what = SNOW_BLOCK;
                mHandler.sendMessage(msg);
            }
        };
        myTimer.schedule(mTask, 3000, 10);
        mTypeTextView.setOnTypeViewListener(new OnTypeViewListener() {
            public void onTypeStart() {
            }

            public void onTypeOver() {
                delayShowTheSnow();
            }
        });
        mTypeTextView.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UnBindResourceUtil.unBindDrawables(mRootView);
        UnBindResourceUtil.unBindDrawables(mRootView);
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        cancelTimer();
        if (mWebView != null) {
            if (mWebViwFrameLayout != null) {
                mWebViwFrameLayout.removeAllViewsInLayout();
                mWebViwFrameLayout.removeAllViews();
            }
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }

    private void cancelTimer() {
        if (myTimer != null) {
            myTimer.cancel();
            myTimer = null;
        }
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        gotoNext();
    }

    private void gotoNext() {
        mWebView.loadUrl(URL);
        delayDo();
    }

    private void delayShow(long time) {
        Observable.timer(time, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    public void onCompleted() {
                        mTypeTextView.start(LOVE);
                    }

                    public void onError(Throwable e) {
                    }

                    public void onNext(Long aLong) {
                    }
                });
    }


    private void delayShowTheSnow() {
        Observable.timer(200, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    public void onCompleted() {
                        mBlueSnowView.setVisibility(View.VISIBLE);
                        showRedHeartLayout();
                    }

                    public void onError(Throwable e) {
                    }

                    public void onNext(Long aLong) {
                    }
                });

    }

    private void delayDo() {
        Observable.timer(0, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    public void onCompleted() {
                        mWebView.setVisibility(View.VISIBLE);
                        delayShow(5100);//延时显示显示打印机
                        mWebView.loadUrl(URL);
                    }

                    public void onError(Throwable e) {
                    }

                    public void onNext(Long aLong) {
                    }
                });
    }


    private int randomColor() {
        return Color.rgb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255));
    }

    private void showRedHeartLayout() {
        Observable.timer(400, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    public void onCompleted() {
                        mHeartLayout.setVisibility(View.VISIBLE);
                        delayDo2();
                    }

                    public void onError(Throwable e) {
                    }

                    public void onNext(Long aLong) {
                    }
                });
    }

    private void delayDo2() {
        Observable.timer((long) mRandom2.nextInt(200), TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    public void onCompleted() {
                        mHeartLayout.addHeart(randomColor());
                        delayDo2();
                    }

                    public void onError(Throwable e) {
                    }

                    public void onNext(Long aLong) {

                    }
                });
    }


}
