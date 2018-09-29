package com.zen.mycataloguemovie;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

/**
 * Created by zhee-zhee on 27-Aug-18.
 */

public class MovieAdapter extends BaseAdapter {
    private ArrayList<MovieItems> mData = new ArrayList<>();
    private final LayoutInflater mInflater;
    private final Context context;

    public MovieAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<MovieItems> items) {
        mData = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        if (mData == null) return 0;
        return mData.size();
    }

    @Override
    public MovieItems getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.movie_items, null);

            holder.imgPosterPath = convertView.findViewById(R.id.imgPoster);
            holder.tvTitle = convertView.findViewById(R.id.textJudul);
            holder.tvReleaseDate = convertView.findViewById(R.id.textTahun);
            holder.tvOverview = convertView.findViewById(R.id.textDeskripsi);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final String Poster = mData.get(position).getPoster_path();
        holder.tvTitle.setText(mData.get(position).getTitle());
        holder.tvReleaseDate.setText(mData.get(position).getRelease_date());
        holder.tvOverview.setText(mData.get(position).getOverview());

        Glide.with(this.context)
                .load("http://image.tmdb.org/t/p/w185" + Poster)
                .apply(RequestOptions.centerCropTransform())
                .into(holder.imgPosterPath);

        return MyViewHolder(position, convertView);
    }

    private static class ViewHolder {
        ImageView imgPosterPath;
        TextView tvTitle, tvReleaseDate, tvOverview;
    }

    private View MyViewHolder(final int position, View v) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetail.class);
                intent.putExtra("judul", mData.get(position).getTitle());
                intent.putExtra("deskripsi", mData.get(position).getOverview());
                intent.putExtra("poster", mData.get(position).getPoster_path());
                intent.putExtra("tahun", mData.get(position).getRelease_date());
                intent.putExtra("backdrop", mData.get(position).getBackdrop_path());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return v;
    }
}