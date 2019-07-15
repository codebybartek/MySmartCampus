package com.example.mysmartcampus.Views;

import android.os.AsyncTask;
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
import com.example.mysmartcampus.Adapters.MaterialsListAdapter;
import com.example.mysmartcampus.Customization;
import com.example.mysmartcampus.Models.Course;
import com.example.mysmartcampus.Models.Exam;
import com.example.mysmartcampus.Models.Material;
import com.example.mysmartcampus.Models.Subject;
import com.example.mysmartcampus.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.util.ArrayList;

import static android.view.View.VISIBLE;


public class Materials extends Fragment {

    View myView;
    PDFView pdf;
    MaterialsListAdapter adapterList;
    ListView mListView;
    ArrayList<Material> materialsSelected = new ArrayList<>();
    ArrayList<Course> courses = new ArrayList<>();
    public static Material material;
    public static int courseSelected;

    LinearLayout material_header_list;
    LinearLayout materialsList;

    private Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.materials, container, false);

        mListView = (ListView) myView.findViewById(R.id.listView);

        material_header_list = (LinearLayout) myView.findViewById(R.id.header_list);
        materialsList = (LinearLayout) myView.findViewById(R.id.materialsList);
        spinner = (Spinner) myView.findViewById(R.id.spinnerMaterials);

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
            material_header_list.setVisibility(View.GONE);
            materialsList.setVisibility(View.GONE);

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
                        materialsSelected = courses.get(position - 1).getMaterials();
                        ShowList(materialsSelected);
                        if (material_header_list.getVisibility() != VISIBLE) {
                            material_header_list.setVisibility(View.VISIBLE);
                            materialsList.setVisibility(View.VISIBLE);
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
            materialsSelected = CourseContent.course.getMaterials();
            CourseContent.course = null;
        }


        ShowList(materialsSelected);

        return myView;
    }

    public void ShowList(ArrayList<Material> materials){
        if(materials.size() == 0){
            Toast.makeText(myView.getContext(), "There are not any materials!", Toast.LENGTH_SHORT).show();
        }
        adapterList = new MaterialsListAdapter(myView.getContext(), R.layout.adapter_view_materials, materials);
        mListView.setAdapter(adapterList);

        final FragmentManager manager = getActivity().getSupportFragmentManager();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                material =  materialsSelected.get(position);
                manager.beginTransaction().replace(R.id.api_view, new ReadPdf()).commit();
            }
        });
    }

    public void loadCourses(){
        ArrayList<Subject> subjects = Customization.user.getSubjects();
        for (int i=0; i<subjects.size(); i++){
            courses.addAll(subjects.get(i).getCourses());
        }
    }

}
