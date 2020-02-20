package com.example.mysmartcampus.Views;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysmartcampus.Adapters.ActivitiesListAdapter;
import com.example.mysmartcampus.Customization;
import com.example.mysmartcampus.Models.Activity;
import com.example.mysmartcampus.Models.Course;
import com.example.mysmartcampus.Models.Subject;
import com.example.mysmartcampus.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.view.View.VISIBLE;

public class Activities extends Fragment {

    View myView;
    ActivitiesListAdapter adapterList;
    ListView mListView;
    ArrayList<Activity> activitiesSelected = new ArrayList<>();
    ArrayList<Course> courses = new ArrayList<>();
    int courseSelected = -1;
    static Course course = null;
    public static Activity activity = null;

    private Spinner spinner;

    CompactCalendarView calendarView;
    LinearLayout activity_header_list;
    LinearLayout activitiesList;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activities, container, false);

        mListView = (ListView) myView.findViewById(R.id.listView);


        activity_header_list = (LinearLayout) myView.findViewById(R.id.activity_header_list);
        activitiesList = (LinearLayout) myView.findViewById(R.id.activitiesList);

        if(CourseContent.course == null){
            loadCourses();
            activity_header_list.setVisibility(View.GONE);
            activitiesList.setVisibility(View.GONE);
        }

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


        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);

        calendarView = (CompactCalendarView) myView.findViewById(R.id.calendarActivities);
        calendarView.setUseThreeLetterAbbreviation(true);

        setMarkers(activitiesSelected);

        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                SimpleDateFormat  spf= new SimpleDateFormat("yyyy-MM-dd");
                String date = spf.format(dateClicked);
                ArrayList<Activity> activitiesHelper = new ArrayList<>();
                if(courseSelected != 0) {
                    for (int i = 0; i < activitiesSelected.size(); i++) {
                        if (activitiesSelected.get(i).getActivityDate().equals(date)) {
                            activitiesHelper.add(activitiesSelected.get(i));
                        }
                    }
                    ShowList(activitiesHelper);
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });

        spinner = (Spinner) myView.findViewById(R.id.spinnerActivities);

        if(CourseContent.course == null) {
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
                        activitiesSelected = courses.get(position - 1).getActivities();
                        setMarkers(activitiesSelected);
                        ShowList(activitiesSelected);
                        if (activity_header_list.getVisibility() != VISIBLE) {
                            activity_header_list.setVisibility(View.VISIBLE);
                            activitiesList.setVisibility(View.VISIBLE);
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
            activitiesSelected = CourseContent.course.getActivities();
            ShowList(activitiesSelected);
            CourseContent.course = null;
        }

        manageViews();

        return myView;
    }

                                                    //FUNCTIONS//

    public void ShowList(ArrayList<Activity> activities){
        if(activities.size() == 0){
            Toast.makeText(myView.getContext(), "There are not any activities in this day!", Toast.LENGTH_SHORT).show();
        }
        adapterList = new ActivitiesListAdapter(myView.getContext(), R.layout.adapter_view_activities, activities);
        mListView.setAdapter(adapterList);
        if(courseSelected != -1){
            course = courses.get(courseSelected);
        }else{
            course = Courses.course;
        }
        final FragmentManager manager = getActivity().getSupportFragmentManager();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                activity = activitiesSelected.get(position);
                manager.beginTransaction().replace(R.id.api_view, new CheckAttendanceList()).commit();
            }
        });
    }

    public void setMarkers(ArrayList<Activity> activities){
        long evantDay = 0;
        calendarView.removeAllEvents();
        for(int i = 0; i < activities.size(); i++){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(activities.get(i).getActivityDate());
                evantDay = date.getTime();
                Event ev1 = new Event(Color.RED, evantDay, activities.get(i).getTitle());
                calendarView.addEvent(ev1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void manageViews(){
        calendarView.setVisibility(View.GONE);

        ImageView img = (ImageView) myView.findViewById(R.id.toogle_btn);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(calendarView.getVisibility() == VISIBLE){
                    calendarView.setVisibility(View.GONE);
                }else{
                    calendarView.setVisibility(View.VISIBLE);
                }
            }
        });

        calendarView.setVisibility(View.GONE);

        TextView text = (TextView) myView.findViewById(R.id.choseCourse);
        text.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Spinner spinnerActivities = (Spinner) myView.findViewById(R.id.spinnerActivities);
                if(spinnerActivities.getVisibility() == VISIBLE){
                    spinnerActivities.setVisibility(View.GONE);
                }else{
                    spinnerActivities.setVisibility(View.VISIBLE);
                }
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
