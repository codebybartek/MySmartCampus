package com.example.mysmartcampus.Views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.mysmartcampus.Adapters.SubjectsListAdapter;
import com.example.mysmartcampus.Customization;
import com.example.mysmartcampus.Models.Subject;
import com.example.mysmartcampus.R;

import java.util.ArrayList;

public class Subjects extends Fragment {

    View myView;

    SubjectsListAdapter adapterList;
    ListView mListView;

    private Spinner spinner;
    static Subject subject = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.subjects, container, false);
        mListView = (ListView) myView.findViewById(R.id.listView);

        ArrayList<Subject> subjects = Customization.user.getSubjects();

        ShowList(subjects);

        return myView;
    }

    public void ShowList(ArrayList<Subject> subjects){
        final FragmentManager manager = getActivity().getSupportFragmentManager();
        adapterList = new SubjectsListAdapter(myView.getContext(), R.layout.adapter_view_courses, subjects);
        mListView.setAdapter(adapterList);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subject =  Customization.user.getSubjects().get(position);
                manager.beginTransaction().replace(R.id.api_view, new Courses()).commit();
            }
        });
    }


}
