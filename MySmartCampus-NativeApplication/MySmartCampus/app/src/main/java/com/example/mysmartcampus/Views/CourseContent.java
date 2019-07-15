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
import android.widget.Toast;

import com.example.mysmartcampus.Adapters.ActivitiesListAdapter;
import com.example.mysmartcampus.Adapters.ExamsListAdapter;
import com.example.mysmartcampus.Adapters.MaterialsListAdapter;
import com.example.mysmartcampus.Adapters.NewsListAdapter;
import com.example.mysmartcampus.Models.Activity;
import com.example.mysmartcampus.Models.Course;
import com.example.mysmartcampus.Models.Exam;
import com.example.mysmartcampus.Models.Material;
import com.example.mysmartcampus.Models.News;
import com.example.mysmartcampus.R;

import java.util.ArrayList;

import static android.view.View.VISIBLE;

public class CourseContent extends Fragment {

    View myView;
    ExamsListAdapter adapterListExams;
    ActivitiesListAdapter adapterListActivities;
    MaterialsListAdapter adapterListMaterials;
    NewsListAdapter adapterListNews;
    ListView mListViewActivities;
    ListView mListViewExams;
    ListView mListViewMaterials;
    ListView mListViewNews;
    static Course course = null;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.course_content, container, false);


        mListViewExams = (ListView) myView.findViewById(R.id.listViewExams);
        mListViewActivities = (ListView) myView.findViewById(R.id.listViewActivities);
        mListViewMaterials = (ListView) myView.findViewById(R.id.listViewMaterials);
        mListViewNews = (ListView) myView.findViewById(R.id.listViewNews);

        if(Courses.course != null){
            course = Courses.course;
        }

        ShowListExams(course.getExams());
        ShowListActivities(course.getActivities());
        ShowListMaterials(course.getMaterials());
        ShowListNews(course.getNews());

        final FragmentManager manager = getActivity().getSupportFragmentManager();

        Button seeMoreActivities= (Button) myView.findViewById(R.id.seeMoreActivities);
        seeMoreActivities.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                manager.beginTransaction().replace(R.id.api_view, new Activities()).commit();
            }
        });

        Button seeMoreExams= (Button) myView.findViewById(R.id.seeMoreExams);
        seeMoreExams.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                manager.beginTransaction().replace(R.id.api_view, new Exams()).commit();
            }
        });

        Button seeMoreMaterials= (Button) myView.findViewById(R.id.seeMoreMaterials);
        seeMoreMaterials.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                manager.beginTransaction().replace(R.id.api_view, new Materials()).commit();
            }
        });

        Button seeMoreNews= (Button) myView.findViewById(R.id.seeMoreNews);
        seeMoreNews.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                manager.beginTransaction().replace(R.id.api_view, new com.example.mysmartcampus.Views.News()).commit();
            }
        });

        return myView;
    }

    //FUNCTIONS//

    private void ShowListExams(ArrayList<Exam> exams){
        if(exams.size() == 0){
            Toast.makeText(myView.getContext(), "There are not any exams!", Toast.LENGTH_SHORT).show();
        }
        adapterListExams = new ExamsListAdapter(myView.getContext(), R.layout.adapter_view_exams, exams);
        mListViewExams.setAdapter(adapterListExams);
    }

    private void ShowListActivities(ArrayList<Activity> activities){
        if(activities.size() == 0){
            Toast.makeText(myView.getContext(), "There are not any activities!", Toast.LENGTH_SHORT).show();
        }
        adapterListActivities = new ActivitiesListAdapter(myView.getContext(), R.layout.adapter_view_activities, activities);
        mListViewActivities.setAdapter(adapterListActivities);
    }

    private void ShowListMaterials(ArrayList<Material> materials){
        if(materials.size() == 0){
            Toast.makeText(myView.getContext(), "There are not any materials!", Toast.LENGTH_SHORT).show();
        }
        adapterListMaterials = new MaterialsListAdapter(myView.getContext(), R.layout.adapter_view_materials, materials);
        mListViewMaterials.setAdapter(adapterListMaterials);
    }

    private void ShowListNews(ArrayList<News> news) {
        if(news.size() == 0){
            Toast.makeText(myView.getContext(), "There are not any materials!", Toast.LENGTH_SHORT).show();
        }
        adapterListNews= new NewsListAdapter(myView.getContext(), R.layout.adapter_view_news, news);
        mListViewNews.setAdapter(adapterListNews);
    }

}
