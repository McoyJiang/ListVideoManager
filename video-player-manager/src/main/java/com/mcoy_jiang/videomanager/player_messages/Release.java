package com.mcoy_jiang.videomanager.player_messages;

import android.media.MediaPlayer;

import com.mcoy_jiang.videomanager.PlayerMessageState;
import com.mcoy_jiang.videomanager.manager.VideoPlayerManagerCallback;
import com.mcoy_jiang.videomanager.ui.VideoPlayerView;

/**
 * This PlayerMessage calls {@link MediaPlayer#release()} on the instance that is used inside {@link VideoPlayerView}
 */
public class Release extends PlayerMessage {

    public Release(VideoPlayerView videoPlayerView, VideoPlayerManagerCallback callback) {
        super(videoPlayerView, callback);
    }

    @Override
    protected void performAction(VideoPlayerView currentPlayer) {
        currentPlayer.release();
    }

    @Override
    protected PlayerMessageState stateBefore() {
        return PlayerMessageState.RELEASING;
    }

    @Override
    protected PlayerMessageState stateAfter() {
        return PlayerMessageState.RELEASED;
    }
}
