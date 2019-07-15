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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysmartcampus.Adapters.ExamsListAdapter;
import com.example.mysmartcampus.Adapters.NewsListAdapter;
import com.example.mysmartcampus.Customization;
import com.example.mysmartcampus.Models.Course;
import com.example.mysmartcampus.Models.Exam;
import com.example.mysmartcampus.Models.Subject;
import com.example.mysmartcampus.R;

import java.util.ArrayList;

import static android.view.View.VISIBLE;

public class Exams extends Fragment {

    View myView;
    ExamsListAdapter adapterList;
    ListView mListView;
    ArrayList<Exam> examsSelected = new ArrayList<>();
    ArrayList<Course> courses = new ArrayList<>();
    int courseSelected;

    LinearLayout exam_header_list;
    LinearLayout examsList;

    private Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.exams, container, false);

        mListView = (ListView) myView.findViewById(R.id.listView);

        exam_header_list = (LinearLayout) myView.findViewById(R.id.header_list);
        examsList = (LinearLayout) myView.findViewById(R.id.examsList);

        spinner = (Spinner) myView.findViewById(R.id.spinnerExams);

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

        if(CourseContent.course == null){
            loadCourses();
            exam_header_list.setVisibility(View.GONE);
            examsList.setVisibility(View.GONE);

            int countCourses = courses.size();
            String[] coursesName = new String[countCourses+1];
            coursesName[0] = "Chose course";
            for (int i = 0; i < countCourses; i++)    {
                coursesName[i+1] = courses.get(i).getTitle();
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
                        examsSelected = courses.get(position - 1).getExams();
                        ShowList(examsSelected);
                        if (exam_header_list.getVisibility() != VISIBLE) {
                            exam_header_list.setVisibility(View.VISIBLE);
                            examsList.setVisibility(View.VISIBLE);
                        }
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
            examsSelected = CourseContent.course.getExams();
            CourseContent.course = null;
        }


        ShowList(examsSelected);

        return myView;
    }

    //FUNCTIONS//

    public void ShowList(ArrayList<Exam> exams){
        if(exams.size() == 0){
            Toast.makeText(myView.getContext(), "There are not any exams!", Toast.LENGTH_SHORT).show();
        }
        adapterList = new ExamsListAdapter(myView.getContext(), R.layout.adapter_view_exams, exams);
        mListView.setAdapter(adapterList);
    }

    public void loadCourses(){
        ArrayList<Subject> subjects = Customization.user.getSubjects();
        for (int i=0; i<subjects.size(); i++){
            courses.addAll(subjects.get(i).getCourses());
        }
    }
}
