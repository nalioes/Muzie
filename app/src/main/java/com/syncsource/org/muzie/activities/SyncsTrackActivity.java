package com.syncsource.org.muzie.activities;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;

import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

import com.syncsource.org.muzie.MuzieApp;
import com.syncsource.org.muzie.adapters.RelatedTrackAdapter;
import com.syncsource.org.muzie.analytics.AnalyticsManager;
import com.syncsource.org.muzie.fragments.SearchResultFragment;
import com.syncsource.org.muzie.model.Item;
import com.syncsource.org.muzie.model.TrackItem;
import com.syncsource.org.muzie.network.HttpDataHandler;
import com.syncsource.org.muzie.network.NetworkUtil;
import com.syncsource.org.muzie.R;
import com.syncsource.org.muzie.events.TrackEvent;
import com.syncsource.org.muzie.model.MyTrack;
import com.syncsource.org.muzie.rests.ApiClient;
import com.syncsource.org.muzie.utils.Config;
import com.syncsource.org.muzie.utils.TrackManageUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static com.syncsource.org.muzie.utils.Config.TOTAL_ITEM;


public class SyncsTrackActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    public static final String SYNCID = "sync_id";
    private YouTubePlayerView youtubeView;
    WebView webView;
    private String videoId;
    LinearLayout progressLayout;
    RelativeLayout syncContainer;
    LinearLayout reloadLayout;
    private boolean isError;
    private boolean isFetchError = false;
    private boolean isGranted = false;
    MyTrack myTrack;
    ImageView trackImage;
    TextView title;
    TextView errorMessage;
    TextView viewCount;
    Button reloadData;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    RelatedTrackAdapter adapter;
    public static final int TOTAL_ITEM = 20;
    public static final String MAX_NUMBER = "15";
    private static final int RECOVERY_REQUEST = 1;
    TextView duration;
    ApiClient apiClient;
    private List<Item> snippetItems = new ArrayList<>();
    private String token;
    private List<MyTrack> myRelatedTrackList = new ArrayList<>();
    private List<MyTrack> myTrackList = new ArrayList<>();
    private List<TrackItem> trackItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syncs_track);

        youtubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        trackImage = (ImageView) findViewById(R.id.sync_track_img);
        recyclerView = (RecyclerView) findViewById(R.id.related_recycler);
        title = (TextView) findViewById(R.id.sync_title);
        reloadData = (Button) findViewById(R.id.button_reload);
        viewCount = (TextView) findViewById(R.id.sync_view_count);
        errorMessage = (TextView) findViewById(R.id.error_message);
        duration = (TextView) findViewById(R.id.sync_duration);
        progressLayout = (LinearLayout) findViewById(R.id.progress_layout);
        syncContainer = (RelativeLayout) findViewById(R.id.sync_container);
        webView = (WebView) findViewById(R.id.webView);
        reloadLayout = (LinearLayout) findViewById(R.id.reload_layout);
        reloadLayout.setVisibility(GONE);
        apiClient = ApiClient.getApiClientInstance();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.scrollToPosition(0);
