package com.mcoy_jiang.videomanager.player_messages;

import com.mcoy_jiang.videomanager.PlayerMessageState;
import com.mcoy_jiang.videomanager.manager.VideoPlayerManagerCallback;
import com.mcoy_jiang.videomanager.ui.VideoPlayerView;

/**
 * This is generic PlayerMessage for setDataSource
 */
public abstract class SetDataSourceMessage extends PlayerMessage {

    public SetDataSourceMessage(VideoPlayerView videoPlayerView, VideoPlayerManagerCallback callback) {
        super(videoPlayerView, callback);
    }

    @Override
    protected PlayerMessageState stateBefore() {
        return PlayerMessageState.SETTING_DATA_SOURCE;
    }

    @Override
    protected PlayerMessageState stateAfter() {
        return PlayerMessageState.DATA_SOURCE_SET;
    }
}
