package com.example.mysmartcampus.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mysmartcampus.Models.Subject;
import com.example.mysmartcampus.R;

import java.util.ArrayList;

public class SubjectsListAdapter extends ArrayAdapter<Subject> {

    private static final String TAG = "SubjectsListAdapter";

    private Context mContext;
    private int mResource;

    public SubjectsListAdapter(Context context, int resource, ArrayList<Subject> subjects) {
        super(context, resource, subjects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String activityTitle = getItem(position).getTitle();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView title = (TextView) convertView.findViewById(R.id.title);

        title.setText(activityTitle);



        return convertView;
    }
}

























