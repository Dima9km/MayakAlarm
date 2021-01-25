package com.dima.mayakalarm.util;

import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class Player {

    String radioUrl = "https://icecast-vgtrk.cdnvideo.ru/mayakfm_aac_64kbps";
    Uri URI = Uri.parse(radioUrl);
    Context context;
    MediaSource mediaSource;
    SimpleExoPlayer player;

    public Player(Context context){
        this.context = context;

        DataSource.Factory dataSourceFactory = new DefaultHttpDataSourceFactory();
        mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(URI));
        player = new SimpleExoPlayer.Builder(context).build();
        player.setMediaSource(mediaSource);

    }

    public void play() {
        player.prepare();
        player.setPlayWhenReady(true);
    }

    public void stop() {
        player.stop();
    }
}
