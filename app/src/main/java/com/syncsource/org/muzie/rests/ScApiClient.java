package com.syncsource.org.muzie.rests;

import com.syncsource.org.muzie.BuildConfig;
import com.syncsource.org.muzie.model.ScTrackContent;
import com.syncsource.org.muzie.utils.Config;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nalioes on 1/14/17.
 */

public class ScApiClient implements ScApiAccess {
    private static Retrofit retrofit = null;
    private static ScApiClient apiClient;
    private ApiInterface apiInterface;

    public ScApiClient() {
        this.apiInterface = getClient().create(ApiInterface.class);
    }

    public static ScApiClient getApiClientInstance() {
        if (apiClient == null) {
            apiClient = new ScApiClient();
        }
        return apiClient;
    }

    public static Retrofit getClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BuildConfig.SC_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    @Override
    public void getSearchScTrack(String query, String limit, String client_id) {
        Call<List<ScTrackContent>> call = apiInterface.getSearchScTrack(query, limit, client_id);
        call.enqueue(new Callback<List<ScTrackContent>>() {
            @Override
            public void onResponse(Call<List<ScTrackContent>> call, Response<List<ScTrackContent>> response) {
                if (response.isSuccessful() && response.body() != null) {

                }
            }

            @Override
            public void onFailure(Call<List<ScTrackContent>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getMostPopularTrack(String to, String from) {
        Call<List<ScTrackContent>> call = apiInterface.getLatestScTrack(to, from, Config.MAX_NUMBER, Config.CLIENT_ID, Config.SC_ORDER);
        call.enqueue(new Callback<List<ScTrackContent>>() {
            @Override
            public void onResponse(Call<List<ScTrackContent>> call, Response<List<ScTrackContent>> response) {
                if (response.isSuccessful() && response.body() != null) {

                }
            }

            @Override
            public void onFailure(Call<List<ScTrackContent>> call, Throwable t) {

            }
        });
    }
}
