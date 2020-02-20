package com.example.mysmartcampus.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mysmartcampus.Models.News;
import com.example.mysmartcampus.Models.Subject;
import com.example.mysmartcampus.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsListAdapter extends ArrayAdapter<com.example.mysmartcampus.Models.News> {

    private static final String TAG = "NewsListAdapter";

    private Context mContext;
    private int mResource;

    public NewsListAdapter(Context context, int resource, ArrayList<com.example.mysmartcampus.Models.News> news) {
        super(context, resource, news);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String newsTitle = getItem(position).getNewsTitle();
        String newsDate = getItem(position).getNewsDate();
        String newsInfo = getItem(position).getNewsContent();
        String newsPicture = getItem(position).getNewsPicture();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView title = (TextView) convertView.findViewById(R.id.title);
        ImageView newsImage = (ImageView) convertView.findViewById(R.id.imageNews);
        TextView info = (TextView) convertView.findViewById(R.id.info);
        TextView date = (TextView) convertView.findViewById(R.id.date);

        title.setText(newsTitle);
        Picasso.get().load(newsPicture).into(newsImage);
        info.setText(newsInfo);
        date.setText(newsDate);




        return convertView;
    }
}

























