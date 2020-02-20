package com.example.mysmartcampus.Views;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mysmartcampus.Adapters.ActivitiesListAdapter;
import com.example.mysmartcampus.MainActivity;
import com.example.mysmartcampus.Models.Activity;
import com.example.mysmartcampus.R;

import java.util.ArrayList;


public class CurrentActivities extends Fragment {
    View myView;
    ActivitiesListAdapter adapterList;
    ListView mListView;
    ArrayList<Activity> activitiesSelected = MainActivity.attendanceList.activities;
    public static Activity activity = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.current_activities, container, false);

        mListView = (ListView) myView.findViewById(R.id.listView);

        ShowList();



        Button backToActivities = (Button) myView.findViewById(R.id.backBtn);
            backToActivities.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.api_view, new Courses()).commit();
                }
            });



        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);


        return myView;
    }

    //FUNCTIONS//

    public void ShowList(){
        if(activitiesSelected.size() == 0){
            Toast.makeText(myView.getContext(), "There are not any activities in this day!", Toast.LENGTH_SHORT).show();
        }
        adapterList = new ActivitiesListAdapter(myView.getContext(), R.layout.adapter_view_activities, activitiesSelected);
        mListView.setAdapter(adapterList);
        final FragmentManager manager = getActivity().getSupportFragmentManager();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                activity = activitiesSelected.get(position);
                manager.beginTransaction().replace(R.id.api_view, new CheckAttendanceList()).commit();
            }
        });
    }




}
