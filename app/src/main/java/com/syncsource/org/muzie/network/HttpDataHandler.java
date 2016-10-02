package com.syncsource.org.muzie.network;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by SyncSource on 10/1/2016.
 */
public class HttpDataHandler {

    static boolean success;

    public HttpDataHandler() {
    }

    public boolean GetHTTPData(String urlString) {

        try {
            success = false;
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            if (urlConnection.getResponseCode() == 200) {
                success = true;
                urlConnection.disconnect();
            } else {
                success = false;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return success;
    }
}