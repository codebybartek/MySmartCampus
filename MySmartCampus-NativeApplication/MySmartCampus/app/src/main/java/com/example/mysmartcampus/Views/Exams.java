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
import android.widget.Toast;
import com.example.mysmartcampus.Adapters.ExamsListAdapter;
import com.example.mysmartcampus.Models.Exam;
import com.example.mysmartcampus.R;

import java.util.ArrayList;


public class Exams extends Fragment {

    View myView;

    ExamsListAdapter adapterListExams;;
    ListView mListViewExams;

    public static ArrayList<Exam> exams = new ArrayList<>();
    public static Exam exam;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.exams, container, false);

        mListViewExams = (ListView) myView.findViewById(R.id.listViewExams);

        if(exams != null) {
            ShowListExams();
        }


        return myView;
    }

    public void ShowListExams(){

        final FragmentManager manager = getActivity().getSupportFragmentManager();
        adapterListExams = new ExamsListAdapter(myView.getContext(), R.layout.adapter_view_exams, exams);
        mListViewExams.setAdapter(adapterListExams);
        mListViewExams.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                exam =  exams.get(position);
                manager.beginTransaction().replace(R.id.api_view, new ExamContent()).commit();
            }
        });
    }

}
