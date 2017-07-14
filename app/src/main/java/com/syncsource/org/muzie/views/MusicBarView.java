package com.syncsource.org.muzie.views;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.ScTrackActivity;
import com.syncsource.org.muzie.databinding.ViewPlayingTrackBinding;
import com.syncsource.org.muzie.model.SCMusic;
import com.syncsource.org.muzie.utils.MediaSyncInterface;
import com.syncsource.org.muzie.utils.MuzieMediaPlayer;
import com.wang.avi.AVLoadingIndicatorView;

import static com.syncsource.org.muzie.utils.MuzieMediaPlayer.mediaPlayer;

/**
 * Created by nls on 7/5/17.
 */

public class MusicBarView {
    static AVLoadingIndicatorView avi;
    static SCMusic scMusic = null;

    public void displayPlayBar(final AppCompatActivity compat, final Context context, LinearLayout container) {

        final ViewPlayingTrackBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_playing_track, null, false);
        avi = (AVLoadingIndicatorView) binding.getRoot().findViewById(R.id.equailizer);

        MuzieMediaPlayer.setSyncMediaListener(new MediaSyncInterface() {
            @Override
            public void getMusic(SCMusic music) {
                binding.artistName.setText(music.getArtist_name());
                binding.title.setText(music.getTitle());
                Glide.with(context)
                        .load(music.getThumbnail())
                        .asBitmap()
                        .into(binding.image);
                scMusic = music;
            }
        });

        if (scMusic != null) {
            binding.artistName.setText(scMusic.getArtist_name());
            binding.title.setText(scMusic.getTitle());
            Glide.with(context)
                    .load(scMusic.getThumbnail())
                    .asBitmap()
                    .into(binding.image);
        }

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ScTrackActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(ScTrackActivity.SCYNCID, scMusic);
                context.startActivity(intent);

                compat.overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        stopAnim();
        if (!MuzieMediaPlayer.isPrepareComplete) {
            binding.play.setImageResource(R.drawable.ic_play_delay);
            binding.play.setEnabled(false);
        } else {
            binding.play.setEnabled(true);
        }

        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                    startAnim();
                    binding.play.setImageResource(R.drawable.ic_pause);
                }
            } else {
                if (mediaPlayer != null) {
                    mediaPlayer.pause();
                    stopAnim();
                    binding.play.setImageResource(R.drawable.ic_play);
                }
            }
        }

        MuzieMediaPlayer.setMediaStateListener(new MuzieMediaPlayer.MediaStateListener() {
            @Override
            public void isStoping(boolean isStoping) {
                if (isStoping) {
                    binding.play.setImageResource(R.drawable.ic_play);
                    binding.play.setEnabled(true);
                    stopAnim();
                }
            }
        });
        MuzieMediaPlayer.setMediaListener(new MuzieMediaPlayer.MediaListener() {
            @Override
            public void isOnPrepared(boolean prepare) {
                binding.play.setImageResource(R.drawable.ic_play);
                binding.play.setEnabled(true);
                stopAnim();
            }
        });

        MuzieMediaPlayer.setInitializeListener(new MuzieMediaPlayer.MediaInitializeListener() {
            @Override
            public void isInitialize(boolean isIntialize) {
                binding.play.setImageResource(R.drawable.ic_play_delay);
                binding.play.setEnabled(false);
                stopAnim();
            }
        });

        binding.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        if (mediaPlayer != null) {
                            mediaPlayer.pause();
                            stopAnim();
                            binding.play.setImageResource(R.drawable.ic_play);
                        }
                    } else {
                        if (mediaPlayer != null) {
                            mediaPlayer.start();
                            startAnim();
                            binding.play.setImageResource(R.drawable.ic_pause);
                        }
                    }
                }
            }
        });
        container.removeAllViews();
        container.addView(binding.getRoot());
    }

    static void startAnim() {
        avi.show();
        avi.smoothToShow();
    }

    static void stopAnim() {
        avi.hide();
        avi.smoothToHide();
    }


}
