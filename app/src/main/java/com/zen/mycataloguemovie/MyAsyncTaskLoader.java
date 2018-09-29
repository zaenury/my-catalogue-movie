package com.zen.mycataloguemovie;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by zhee-zhee on 27-Aug-18.
 */

public class MyAsyncTaskLoader extends AsyncTaskLoader<ArrayList<MovieItems>> {
    private ArrayList<MovieItems> mData;
    private boolean mHasResult;
    private URI uri;
    private final String mKumpulanMovie;

    public MyAsyncTaskLoader(final Context context, String kumpulanmovie) {
        super(context);
        onContentChanged();
        this.mKumpulanMovie = kumpulanmovie;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()) {
            forceLoad();
        } else if (mHasResult) {
            deliverResult(mData);
        }
    }

    private static final String API_KEY = "79b655425e529cc9120d0a475874f77a";

    @Override
    public ArrayList<MovieItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<MovieItems> movieItemses = new ArrayList<>();
        try {
            uri = new URI(mKumpulanMovie.replace(" ", "%20"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=" + uri;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        MovieItems movieItems = new MovieItems(movie);
                        movieItemses.add(movieItems);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return movieItemses;
    }

    @Override
    public void deliverResult(ArrayList<MovieItems> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }
}
