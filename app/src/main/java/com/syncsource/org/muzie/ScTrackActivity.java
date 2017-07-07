package com.syncsource.org.muzie;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.syncsource.org.muzie.databinding.ActivityScTrackBinding;
import com.syncsource.org.muzie.model.MyTrack;
import com.syncsource.org.muzie.model.SCMusic;
import com.syncsource.org.muzie.utils.Config;
import com.syncsource.org.muzie.utils.MuzieMediaPlayer;
import com.syncsource.org.muzie.utils.TrackManageUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.syncsource.org.muzie.utils.MuzieMediaPlayer.mediaPlayer;

public class ScTrackActivity extends AppCompatActivity {
    public static final String SCYNCID = "sc_sync_id";
    private ActivityScTrackBinding binding;
    //    private MediaPlayer mediaPlayer;
    private SCMusic myTrack;
    private Handler handler;
    private static MuzieMediaPlayer muzieMediaPlayer;
    public static boolean serviceBound = false;
    public static final String Broadcast_PLAY_NEW_AUDIO = "com.syncsource.org.muzie.PlayNewAudio";
    public static ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MuzieMediaPlayer.ClientBinder clientBInder = (MuzieMediaPlayer.ClientBinder) service;
            muzieMediaPlayer = clientBInder.getService();
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sc_track);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        muzieMediaPlayer = MuzieMediaPlayer.getPlayerInstance();
        handler = new Handler();
        if (getIntent().hasExtra(SCYNCID)) {
            myTrack = (SCMusic) getIntent().getSerializableExtra(SCYNCID);
            Glide.with(this)
                    .load(myTrack.getThumbnail())
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.scTrackImage);

            binding.scTrackTitle.setText(myTrack.getTitle());
            binding.currentDuration.setText("00:00");
            binding.subTitle.setText(myTrack.getArtist_name());
            binding.totalDuration.setText(myTrack.getDuration());

        }
        if (!MuzieMediaPlayer.isPrepareComplete) {
            binding.playTrack.setImageResource(R.drawable.ic_play_delay);
            binding.playTrack.setEnabled(false);
        } else {
            binding.playTrack.setEnabled(true);
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    if (mediaPlayer != null) {
                        mediaPlayer.start();
                        binding.playTrack.setImageResource(R.drawable.ic_pause);
                    }
                } else {
                    if (mediaPlayer != null) {
                        mediaPlayer.pause();
                        binding.playTrack.setImageResource(R.drawable.ic_play);

                    }
                }
                updateTimeProgress();
            }
        }

        if (mediaPlayer != null) {
            if (MuzieMediaPlayer.getId() != 0) {

                if (MuzieMediaPlayer.getId() == myTrack.getId()) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                        binding.playTrack.setImageResource(R.drawable.ic_pause);
                        updateTimeProgress();
                    }
                } else {
                    mediaPlayer.reset();
                    MuzieMediaPlayer.setId(myTrack.getId());
                    playAudio(myTrack.getStreamUrl() + "?client_id=" + Config.CLIENT_ID);
                }
            } else {
                MuzieMediaPlayer.setId(myTrack.getId());
                playAudio(myTrack.getStreamUrl() + "?client_id=" + Config.CLIENT_ID);
            }

        }

        MuzieMediaPlayer.setMediaListener(new MuzieMediaPlayer.MediaListener() {
            @Override
            public void isOnPrepared(boolean prepare) {
                if (prepare) {
                    binding.playTrack.setImageResource(R.drawable.ic_play);
                    binding.playTrack.setEnabled(true);
                }

            }
        });

        binding.playTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkingPlay();
            }
        });

        binding.fileDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ScTrackActivity.this, "download", Toast.LENGTH_SHORT).show();
                new DownloadFileFromURL().execute(myTrack.getStreamUrl() + "?client_id=" + Config.CLIENT_ID);
            }
        });
        binding.progressLength.setIndeterminateDrawable(getResources().getDrawable(R.drawable.gradient));
        binding.progressLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                binding.progressLength.setThumb(getResources().getDrawable(R.drawable.seekbar_thumb_activite));
                handler.removeCallbacks(timeProgress);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                binding.progressLength.setThumb(getResources().getDrawable(R.drawable.seekbar_thumb));
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
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mediaPlayer != null) {
//     handler.removeCallbacks(timeProgress);
        }
        overridePendingTransition(R.anim.back_enter, R.anim.back_exit);
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
                    String fileName = myTrack.getTitle() + ".mp3";
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("ServiceState", serviceBound);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        serviceBound = savedInstanceState.getBoolean("ServiceState");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceBound) {
//            unbindService(serviceConnection);
            //service is active
//            muzieMediaPlayer.stopSelf();
        }
    }

    private void playSong() {

    }

    public void playAudio(String media) {
        //Check is service is active
        if (!serviceBound) {

            Intent playerIntent = new Intent(this, MuzieMediaPlayer.class);
            playerIntent.putExtra("media", media);
            startService(playerIntent);
            bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE);
            binding.progressLength.setProgress(0);
            binding.progressLength.setMax(100);
            updateTimeProgress();
        } else {
            //Service is active
            //Send media with BroadcastReceiver
            Intent broadcastIntent = new Intent(Broadcast_PLAY_NEW_AUDIO);
            broadcastIntent.putExtra("media", media);
            sendBroadcast(broadcastIntent);
        }
    }

    private void checkingPlay() {
        if (mediaPlayer != null) {
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
            updateTimeProgress();
        }
    }

    public void updateTimeProgress() {
        if (mediaPlayer != null) {
            handler.postDelayed(timeProgress, 100);
        }
    }

    private Runnable timeProgress = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
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

            }

        }
    };

}
