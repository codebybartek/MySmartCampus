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
import com.example.mysmartcampus.Adapters.StudentsListAdapter;
import com.example.mysmartcampus.Customization;
import com.example.mysmartcampus.MainActivity;
import com.example.mysmartcampus.Models.Course;
import com.example.mysmartcampus.Models.Exam;
import com.example.mysmartcampus.Models.Student;
import com.example.mysmartcampus.Models.Subject;
import com.example.mysmartcampus.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.view.View.VISIBLE;

public class CheckAttendanceList extends Fragment {

    static View myView;
    static StudentsListAdapter adapterList;
    static ListView mListView;
    static ArrayList<Student> studentsAll;
    static ArrayList<Student> studentsPresent = new ArrayList<>();
    int activityPosition;
    private Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.check_attendance_list, container, false);

        mListView = (ListView) myView.findViewById(R.id.listView);

        if(Activities.course != null){
            if(Activities.activity.getChecked() ==1){
                ShowList(Activities.activity.getStudentsPresence());
            }
            studentsAll = Activities.course.getGroup().getStudents();
        }

        Button saveList= (Button) myView.findViewById(R.id.saveList);
        saveList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                for(int i=0; i<Customization.user.getSubjects().size(); i++){
                    for(int j=0; j<Customization.user.getSubjects().get(i).getCourses().size(); j++){
                        for(int w=0; w<Customization.user.getSubjects().get(i).getCourses().get(j).getActivities().size(); w++){
                            if(Activities.activity.getActivityDate() == Customization.user.getSubjects().get(i).getCourses().get(j).getActivities().get(w).getActivityDate() && Activities.activity.getHour() == Customization.user.getSubjects().get(i).getCourses().get(j).getActivities().get(w).getHour() && Activities.activity.getTitle() == Customization.user.getSubjects().get(i).getCourses().get(j).getActivities().get(w).getTitle()){
                                Customization.user.getSubjects().get(i).getCourses().get(j).getActivities().get(w).setStudentsPresence(studentsPresent);
                                Customization.user.getSubjects().get(i).getCourses().get(j).getActivities().get(w).setChecked();
                                Toast.makeText(myView.getContext(), "List was successfully added!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });

        Button backToActivities= (Button) myView.findViewById(R.id.backBtn);
        backToActivities.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.api_view, new Activities()).commit();
            }
        });

        return myView;
    }

    //FUNCTIONS//

    public static void ShowList(ArrayList<Student> students){
        if(students.size() == 0){
            Toast.makeText(myView.getContext(), "There are not any students on list!", Toast.LENGTH_SHORT).show();
        }
        adapterList = new StudentsListAdapter(myView.getContext(), R.layout.adapter_view_students, students);
        mListView.setAdapter(adapterList);
    }

    public static void Istagged(String tag){
        if(Activities.activity.getChecked() != 1){
            Toast.makeText(myView.getContext(), tag, Toast.LENGTH_SHORT).show();
            int searchListLength = studentsAll.size();
            for (int i = 0; i < searchListLength; i++) {
                if (studentsAll.get(i).getTagId().contains(tag)) {
                    Date date = Calendar.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String strDate = dateFormat.format(date);
                    studentsAll.get(i).setTagDate(strDate);
                    studentsPresent.add(studentsAll.get(i));
                    ShowList(studentsPresent);
                }
            }
        }
    }

}
