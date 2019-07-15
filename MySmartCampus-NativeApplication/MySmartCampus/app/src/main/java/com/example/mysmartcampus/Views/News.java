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
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysmartcampus.Adapters.NewsListAdapter;
import com.example.mysmartcampus.Customization;
import com.example.mysmartcampus.Models.Course;
import com.example.mysmartcampus.Models.Subject;
import com.example.mysmartcampus.R;
import java.util.ArrayList;

public class News extends Fragment {

    View myView;
    NewsListAdapter adapterList;
    ListView mListView;
    ArrayList<com.example.mysmartcampus.Models.News> newsSelected = new ArrayList<>();
    ArrayList<Course> courses = new ArrayList<>();
    int courseSelected;

    private Spinner spinner;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.news, container, false);
        mListView = (ListView) myView.findViewById(R.id.listView);

        spinner = (Spinner) myView.findViewById(R.id.spinnerNews);

        Button backToActivities = (Button) myView.findViewById(R.id.backBtn);
        if(CourseContent.course != null) {

            backToActivities.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.api_view, new Courses()).commit();
                }
            });
        }else{
            backToActivities.setVisibility(View.GONE);
        }

        if(CourseContent.course == null) {
            loadCourses();

            int countCourses = courses.size();
            String[] coursesName = new String[countCourses + 1];
            coursesName[0] = "Chose course";
            for (int i = 0; i < countCourses; i++) {
                coursesName[i + 1] = courses.get(i).getTitle();
            }

            final String[] paths = coursesName;

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, paths);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    courseSelected = position;
                    if (position != 0) {
                        newsSelected = courses.get(position - 1).getNews();
                        ShowList(newsSelected);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }else{
            TextView choseCourse = (TextView) myView.findViewById(R.id.choseCourse);
            spinner.setVisibility(View.GONE);
            choseCourse.setVisibility(View.GONE);
            newsSelected = CourseContent.course.getNews();
            CourseContent.course = null;
        }

        ShowList(newsSelected);

        return myView;
    }

    //FUNCTIONS//

    public void ShowList(ArrayList<com.example.mysmartcampus.Models.News> news){
        if(news.size() == 0){
            Toast.makeText(myView.getContext(), "There are not any news!", Toast.LENGTH_SHORT).show();
        }
        adapterList = new NewsListAdapter(myView.getContext(), R.layout.adapter_view_news, news);
        mListView.setAdapter(adapterList);
    }

    public void loadCourses(){
        ArrayList<Subject> subjects = Customization.user.getSubjects();
        for (int i=0; i<subjects.size(); i++){
            courses.addAll(subjects.get(i).getCourses());
        }
    }
}
