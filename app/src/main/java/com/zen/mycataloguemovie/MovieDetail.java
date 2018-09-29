package com.zen.mycataloguemovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by zhee-zhee on 28-Aug-18.
 */

public class MovieDetail extends AppCompatActivity {
    private TextView tvTitle, tvReleaseDate, tvOverview;
    private ImageView imgPosterPath, imgBackdropPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        tvTitle = findViewById(R.id.tv_title);
        tvOverview = findViewById(R.id.tv_overview);
        tvReleaseDate = findViewById(R.id.tv_release_date);
        imgPosterPath = findViewById(R.id.img_poster_path);
        imgBackdropPath = findViewById(R.id.img_backdrop_path);

        Intent intentMovie = getIntent();
        if (intentMovie.hasExtra("judul")) {

            String title = getIntent().getStringExtra("judul");
            String overview = getIntent().getStringExtra("deskripsi");
            String posterPath = getIntent().getStringExtra("poster");
            String backdropPath = getIntent().getStringExtra("backdrop");
            String releaseDate = getIntent().getStringExtra("tahun");

            tvTitle.setText(title);
            tvOverview.setText(overview);
            tvReleaseDate.setText(releaseDate);

            Glide.with(MovieDetail.this)
                    .load("http://image.tmdb.org/t/p/w780" + backdropPath)
                    .into(imgBackdropPath);

            Glide.with(MovieDetail.this)
                    .load("http://image.tmdb.org/t/p/w300" + posterPath)
                    .apply(RequestOptions.centerCropTransform())
                    .into(imgPosterPath);

        } else {

            Toast.makeText(MovieDetail.this, "", Toast.LENGTH_SHORT).show();

        }
    }
}
