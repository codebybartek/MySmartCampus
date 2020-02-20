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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.mysmartcampus.Adapters.CoursesListAdapter;
import com.example.mysmartcampus.Customization;
import com.example.mysmartcampus.Models.Course;
import com.example.mysmartcampus.Models.Subject;
import com.example.mysmartcampus.R;

import java.util.ArrayList;

public class Courses extends Fragment {

    View myView;

    CoursesListAdapter adapterList;
    ListView mListView;

    ArrayList<Subject> subjects = Customization.user.getSubjects();
    ArrayList<Course> courses;
    static Course course = null;
    private Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.courses, container, false);


        mListView = (ListView) myView.findViewById(R.id.listView);
        spinner = (Spinner) myView.findViewById(R.id.spinnerCourse);

        Button backToActivities = (Button) myView.findViewById(R.id.backBtn);
        if(Subjects.subject != null) {

            backToActivities.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.api_view, new Subjects()).commit();
                }
            });
        }else{
            backToActivities.setVisibility(View.GONE);
        }

        if(Subjects.subject == null) {

            int countSubjects = subjects.size();
            String[] subjectsName = new String[countSubjects + 1];
            subjectsName[0] = "Chose course";
            for (int i = 0; i < countSubjects; i++) {
                subjectsName[i + 1] = subjects.get(i).getTitle();
            }

            final String[] paths = subjectsName;
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, paths);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0) {
                        //courses = Customization.user.getSubjects().get(position - 1).getCourses();
                        courses = Main.courses;
                        ShowList(courses);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            courses = Customization.user.getSubjects().get(0).getCourses();
        }
        else{
            courses = Subjects.subject.getCourses();
            spinner.setVisibility(View.GONE);
            Subjects.subject = null;
        }
        ShowList(courses);

        return myView;
    }

    public void ShowList(ArrayList<Course> courses){
        final FragmentManager manager = getActivity().getSupportFragmentManager();
        adapterList = new CoursesListAdapter(myView.getContext(), R.layout.adapter_view_courses, courses);
        mListView.setAdapter(adapterList);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                course =  Customization.user.getSubjects().get(0).getCourses().get(position);
                manager.beginTransaction().replace(R.id.api_view, new CourseContent()).commit();
            }
        });
    }


}
