package com.syncsource.org.muzie;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.syncsource.org.muzie.activities.SyncsTrackActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ScTrackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sc_track);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String file_url = "https://api.soundcloud.com/tracks/78066328/stream?client_id=6dbfcdd828adf44359b78580fe4d6023";
        new DownloadFileFromURL().execute(file_url);
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
                    String fileName = "Somethings.mp3";
                    String storagePath = Environment.getExternalStorageDirectory().toString();
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


}
