package com.mcoy_jiang.videomanager.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mcoy_jiang.videomanager.Config;
import com.mcoy_jiang.videomanager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.R;

/**
 * Created by axing on 16/7/9.
 */
public class McoyVideoView extends RelativeLayout implements MediaPlayerWrapper.MainThreadMediaPlayerListener, View.OnClickListener {

    private SingleVideoPlayerManager videoManager;
    private LayoutInflater inflater;
    private VideoPlayerView videoView;
    private UniversalMediaController videoController;
    private PieImageView mcoyPieImage;
    private ImageView mcoyPlayImage;
    private String mVideoUrl;

    /**
     * mcoy add for VideoPlayerView setFullScreen function
     */
    private Context mContext;

    public McoyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        videoManager = SingleVideoPlayerManager.getInstance();

        initViews(context);

        initListeners();

    }

    /**
     * 外部使用McoyVideoView时，需要传入需要播放的视频地址URL
     * @param videoUrl
     */
    public void setVideoUrl(String videoUrl) {
        mVideoUrl = videoUrl;
    }

    public void playNewVideo() {
        if (videoManager != null) {
            videoManager.playNewVideo(null, videoView, mVideoUrl);
        }
    }

    private void initListeners() {
        /**
         * 设置VideoPlayerView播放进度的回调监听,主要包含以下监听：
         * onVideoSizeChangedMainThread：当视频view尺寸发生改变时
         * onVideoPreparedMainThread：当视频准备已完成时，之前需要调用prepare方法
         * onVideoCompletionMainThread:视频播放完毕时
         * onErrorMainThread：视频播放发生错误
         * onBufferingUpdateMainThread:缓冲发生改变
         * onVideoStoppedMainThread：视屏播放停止时
         * onInfo：视屏播放的信息发生改变(包括缓冲开始，缓冲结束)
         *
         */
        videoView.addMediaPlayerListener(this);
    }

    private void initViews(Context context) {
        inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.layout_mcoy_videoview, this);

        mcoyPieImage = ((PieImageView) findViewById(R.id.mcoyPieImage));
        mcoyPlayImage = ((ImageView) findViewById(R.id.mcoyPlayImage));
        mcoyPieImage.setOnClickListener(this);

        /**
         * 分别初始化VideoPlayerView和MediaController
         */
        videoView = ((VideoPlayerView) findViewById(R.id.mcoyVideoView));
        videoView.setParent(this);
        videoController = ((UniversalMediaController) findViewById(R.id.mcoyVideoController));

        /**
         * 将VideoPlayerView和MediaController进行绑定
         */
        videoView.setMediaController(videoController);
        videoController.setMediaPlayer(videoView);
    }

    @Override
    public void onVideoSizeChangedMainThread(int width, int height) {
        Log.e("TAG", "onVideoSizeChangedMainThread: ");
    }

    @Override
    public void onVideoPreparedMainThread() {
        Log.e("TAG", "onVideoPreparedMainThread: ");
    }

    @Override
    public void onVideoCompletionMainThread() {
        Log.e("TAG", "onVideoCompletionMainThread: ");

        if (isFullScreen) {
            setFullscreen(false);
        }
        mcoyPieImage.setVisibility(VISIBLE);
        mcoyPlayImage.setVisibility(VISIBLE);
    }

    @Override
    public void onErrorMainThread(int what, int extra) {
        Log.e("TAG", "onErrorMainThread: ");
    }

    @Override
    public void onBufferingUpdateMainThread(int percent) {
        Log.e("TAG", "onBufferingUpdateMainThread: ");
    }

    @Override
    public void onVideoStoppedMainThread() {
        Log.e("TAG", "onVideoStoppedMainThread: ");
        mcoyPieImage.setVisibility(VISIBLE);
        mcoyPlayImage.setVisibility(VISIBLE);
    }

    @Override
    public void onInfo(int what, int extra) {
        Log.e("TAG", "onInfo: ");
    }

    @Override
    public void onClick(View v) {
        mcoyPieImage.setVisibility(INVISIBLE);
        mcoyPlayImage.setVisibility(INVISIBLE);

        playNewVideo();
    }

    private boolean isFullScreen;
    private float xScale;
    private float yScale;

    public void setFullscreen(boolean fullscreen) {
        int screenOrientation = fullscreen ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setFullscreen(fullscreen, screenOrientation);
    }

    /**
     *
     * @param fullScreen
     * @param screenOrientation 此参数暂时用不到，后续可能会使用
     */
    public void setFullscreen(boolean fullScreen, int screenOrientation) {
        final RelativeLayout.LayoutParams lp = (LayoutParams) getLayoutParams();

        int videoWidth = getWidth();
        int videoHeight = getHeight();

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;

        if (fullScreen) {
            xScale = (float) (screenWidth - 20) / getHeight();
            yScale = (float) (screenHeight) / getWidth();

            if (Config.SHOW_LOGS) {
                Log.e("TAG", "onAnimationEnd: getWidth is " + getWidth() +
                        " screenWidth is " + getResources().getDisplayMetrics().widthPixels
                        + "getHeight is " + getHeight() + " screenHeight is "
                        + getResources().getDisplayMetrics().heightPixels);
                Log.e("TAG", "onAnimationEnd: xScale is " + xScale + " yScale is " + yScale);
            }

            ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(this, "rotation", getRotation(), 90);
            rotateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Float animatedValue = (Float) animation.getAnimatedValue();
                    float width = (animatedValue * xScale / 90) < 1 ? 1 : animatedValue * xScale / 90;
                    float height = (animatedValue * yScale / 90) < 1 ? 1 : animatedValue * yScale / 90;

                    if (Config.SHOW_LOGS) {
                        Log.e("TAG", "onAnimationUpdate: animatedValue is " + animatedValue + " yLoc is " + getTranslationY());
                        Log.e("TAG", "onAnimationUpdate: width is " + width + " height is " + height);
                    }

                    setScaleX(width);
                    setScaleY(height);
                }
            });
            AnimatorSet animSet = new AnimatorSet();
            animSet.setDuration(500);
            animSet.play(rotateAnim);
            animSet.start();

            isFullScreen = true;
        } else {
            ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(this, "rotation", getRotation(), 0);
            rotateAnim.setDuration(500);
            rotateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Float animatedValue = (Float) animation.getAnimatedValue();
                    Log.e("TAG", "onAnimationUpdate: animatedValue is " + animatedValue + " yLoc is " + getTranslationY());
                    float a = (animatedValue * xScale / 90) < 1 ? 1 : animatedValue * xScale / 90;
                    float b = (animatedValue * yScale / 90) < 1 ? 1 : animatedValue * yScale / 90;
                    Log.e("TAG", "onAnimationUpdate: a is " + a + " b is " + b);
                    setScaleX(a);
                    setScaleY(b);
                }
            });
            rotateAnim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    if (Build.VERSION.SDK_INT > 17) {
                        lp.removeRule(CENTER_IN_PARENT);
                        setLayoutParams(lp);
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                }
                @Override
                public void onAnimationCancel(Animator animation) {
                }
                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            rotateAnim.start();

            isFullScreen = false;
        }

    }

}
