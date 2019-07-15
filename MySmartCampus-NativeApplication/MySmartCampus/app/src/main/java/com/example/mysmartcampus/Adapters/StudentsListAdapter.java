package com.example.mysmartcampus.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mysmartcampus.Models.Student;
import com.example.mysmartcampus.R;

import java.util.ArrayList;

public class StudentsListAdapter extends ArrayAdapter<Student> {

    private static final String TAG = "StudentsListAdapter";

    private Context mContext;
    private int mResource;

    public StudentsListAdapter(Context context, int resource, ArrayList<Student> students) {
        super(context, resource, students);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String studentaName = getItem(position).getName();
        String studentIndex = String.valueOf(getItem(position).getIndex());
        String studentTag = getItem(position).getTagId();
        String tagDate = getItem(position).getTagDate();


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView index = (TextView) convertView.findViewById(R.id.index);
        TextView tagId = (TextView) convertView.findViewById(R.id.tagId);
        TextView date = (TextView) convertView.findViewById(R.id.date);

        name.setText(studentaName);
        index.setText(studentIndex);
        tagId.setText(studentTag);
        date.setText(tagDate);


        return convertView;
    }
}

























