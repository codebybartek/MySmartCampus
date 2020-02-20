package com.example.mysmartcampus.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mysmartcampus.Models.Grade;
import com.example.mysmartcampus.Models.Student;
import com.example.mysmartcampus.R;

import java.util.ArrayList;

public class GradesListAdapter extends ArrayAdapter<Grade> {

    private static final String TAG = "GradesListAdapter";

    private Context mContext;
    private int mResource;

    public GradesListAdapter(Context context, int resource, ArrayList<Grade> grades) {
        super(context, resource, grades);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String studentaName = getItem(position).getGradeUserName();
        String studentIndex = String.valueOf(getItem(position).getGradeUserId());
        Integer Grade = getItem(position).getGrade();
        String gradeDate = getItem(position).getGradeDate();
        gradeDate = gradeDate.substring(0, 16);


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView index = (TextView) convertView.findViewById(R.id.index);
        TextView grade = (TextView) convertView.findViewById(R.id.grade);
        TextView date = (TextView) convertView.findViewById(R.id.date);

        name.setText(studentaName);
        index.setText(String.valueOf(studentIndex));
        grade.setText(String.valueOf(Grade));
        date.setText(gradeDate);


        return convertView;
    }
}

























