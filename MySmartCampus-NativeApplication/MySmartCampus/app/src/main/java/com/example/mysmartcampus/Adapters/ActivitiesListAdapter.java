package com.example.mysmartcampus.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.mysmartcampus.Models.Activity;
import com.example.mysmartcampus.Models.Student;
import com.example.mysmartcampus.R;
import com.example.mysmartcampus.Views.Activities;

import java.util.ArrayList;

public class ActivitiesListAdapter extends ArrayAdapter<Activity> {

    private static final String TAG = "ActivitiesListAdapter";

    private Context mContext;
    private int mResource;

    public ActivitiesListAdapter(Context context, int resource, ArrayList<Activity> activities) {
        super(context, resource, activities);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String activityTitle = getItem(position).getTitle();
        String activityDuration = String.valueOf(getItem(position).getDuration());
        String activityDate = getItem(position).getActivityDate();
        String activityHour = getItem(position).getHour();


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView duration = (TextView) convertView.findViewById(R.id.duration);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        TextView hour = (TextView) convertView.findViewById(R.id.hour);

        title.setText(activityTitle);
        duration.setText(activityDuration);
        date.setText(activityDate);
        hour.setText(activityHour);


        return convertView;
    }
}

























