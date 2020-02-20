package com.example.mysmartcampus.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mysmartcampus.Models.Exam;
import com.example.mysmartcampus.Models.Student;
import com.example.mysmartcampus.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ExamsListAdapter extends ArrayAdapter<Exam> {

    private static final String TAG = "ExamsListAdapter";

    private Context mContext;
    private int mResource;

    public ExamsListAdapter(Context context, int resource, ArrayList<Exam> exams) {
        super(context, resource, exams);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String examTitle = getItem(position).getExamTitle();
        int examId = getItem(position).getExamId();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView title = (TextView) convertView.findViewById(R.id.examTitle);
        TextView index = (TextView) convertView.findViewById(R.id.examId);

        title.setText(examTitle);
        index.setText(String.valueOf(examId));



        return convertView;
    }
}

























