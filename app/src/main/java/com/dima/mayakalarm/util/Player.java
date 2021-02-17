package com.dima.mayakalarm.util;

import android.content.Context;
import android.media.AudioManager;
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
    private final MediaPlayer mediaPlayer;
    private final Context context;

    public Player(Context context) {
        this.context = context;
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

        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) / 3, 0);

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
