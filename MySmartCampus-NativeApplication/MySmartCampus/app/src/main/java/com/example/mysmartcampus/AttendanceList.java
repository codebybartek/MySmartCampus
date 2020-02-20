package com.example.mysmartcampus;

import android.content.Context;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.mysmartcampus.Models.Activity;
import com.example.mysmartcampus.Models.Group;
import com.example.mysmartcampus.Models.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AttendanceList {

    public Context mContext;

    private String activitiesUrl = "http://vps740401.ovh.net/api/currentactivities";

    private Group group;

    public ArrayList<Activity> activities = new ArrayList<>();



    public AttendanceList(){
        this.mContext = MainActivity.context;
    }


    public void loadActivities() {

        if (MainActivity.isInternet) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, activitiesUrl,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONObject obj = new JSONObject(response);
                                JSONArray responseArray = obj.getJSONArray("data");

                                Activity activity = null;

                                for (int i = 0; i < responseArray.length(); i++) {

                                    JSONObject ObjectData = responseArray.getJSONObject(i);
                                    ArrayList<Student> students = new ArrayList<>();
                                    ArrayList<Student> Presentstudents = new ArrayList<>();

                                    Integer activityId = ObjectData.getInt("id");
                                    String activityTitle = ObjectData.getString("title");
                                    String activityDate = ObjectData.getString("activityDate");
                                    Integer activityDuration = ObjectData.getInt("duration");
                                    String activityHash = ObjectData.getString("hash");
                                    Integer checked = ObjectData.getInt("checked");

                                    JSONArray responseArrayStudents = ObjectData.getJSONArray("students");

                                    //GET ACTIVITIES

                                    Student student = null;

                                    for (int j = 0; j < responseArrayStudents.length(); j++) {

                                        Integer student_Id = responseArrayStudents.getJSONObject(j).getInt("id");
                                        String studentName = responseArrayStudents.getJSONObject(j).getString("name");
                                        String studentEmail = responseArrayStudents.getJSONObject(j).getString("email");
                                        String studentTagId = responseArrayStudents.getJSONObject(j).getString("tagId");

                                        student = new Student(student_Id, studentName, studentEmail, "", studentTagId, false, "");

                                        students.add(student);
                                    }

                                    JSONArray responseArrayPresentStudents = ObjectData.getJSONArray("present_students");

                                    //GET ACTIVITIES

                                    Student Presentstudent = null;

                                    for (int j = 0; j < responseArrayPresentStudents.length(); j++) {

                                        Integer student_Id = responseArrayPresentStudents.getJSONObject(j).getInt("id");
                                        String studentName = responseArrayPresentStudents.getJSONObject(j).getString("name");
                                        String studentTagId = responseArrayPresentStudents.getJSONObject(j).getString("tagId");
                                        String studentDataPresence = responseArrayPresentStudents.getJSONObject(j).getString("datePresence");

                                        Presentstudent = new Student(student_Id, studentName, "", "", studentTagId, true, studentDataPresence);

                                        Presentstudents.add(Presentstudent);
                                    }

                                    String[] activityDateD = activityDate.split(" ");

                                    activity = new Activity(activityId, activityTitle, activityDateD[0], activityDateD[1], activityHash, activityDuration, checked);

                                    activity.setStudents(students);
                                    activity.setStudentsPresence(Presentstudents);

                                    activities.add(activity);

                                }

                                //Toast.makeText(mContext.getApplicationContext(), activities.get(0), Toast.LENGTH_SHORT).show();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    //Display error message whenever an error occurs
                    Toast.makeText(mContext.getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization", "Bearer " + MainActivity.token);
                    return headers;
                }
            };

            // Access the RequestQueue through your singleton class.
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);

            //adding the string request to request queue
            requestQueue.add(stringRequest);
        }
    }

}