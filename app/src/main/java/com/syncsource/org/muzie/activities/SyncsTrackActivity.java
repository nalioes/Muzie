package com.syncsource.org.muzie.activities;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.syncsource.org.muzie.MuzieApp;
import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.events.TrackEvent;
import com.syncsource.org.muzie.model.MyTrack;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class SyncsTrackActivity extends AppCompatActivity {
    public static final String SYNCID = "sync_id";
    WebView webView;
    private String videoId;
    LinearLayout progressLayout;
    RelativeLayout syncContainer;
    LinearLayout reloadLayout;
    private boolean isError;
    MyTrack myTrack;
    ImageView syncImage;
    ImageView trackImage;
    TextView title;
    TextView duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syncs_track);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        trackImage = (ImageView) findViewById(R.id.sync_track_img);
        title = (TextView) findViewById(R.id.sync_title);
        duration = (TextView) findViewById(R.id.sync_duration);
        progressLayout = (LinearLayout) findViewById(R.id.progress_layout);
        syncContainer = (RelativeLayout) findViewById(R.id.sync_container);
        syncImage = (ImageView) findViewById(R.id.sync_img);
        webView = (WebView) findViewById(R.id.webView);
        reloadLayout = (LinearLayout) findViewById(R.id.reload_layout);
        reloadLayout.setVisibility(View.GONE);

        Intent intent = getIntent();
        if (intent.hasExtra(SYNCID)) {
            final MyTrack myTrack = (MyTrack) intent.getSerializableExtra(SYNCID);
            bindSyncData(myTrack);
        }

        progressLayout.setVisibility(View.VISIBLE);
        syncContainer.setVisibility(View.INVISIBLE);
        webView.setVisibility(View.INVISIBLE);

        webView.loadUrl("https://www.youtube2mp3.cc/button-api/#" + videoId + "|mp3");
        webView.setWebViewClient(new RequestWebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String s, String userAgent, String urlContent, String mime, long contentLength) {
                DownloadManager.Request requestDownload = new DownloadManager.Request(Uri.parse(s));
                requestDownload.allowScanningByMediaScanner();
                requestDownload.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                requestDownload.setDescription("");
                if (isStoragePermissionGranted()) {
                    Toast.makeText(getApplicationContext(), "Downloading File.", Toast.LENGTH_SHORT).show();
                    final String fileName = URLUtil.guessFileName(s, urlContent, mime);
                    requestDownload.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                    DownloadManager downloadManager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                    downloadManager.enqueue(requestDownload);
                }
            }
        });
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(MuzieApp.TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }

    private class RequestWebViewClient extends android.webkit.WebViewClient {
        public RequestWebViewClient() {
            super();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            if (isError) {
                webView.setVisibility(View.GONE);
                progressLayout.setVisibility(View.GONE);
                reloadLayout.setVisibility(View.VISIBLE);
                syncContainer.setVisibility(View.GONE);
            } else {
                progressLayout.setVisibility(View.INVISIBLE);
                webView.setVisibility(View.VISIBLE);
                syncContainer.setVisibility(View.VISIBLE);
            }

//            htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"button_api.css\" />";
//            webView.loadUrl("file:///android_asset/api.html");
//            webView.loadDataWithBaseURL("file:///android_asset/.",htmlData,"text/html","UTF-8",null);
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            isError = true;
            super.onReceivedError(view, request, error);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void eventSync(TrackEvent.OnSyncEvent event) {

        if (event.isSuccess()) {
            Toast.makeText(SyncsTrackActivity.this, "Event Reached", Toast.LENGTH_SHORT).show();
            myTrack = event.getMyTrack();
            bindSyncData(myTrack);
        }
    }

    public void bindSyncData(MyTrack track) {
        if (track != null) {

            videoId = track.getVideoID();
            title.setText(track.getTitle().toString());
            Glide.with(getApplicationContext())
                    .load(track.getThumbnail())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(syncImage);
            Glide.with(getApplicationContext())
                    .load(track.getThumbnail())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(trackImage);
            duration.setText("5:20");
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(MuzieApp.TAG, "Permission is granted");
                return true;
            } else {

                Log.v(MuzieApp.TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(SyncsTrackActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(MuzieApp.TAG, "Permission is granted");
            return true;
        }


    }
}
