package com.mcoy_jiang.videomanager.player_messages;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import com.mcoy_jiang.videomanager.manager.VideoPlayerManagerCallback;
import com.mcoy_jiang.videomanager.ui.VideoPlayerView;

/**
 * This PlayerMessage calls {@link MediaPlayer#setDataSource(Context, Uri)} on the instance that is used inside {@link VideoPlayerView}
 */
public class SetUrlDataSourceMessage extends SetDataSourceMessage {

    private final String mVideoUrl;

    public SetUrlDataSourceMessage(VideoPlayerView videoPlayerView, String videoUrl, VideoPlayerManagerCallback callback) {
        super(videoPlayerView, callback);
        mVideoUrl = videoUrl;
    }

    @Override
    protected void performAction(VideoPlayerView currentPlayer) {
        currentPlayer.setDataSource(mVideoUrl);
    }
}
