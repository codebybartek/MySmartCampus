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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mysmartcampus.Adapters.GradesListAdapter;

import com.example.mysmartcampus.MainActivity;

import com.example.mysmartcampus.Models.Grade;
import com.example.mysmartcampus.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExamContent extends Fragment {

    View myView;

    GradesListAdapter adapterListGrades;
    ListView mListViewGrades;
    public static ArrayList<Grade> grades = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.exam_content, container, false);

        mListViewGrades = (ListView) myView.findViewById(R.id.gradesList);

        getGrades();

        Button backToGroups = (Button) myView.findViewById(R.id.backBtn);
        backToGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.api_view, new Exams()).commit();
            }
        });

        return myView;
    }

    public void ShowGrades(){
        Toast.makeText(myView.getContext(), grades.get(0).getGradeDate(), Toast.LENGTH_SHORT).show();
        TextView myAwesomeTextView = (TextView)  myView.findViewById(R.id.examTitle);
        myAwesomeTextView.setText("Exam: " + Exams.exam.getExamTitle());

        final FragmentManager manager = getActivity().getSupportFragmentManager();
        adapterListGrades = new GradesListAdapter(myView.getContext(), R.layout.adapter_view_grades, grades);
        mListViewGrades.setAdapter(adapterListGrades);
    }

    private void getGrades() {
        Toast.makeText(myView.getContext(), Exams.exam.getExamHash(), Toast.LENGTH_SHORT).show();
        String activity_url = "http://vps740401.ovh.net/api/exams/" + Exams.exam.getExamHash();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, activity_url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);

                            JSONArray responseArray = obj.getJSONArray("data");
                            for (int i = 0; i < responseArray.length(); i++) {
                                JSONObject ObjectData = responseArray.getJSONObject(i);


                                JSONArray responseArrayGrade = ObjectData.getJSONArray("grades");

                                //Create String out of the Parsed JSO

                                for (int j = 0; j < responseArrayGrade.length(); j++) {
                                    JSONObject ObjectDataGrade = responseArrayGrade.getJSONObject(j);
                                    Integer Grade = ObjectDataGrade.getInt("grade");
                                    String gradeDate = ObjectDataGrade.getString("grade_date");

                                    JSONObject ObjectDataStudent = ObjectDataGrade.getJSONObject("user");

                                    String studentName = ObjectDataStudent.getString("name");
                                    String studentId = ObjectDataStudent.getString("id");

                                    Grade grade = new Grade(gradeDate, Grade, studentId,  studentName);
                                    grades.add(grade);
                                }




                            }

                            if(!grades.isEmpty()) {
                                ShowGrades();
                            }else{
                                Toast.makeText(myView.getContext(),
                                        "The list is empty!", Toast.LENGTH_SHORT).show();
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

        // Access the RequestQueue through your singleton class.
        RequestQueue requestQueue = Volley.newRequestQueue(myView.getContext());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}
