package com.mcoy_jiang.videomanager.manager;

import com.mcoy_jiang.videomanager.PlayerMessageState;
import com.mcoy_jiang.videomanager.meta.MetaData;
import com.mcoy_jiang.videomanager.player_messages.PlayerMessage;
import com.mcoy_jiang.videomanager.ui.VideoPlayerView;

/**
 * This callback is used by {@link PlayerMessage}
 * to get and set data it needs
 */
public interface VideoPlayerManagerCallback {

    void setCurrentItem(MetaData currentItemMetaData, VideoPlayerView newPlayerView);

    void setVideoPlayerState(VideoPlayerView videoPlayerView, PlayerMessageState playerMessageState);

    PlayerMessageState getCurrentPlayerState();
}
