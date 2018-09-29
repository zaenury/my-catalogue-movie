package com.zen.mycataloguemovie;

import org.json.JSONObject;

/**
 * Created by zhee-zhee on 27-Aug-18.
 */

public class MovieItems {
    private String title, overview, release_date, poster_path, backdrop_path;

    public MovieItems(JSONObject object) {
        try {
            String judulMovie = object.getString("title");
            String deskripsi = object.getString("overview");
            String tahun = object.getString("release_date");
            String poster = object.getString("poster_path");
            String backdrop = object.getString("backdrop_path");
            this.title = judulMovie;
            this.overview = deskripsi;
            this.release_date = tahun;
            this.poster_path = poster;
            this.backdrop_path = backdrop;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }
}
