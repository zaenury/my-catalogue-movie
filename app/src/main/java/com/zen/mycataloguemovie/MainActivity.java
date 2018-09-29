package com.zen.mycataloguemovie;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {

    private EditText editMovie;
    private Button buttonCari;
    private ListView listView;
    private MovieAdapter adapter;
    static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editMovie = findViewById(R.id.edt_pencarian_film);
        buttonCari = findViewById(R.id.btn_cari);
        listView = findViewById(R.id.list_view);

        adapter = new MovieAdapter(this);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        buttonCari.setOnClickListener(myListener);

        String movie = editMovie.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_MOVIE, movie);

        getLoaderManager().initLoader(0, bundle, this);
    }

    private final View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String movie = editMovie.getText().toString();

            if (TextUtils.isEmpty(movie)) return;
            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_MOVIE, movie);
            getLoaderManager().restartLoader(0, bundle, MainActivity.this);

            hideKeyboard(MainActivity.this);
        }
    };

    private static void hideKeyboard(Activity a) {
        InputMethodManager input = (InputMethodManager) a.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View v = a.getCurrentFocus();

        if (v == null) {
            v = new View(a);
        }
        if (input != null) {
            input.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, Bundle args) {
        String kumpulanMovie = "";
        if (args != null) {
            kumpulanMovie = args.getString(EXTRAS_MOVIE);
        }
        return new MyAsyncTaskLoader(this, kumpulanMovie);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItems>> loader) {
        adapter.setData(null);
    }
}