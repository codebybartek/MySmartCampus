package com.example.mysmartcampus.Views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mysmartcampus.Adapters.StudentsListAdapter;

import com.example.mysmartcampus.R;


public class GroupContent extends Fragment {

    View myView;

    StudentsListAdapter adapterListStudents;
    ListView mListViewStudents;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.group_content, container, false);

        mListViewStudents = (ListView) myView.findViewById(R.id.studentsList);


        if(Groups.group != null) {
            ShowGroup();
        }

        Button backToGroups = (Button) myView.findViewById(R.id.backBtn);
        backToGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.api_view, new Groups()).commit();
            }
        });

        return myView;
    }

    public void ShowGroup(){

        TextView myAwesomeTextView = (TextView)  myView.findViewById(R.id.groupName);
        myAwesomeTextView.setText("Group: " + Groups.group.getName());

        final FragmentManager manager = getActivity().getSupportFragmentManager();
        adapterListStudents = new StudentsListAdapter(myView.getContext(), R.layout.adapter_view_students, Groups.group.getStudents());
        mListViewStudents.setAdapter(adapterListStudents);
    }
}
