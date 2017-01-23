package com.syncsource.org.muzie;

import android.databinding.DataBindingUtil;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.syncsource.org.muzie.databinding.ActivityScTrackBinding;
import com.syncsource.org.muzie.model.MyTrack;
import com.syncsource.org.muzie.utils.Config;
import com.syncsource.org.muzie.utils.TrackManageUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ScTrackActivity extends AppCompatActivity {
    public static final String SCYNCID = "sc_sync_id";
    private ActivityScTrackBinding binding;
    private MediaPlayer mediaPlayer;
    private MyTrack myTrack;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sc_track);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mediaPlayer = new MediaPlayer();
        handler = new Handler();

        if (getIntent().hasExtra(SCYNCID)) {
            myTrack = (MyTrack) getIntent().getSerializableExtra(SCYNCID);
            Glide.with(this)
                    .load(myTrack.getThumbnail())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.scTrackImage);
            binding.scTrackTitle.setText(myTrack.getTitle());
            binding.currentDuration.setText("00:00");
            binding.totalDuration.setText(myTrack.getDuration());
            binding.playTrack.setImageResource(R.drawable.ic_play_delay);
            binding.playTrack.setEnabled(false);
        }

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                binding.playTrack.setImageResource(R.drawable.ic_play);
                binding.playTrack.setEnabled(true);
            }
        });

        binding.playTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkingPlay();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
            }
        });

        binding.fileDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DownloadFileFromURL().execute(myTrack.getStreamUrl() + "?client_id=" + Config.CLIENT_ID);
            }
        });

        binding.progressLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeCallbacks(timeProgress);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                handler.removeCallbacks(timeProgress);
                int totalDuration = mediaPlayer.getDuration();
                int currentDuration = TrackManageUtil.progressToTimer(seekBar.getProgress(), totalDuration);
                mediaPlayer.seekTo(currentDuration);
                updateTimeProgress();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        playSong();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mediaPlayer != null) {
            handler.removeCallbacks(timeProgress);
            mediaPlayer.release();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            handler.removeCallbacks(timeProgress);
            mediaPlayer.release();
        }
    }

    class DownloadFileFromURL extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... f_url) {
            URL u = null;
            InputStream is = null;
            boolean success = false;
            try {
                u = new URL(f_url[0]);
                is = u.openStream();
                HttpURLConnection huc = (HttpURLConnection) u.openConnection();//to know the size of video
                int size = huc.getContentLength();

                if (huc != null) {
                    String fileName = myTrack.getTitle();
                    String storagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
                    File f = new File(storagePath, fileName);

                    FileOutputStream fos = new FileOutputStream(f);
                    byte[] buffer = new byte[1024];
                    long total = 0;
                    int len1 = 0;
                    if (is != null) {
                        while ((len1 = is.read(buffer)) > 0) {
                            total += len1;
                            publishProgress((int) ((total * 100) / size));
                            fos.write(buffer, 0, len1);
                        }
                    }
                    if (fos != null) {
                        fos.close();
                        success = true;
                    }
                }
            } catch (MalformedURLException mue) {
                success = false;
                mue.printStackTrace();
            } catch (IOException ioe) {
                success = false;
                ioe.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException ioe) {
                    // just going to ignore this one
                }
            }
            return success;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(ScTrackActivity.this, "Download Finished", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void playSong() {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(myTrack.getStreamUrl() + "?client_id=" + Config.CLIENT_ID);
            mediaPlayer.prepareAsync();
            binding.progressLength.setProgress(0);
            binding.progressLength.setMax(100);
            updateTimeProgress();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkingPlay() {
        if (mediaPlayer.isPlaying()) {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
                binding.playTrack.setImageResource(R.drawable.ic_play);
            }
        } else {
            if (mediaPlayer != null) {
                mediaPlayer.start();
                binding.playTrack.setImageResource(R.drawable.ic_pause);
            }
        }
    }

    private void updateTimeProgress() {
        if (mediaPlayer != null) {
            handler.postDelayed(timeProgress, 100);
        }
    }

    private Runnable timeProgress = new Runnable() {
        @Override
        public void run() {
            long totalDuration = mediaPlayer.getDuration();
            long currentDuration = mediaPlayer.getCurrentPosition();
            binding.currentDuration.setText(String.valueOf(TrackManageUtil.milliSecondsToTimer(currentDuration)));
//            binding.totalDuration.setText(String.valueOf(TrackManageUtil.milliSecondsToTimer(totalDuration)));
            int progress = Integer.valueOf(TrackManageUtil.getProgressPercentage(currentDuration, totalDuration));
            binding.progressLength.setProgress(progress);
            if (progress == 100) {
                binding.playTrack.setImageResource(R.drawable.ic_play);
            }
            handler.postDelayed(this, 100);
        }
    };
}
