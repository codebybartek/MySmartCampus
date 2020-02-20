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
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mysmartcampus.Adapters.StudentsListAdapter;
import com.example.mysmartcampus.MainActivity;
import com.example.mysmartcampus.Models.Activity;
import com.example.mysmartcampus.Models.Student;
import com.example.mysmartcampus.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class CheckAttendanceList extends Fragment {

    static View myView;
    static StudentsListAdapter adapterList;
    static ListView mListView;
    public static ArrayList<Student> studentsAll;
    public static ArrayList<Student> studentsPresent = new ArrayList<>();
    private Activity activity;
    private boolean isChecked = false;

    private String attandanceListUrl = "http://vps740401.ovh.net/api/attandancelist";

    private Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.check_attendance_list, container, false);

        mListView = (ListView) myView.findViewById(R.id.listView);

        studentsPresent.clear();



       if(Activities.activity != null){
            if(Activities.activity.getChecked() == 1){
                Toast.makeText(myView.getContext(), Activities.activity.getHash(), Toast.LENGTH_SHORT).show();
                getPresentStudents();
                isChecked = true;
                ShowList();
            }else{
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String strDate = dateFormat.format(date);
                if(CurrentActivities.activity.getActivityDate().equals(strDate)){
                    Activity activity = null;
                    for (int i = 0; i < MainActivity.attendanceList.activities.size(); i++) {
                        if (MainActivity.attendanceList.activities.get(i).getTitle().equals(Activities.activity.getTitle())) {
                            activity = MainActivity.attendanceList.activities.get(i);
                        }
                    }
                    if (activity != null) {
                        studentsAll = activity.getStudents();
                    }
                }else{
                    Toast.makeText(myView.getContext(), "You could only check activity in the same day!", Toast.LENGTH_SHORT).show();
                }
            }
        }else if(CurrentActivities.activity != null){
            if(CurrentActivities.activity.getChecked() == 1){
                studentsPresent = CurrentActivities.activity.getStudentsPresence();
                studentsAll = studentsPresent;
                isChecked = true;
                ShowList();
            }else{
                studentsAll = CurrentActivities.activity.getStudents();
            }
        }else {
            final FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.api_view, new CurrentActivities()).commit();
        }


        if(CurrentActivities.activity != null) {
            if (CurrentActivities.activity.getChecked() == 1) {
                studentsPresent = CurrentActivities.activity.getStudentsPresence();
                studentsAll = studentsPresent;
                isChecked = true;
                ShowList();
            } else {
                studentsAll = CurrentActivities.activity.getStudents();
            }
            activity = CurrentActivities.activity;
        }

        Button backToActivities = (Button) myView.findViewById(R.id.backBtn);
        backToActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.api_view, new CurrentActivities()).commit();
            }
        });

        //Add the Attendancelist

        if(isChecked == false) {
            Button saveList = (Button) myView.findViewById(R.id.saveList);
            saveList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    for (int i = 0; i < studentsPresent.size(); i++) {
                        final int finalI = i;
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, attandanceListUrl,
                                new Response.Listener<String>() {


                                    @Override
                                    public void onResponse(String response) {
                                        try {

                                            JSONObject obj = new JSONObject(response);
                                            String created = obj.getString("created");
                                            CurrentActivities.activity.setChecked();
                                            CurrentActivities.activity.setStudentsPresence(studentsPresent);
                                            Toast.makeText(myView.getContext(), created, Toast.LENGTH_SHORT).show();
                                            hideButton();

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(myView.getContext(),
                                        error.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                HashMap<String, String> headers = new HashMap<String, String>();
                                headers.put("Authorization", "Bearer " + MainActivity.token);
                                return headers;
                            }

                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("data_presence", studentsPresent.get(finalI).getTagDate());
                                params.put("user_id", String.valueOf(studentsPresent.get(finalI).getId()));
                                params.put("activity_id", String.valueOf(activity.getId()));

                                return params;
                            }
                        };


                        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.context);

                        requestQueue.add(stringRequest);
                    }
                }
            });
        }else{
            Button saveList = (Button) myView.findViewById(R.id.saveList);
            saveList.setVisibility(View.GONE);
            Toast.makeText(myView.getContext(), "This activity was checked before!", Toast.LENGTH_SHORT).show();
        }

        return myView;
    }

    //FUNCTIONS//

    public static void ShowList(){
        if(studentsPresent.size() == 0){
            Toast.makeText(myView.getContext(), "There are not any students on list!", Toast.LENGTH_SHORT).show();
        }
        adapterList = new StudentsListAdapter(myView.getContext(), R.layout.adapter_view_students, studentsPresent);
        mListView.setAdapter(adapterList);
    }
    public void hideButton(){
        Button saveList = (Button) myView.findViewById(R.id.saveList);
        saveList.setVisibility(View.GONE);
    }

    private void getPresentStudents() {

        String activity_url = "http://vps740401.ovh.net/api/activities/" + Activities.activity.getHash();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, activity_url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);

                            JSONArray responseArray = obj.getJSONArray("data");
                            for (int i = 0; i < responseArray.length(); i++) {
                                JSONObject ObjectData = responseArray.getJSONObject(i);


                                JSONArray responseArrayStudent = ObjectData.getJSONArray("students");

                                //Create String out of the Parsed JSO

                                for (int j = 0; j < responseArrayStudent.length(); j++) {
                                    JSONObject ObjectDataStudent = responseArrayStudent.getJSONObject(j);
                                    Integer studentId = ObjectDataStudent.getInt("id");
                                    String studentName = ObjectDataStudent.getString("name");
                                    String datePresence = ObjectDataStudent.getString("datePresence");
                                    String tagId = ObjectDataStudent.getString("tagId");

                                    Student student = new Student(studentId, studentName, "",  "", tagId,  false, "");
                                    studentsPresent.add(student);
                                }

                                ShowList();

                            }

                            //Main.courses = courses;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                //Display error message whenever an error occurs
                Toast.makeText(myView.getContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + MainActivity.token);
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(myView.getContext());

        requestQueue.add(stringRequest);
    }

    /*public static void Istagged(String tag){
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
        }*/

}
