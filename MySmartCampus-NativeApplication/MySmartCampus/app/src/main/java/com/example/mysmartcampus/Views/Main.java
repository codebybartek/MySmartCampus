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

import com.example.mysmartcampus.Adapters.CoursesListAdapter;
import com.example.mysmartcampus.Customization;
import com.example.mysmartcampus.Models.Activity;
import com.example.mysmartcampus.Models.Course;
import com.example.mysmartcampus.Models.Group;
import com.example.mysmartcampus.R;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Main extends Fragment {

    View myView;

    CoursesListAdapter adapterListCourses;
    ListView mListViewCourses;
    ListView mListViewActivities;

    public static Course course = null;
    public static ArrayList<Course> courses = new ArrayList<>();
    public static ArrayList<Activity> activities = new ArrayList<>();
    public static ArrayList<Group> groups = new ArrayList<>();
    public static Activity activity = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.content_start, container, false);

        mListViewCourses = (ListView) myView.findViewById(R.id.listViewCourses);
        mListViewActivities = (ListView) myView.findViewById(R.id.listViewActivities);

        ShowListCourses();
        if(!courses.isEmpty()) {
           ShowListCourses();
        }else{
            try {
                sleep(50);
                ShowListCourses();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return myView;
    }

    public void ShowListCourses(){
        final FragmentManager manager = getActivity().getSupportFragmentManager();
        adapterListCourses = new CoursesListAdapter(myView.getContext(), R.layout.adapter_view_courses, courses);
        mListViewCourses.setAdapter(adapterListCourses);
        mListViewCourses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                course =  Customization.user.getSubjects().get(0).getCourses().get(position);
                manager.beginTransaction().replace(R.id.api_view, new CourseContent()).commit();
            }
        });
    }
}