//        recyclerView.addOnScrollListener(recyclerViewScroller);
        Intent intent = getIntent();
        if (intent.hasExtra(SYNCID)) {
            final MyTrack myTrack = (MyTrack) intent.getSerializableExtra(SYNCID);
            bindSyncData(myTrack);
            if (!TextUtils.isEmpty(myTrack.getVideoID())) {
                apiClient.getRelatedTrackID(Config.SNIPPET, myTrack.getVideoID(), MAX_NUMBER, Config.SEARCH_APIKEY);
            }
        }
        loadData();
        reloadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reloadLayout.setVisibility(GONE);
                loadData();
            }
        });
    }

    public void loadData() {
        youtubeView.initialize(Config.SEARCH_APIKEY, this);
        progressLayout.setVisibility(View.VISIBLE);
        syncContainer.setVisibility(View.INVISIBLE);
        webView.setVisibility(View.INVISIBLE);
        webView.loadUrl("https://www.youtube2mp3.cc/button-api/#" + videoId + "|mp3");
        webView.setWebViewClient(new RequestWebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        trackDownload();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            isGranted = true;
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
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            if (NetworkUtil.isOnline(getApplicationContext())) {
                new ConnectToUrl().execute();
            } else {
                isError = true;
            }
            String mainUrl = "https://www.youtube2mp3.cc/";
            if (mainUrl.compareTo(url) == 0) {
                reloadData.setVisibility(GONE);
                errorMessage.setText(getString(R.string.fetch_error_message));
                progressLayout.setVisibility(View.VISIBLE);
                webView.setVisibility(GONE);
                syncContainer.setVisibility(GONE);
                isError = true;
                isFetchError = true;
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            if (isError) {
                webView.setVisibility(GONE);
                progressLayout.setVisibility(GONE);
                reloadLayout.setVisibility(View.VISIBLE);
                syncContainer.setVisibility(GONE);
            } else {
                progressLayout.setVisibility(View.INVISIBLE);
                webView.setVisibility(View.VISIBLE);
                syncContainer.setVisibility(View.VISIBLE);
                isStoragePermissionGranted();
            }
//            htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"button_api.css\" />";
//            webView.loadUrl("file:///android_asset/api.html");
//            webView.loadDataWithBaseURL("file:///android_asset/.",htmlData,"text/html","UTF-8",null);
            super.onPageFinished(view, url);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView webview, int errorCode, String description, String failUrl) {
            super.onReceivedError(webview, errorCode, description, failUrl);
            isError = true;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            isError = true;
            super.onReceivedError(view, request, error);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        AnalyticsManager.sendScreenView(getString(R.string.download_music_screen));
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
        finish();
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
    public void eventSync(TrackEvent.OnRelatedSnippetEvent event) {

        if (event.isSuccess()) {
            snippetItems = event.getItem();
            token = event.getToken();
            relatedTrackId(snippetItems);

        }
    }

    @Subscribe
    public void EventTrackID(TrackEvent.OnTrackIDEvent event) {

        if (event.isSuccess()) {
            trackItems = event.getItem();
            if (snippetItems.size() > 0 && trackItems.size() > 0) {
                TrackManageUtil trackManageUtil = new TrackManageUtil();
                myRelatedTrackList = trackManageUtil.getTrackList(snippetItems, trackItems, token);
                if (myRelatedTrackList.size() > 0) {
                    adapter = new RelatedTrackAdapter(getApplicationContext(), myRelatedTrackList);
                    recyclerView.setAdapter(adapter);
                }
            }
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
                    .into(trackImage);
            duration.setText(track.getDuration());
            viewCount.setText(track.getViewCount());
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                isGranted = true;
                Log.v(MuzieApp.TAG, "Permission is granted");
                return true;
            } else {

                Log.v(MuzieApp.TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(SyncsTrackActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            Log.v(MuzieApp.TAG, "Permission is granted");
            return true;
        }
    }

    public void trackDownload() {

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

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        YouTubePlayer.PlayerStyle style = YouTubePlayer.PlayerStyle.MINIMAL;
        if (!b) {
            youTubePlayer.cueVideo(videoId);
            youTubePlayer.setPlayerStyle(style);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
        if (error.isUserRecoverableError()) {
            error.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
//            String errorReason = String.format(getString(R.string.player_error), error.toString());
//            Toast.makeText(this, errorReason, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            getYouTubePlayerProvider().initialize(Config.SEARCH_APIKEY, this);
        }
    }

    protected Provider getYouTubePlayerProvider() {
        return youtubeView;
    }

    private class ConnectToUrl extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            HttpDataHandler httpDataHandler = new HttpDataHandler();
            boolean response = httpDataHandler.GetHTTPData(Config.URL);
            return response;
        }

        @Override
        protected void onPostExecute(Boolean res) {
            if (!res) {
                isError = true;

            } else {
                if (isFetchError) {
                    isError = true;
                } else {
                    isError = false;
                }
            }
        }

    }

    @Subscribe
    public void eventSnippet(TrackEvent.OnNextRelatedSnippetEvent event) {
        if (event.isSuccess()) {
            snippetItems = event.getItem();
            token = event.getToken();
            relatedTrackId(snippetItems);
        }
    }

    @Subscribe
    public void eventTrack(TrackEvent.OnNextRelatedTrackIDEvent event) {

        if (event.isSuccess()) {
            trackItems = event.getItem();
            if (snippetItems.size() > 0 && trackItems.size() > 0) {
                TrackManageUtil trackManageUtil = new TrackManageUtil();
                myTrackList = trackManageUtil.getTrackList(snippetItems, trackItems, token);
                if (myTrackList.size() > 0) {
                    for (int i = 0; i < myTrackList.size(); i++) {
                        myRelatedTrackList.add(myTrackList.get(i));
                    }
                    adapter.addRelatedTrackItem(getApplicationContext(), myRelatedTrackList);
                    recyclerView.getRecycledViewPool().clear();
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    public void relatedTrackId(List<Item> items) {
        StringBuilder sb = new StringBuilder();
        if (items.size() > 0) {

            for (int i = 0; i < items.size(); i++) {
                if (!TextUtils.isEmpty(items.get(i).getId().getVideoId())) {
                    sb.append(items.get(i).getId().getVideoId() + ",");
                }
            }
            if (!TextUtils.isEmpty(sb)) {
                apiClient.getTrackDuration(Config.CONTENTDETAIL + "," + Config.STATISTICS, sb.toString(), Config.SEARCH_APIKEY);
            }
        }
    }

    private RecyclerView.OnScrollListener recyclerViewScroller = new RecyclerView.OnScrollListener() {
        private int totalItem, visibleItemCount, firstVisibleItem;
        private boolean loading = true;
        private int visibleThreshold = 1;
        private int previousTotal = 0;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            visibleItemCount = recyclerView.getChildCount();
            totalItem = layoutManager.getItemCount();
            firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

            if (loading) {
                if (totalItem > previousTotal + 1) {
                    loading = false;
                    previousTotal = totalItem;
                }
            }

            if (myRelatedTrackList.size() == TOTAL_ITEM) {
                loading = false;
                adapter.enableFooter(false);
            }

            if (!loading && (totalItem - visibleItemCount) <= (firstVisibleItem + visibleThreshold) && myRelatedTrackList.size() != TOTAL_ITEM) {
                apiClient.getNextRelatedTrackID(myRelatedTrackList.get(adapter.getItemCount() - 3).getNextToken(),Config.SNIPPET, videoId, Config.MAX_NUMBER, Config.SEARCH_APIKEY);
                loading = true;
            }
        }
    };


}
