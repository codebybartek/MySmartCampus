package com.example.mysmartcampus.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mysmartcampus.Models.Group;
import com.example.mysmartcampus.Models.Subject;
import com.example.mysmartcampus.R;

import java.util.ArrayList;

public class GroupsListAdapter extends ArrayAdapter<Group> {

    private static final String TAG = "GroupsListAdapter";

    private Context mContext;
    private int mResource;

    public GroupsListAdapter(Context context, int resource, ArrayList<Group> groups) {
        super(context, resource, groups);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String groupTitle = getItem(position).getName();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView title = (TextView) convertView.findViewById(R.id.title);

        title.setText(groupTitle);



        return convertView;
    }
}

























