package com.dima.mayakalarm.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import com.dima.mayakalarm.R;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class Player {

    private final SimpleExoPlayer player;
    private MediaPlayer mediaPlayer;

    public Player(Context context) {

        DataSource.Factory dataSourceFactory = new DefaultHttpDataSourceFactory();
        String radioUrl = "https://icecast-vgtrk.cdnvideo.ru/mayakfm_aac_64kbps";
        Uri URI = Uri.parse(radioUrl);
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(URI));
        player = new SimpleExoPlayer.Builder(context).build();
        player.setMediaSource(mediaSource);

        mediaPlayer = MediaPlayer.create(context, R.raw.alarm);
        mediaPlayer.setLooping(true);
    }

    public void play() {
        player.prepare();
        player.setPlayWhenReady(true);
        player.addListener(new ExoPlayer.EventListener(){
            @Override
            public void onPlayerError(ExoPlaybackException error) {
                        mediaPlayer.start();
            }
        });

    }

    public void stop() {
        player.stop();
        mediaPlayer.stop();
    }
}
