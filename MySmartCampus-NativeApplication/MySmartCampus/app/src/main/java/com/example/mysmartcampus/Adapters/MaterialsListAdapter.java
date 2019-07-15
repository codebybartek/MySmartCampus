package com.example.mysmartcampus.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mysmartcampus.Models.Course;
import com.example.mysmartcampus.Models.Material;
import com.example.mysmartcampus.R;

import java.util.ArrayList;

public class MaterialsListAdapter extends ArrayAdapter<Material> {

    private static final String TAG = "MaterialsListAdapter";

    private Context mContext;
    private int mResource;

    public MaterialsListAdapter(Context context, int resource, ArrayList<Material> materials) {
        super(context, resource, materials);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String materialTile = getItem(position).getTitle();
        String materialDate = getItem(position).getMaterialDate();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView title = (TextView) convertView.findViewById(R.id.materialTitle);
        TextView date = (TextView) convertView.findViewById(R.id.materialDate);

        title.setText(materialTile);
        date.setText(materialDate);

        return convertView;
    }
}

























