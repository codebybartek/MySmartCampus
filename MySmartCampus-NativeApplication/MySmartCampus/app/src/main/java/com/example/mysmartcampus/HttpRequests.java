package com.example.mysmartcampus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Px;
import android.view.ViewDebug;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.mysmartcampus.Models.Activity;
import com.example.mysmartcampus.Models.Course;
import com.example.mysmartcampus.Models.Exam;
import com.example.mysmartcampus.Models.Group;
import com.example.mysmartcampus.Models.Material;
import com.example.mysmartcampus.Models.News;
import com.example.mysmartcampus.Models.Student;
import com.example.mysmartcampus.Models.Subject;
import com.example.mysmartcampus.Views.Exams;
import com.example.mysmartcampus.Views.Groups;
import com.example.mysmartcampus.Views.Main;
import com.example.mysmartcampus.Views.Materials;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class HttpRequests {

    public Context mContext;
    private String loginUrl = "http://vps740401.ovh.net/api/login";
    private String loginMobileUrl = "http://vps740401.ovh.net/api/loginMobile";
    private String coursesUrl = "http://vps740401.ovh.net/api/courses";
    private String groupsUrl = "http://vps740401.ovh.net/api/groups";
    private String getUserUrl = "http://vps740401.ovh.net/api/user";
    private String examsUrl = "http://vps740401.ovh.net/api/grades";
    private String materialsUrl = "http://vps740401.ovh.net/api/materials";

    private String mobile;

    private static ArrayList<Student> studentsInGroup = new ArrayList<>();
    private static ArrayList<Subject> subjects = new ArrayList<>();


    private Group group;


    public HttpRequests(String email, String password){
        this.mContext = MainActivity.context;
        Main.activities.clear();
        Main.courses.clear();
        if(Customization.user == null) {
            login(email, password);
        }else {
            loginMobile();
        }
    }


    public void login(final String email, final String password) {

        mobile = generateString(new Random(), "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890", 10);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);
                            MainActivity.token = obj.getString("token");
                            loadCourses();
                            loadUser();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                //Display error message whenever an error occurs
                MainActivity.failedLogin = true;
                Intent intent = new Intent(MainActivity.context, LoginActivity.class);
                MainActivity.context.startActivity(intent);

            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                params.put("mobile", mobile);

                return params;
            }
        };

        // Access the RequestQueue through your singleton class.
        RequestQueue requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    public void loginMobile() {

        mobile = Customization.user.getMobileToken();
        Toast.makeText(mContext.getApplicationContext(),
                mobile, Toast.LENGTH_SHORT).show();
        Toast.makeText(mContext.getApplicationContext(),
                Customization.user.getTagId(), Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginMobileUrl,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);
                            MainActivity.token = obj.getString("token");
                            loadCourses();
                            loadGroups();
                            loadExams();
                            loadMaterials();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                //Display error message whenever an error occurs
                MainActivity.failedLogin = true;
                Intent intent = new Intent(MainActivity.context, LoginActivity.class);
                MainActivity.context.startActivity(intent);

            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("tag", Customization.user.getTagId());
                params.put("mobile_token", Customization.user.getMobileToken());

                return params;
            }
        };

        // Access the RequestQueue through your singleton class.
        RequestQueue requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    public void loadCourses() {

        //Clear subjects lis
        Customization.user = null;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, coursesUrl,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);
                            JSONArray responseArray = obj.getJSONArray("data");

                            for (int i = 0; i < responseArray.length(); i++) {

                                ArrayList<Activity> activities = new ArrayList<>();
                                ArrayList<News> newsArray = new ArrayList<>();

                                JSONObject ObjectData = responseArray.getJSONObject(i);

                                Integer courseId = ObjectData.getInt("id");
                                String courseName = ObjectData.getString("name");

                                JSONObject ObjectDataGroup = ObjectData.getJSONObject("group");

                                String groupHash = ObjectDataGroup.getString("hash");

                                JSONObject ObjectDataSubject = ObjectData.getJSONObject("subject");

                                String subjectName = ObjectDataSubject.getString("name");

                                JSONArray responseArrayActivity = ObjectData.getJSONArray("activities");

                                //GET ACTIVITIES

                                Activity activity = null;

                                for (int j = 0; j < responseArrayActivity.length(); j++) {



                                    Integer activity_id = responseArrayActivity.getJSONObject(j).getInt("id");
                                    String title = responseArrayActivity.getJSONObject(j).getString("title");
                                    String hash = responseArrayActivity.getJSONObject(j).getString("hash");
                                    String activityDate = responseArrayActivity.getJSONObject(j).getString("activityDate");
                                    Integer duration = responseArrayActivity.getJSONObject(j).getInt("duration");
                                    Integer checked = responseArrayActivity.getJSONObject(j).getInt("checked");

                                    String[] activityDateD = activityDate.split(" ");

                                    activity = new Activity(activity_id, title, activityDateD[0], activityDateD[1], hash, duration, checked);

                                    activities.add(activity);
                                }

                                //GET NEWS

                                JSONArray responseArrayNews = ObjectData.getJSONArray("news");

                                //Create String out of the Parsed JSON

                                News news = null;

                                for (int j = 0; j < responseArrayNews.length(); j++) {

                                    Integer news_id = responseArrayNews.getJSONObject(j).getInt("id");
                                    String title = responseArrayNews.getJSONObject(j).getString("title");
                                    String content = responseArrayNews.getJSONObject(j).getString("content");
                                    String news_date = responseArrayNews.getJSONObject(j).getString("news_date");
                                    String img_src = responseArrayNews.getJSONObject(j).getString("img_src");

                                    news = new News(title, content,news_date, img_src);

                                    newsArray.add(news);

                                }

                                boolean addSubject = true;

                                for(int s = 0; s < subjects.size(); s++){
                                    if(subjects.get(s).getTitle().equals(subjectName)){
                                        addSubject = false;
                                    }
                                }

                                if(addSubject) {
                                    ArrayList <Course> courses = new ArrayList<>();
                                    Subject subject = new Subject(subjectName, courses);
                                    subjects.add(subject);
                                }


                                addCourse(groupHash, courseName, subjectName, activities, newsArray);


                            }

                            //Customization.initUser("Bartek", "bartek@bartek.com", "dasds", "asdad","asdsad", subjects);
                            loadUser();

                            MainActivity.initAttendanceList();


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
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    private void addCourse(String groupHash, final String courseName, final String subjectName, final ArrayList<Activity> activitiesArray, final ArrayList<News> newsArray) {
        String groups_url = "http://vps740401.ovh.net/api/groups/" + groupHash;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, groups_url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);

                            JSONArray responseArray = obj.getJSONArray("data");
                            for (int i = 0; i < responseArray.length(); i++) {
                                JSONObject ObjectData = responseArray.getJSONObject(i);

                                String groupName = ObjectData.getString("name");

                                JSONArray responseArrayStudent = ObjectData.getJSONArray("students");

                                //Create String out of the Parsed JSO

                                for (int j = 0; j < responseArrayStudent.length(); j++) {
                                    JSONObject ObjectDataStudent = responseArrayStudent.getJSONObject(j);
                                    Integer studentId = ObjectDataStudent.getInt("id");
                                    String studentName = ObjectDataStudent.getString("name");
                                    String email = ObjectDataStudent.getString("email");
                                    String tagId = ObjectDataStudent.getString("tagId");
                                    String hash = ObjectDataStudent.getString("hash");

                                    Student student = new Student(studentId, studentName, email,  hash, tagId,  false, "");
                                    studentsInGroup.add(student);
                                }

                                group = new Group(groupName, studentsInGroup);



                                Course course = new Course(courseName,  group);
                                course.setActivities(activitiesArray);
                                course.setNews(newsArray);

                                Main.courses.add(course);

                                for(int s = 0; s < subjects.size(); s++){
                                    if(subjects.get(s).getTitle().equals(subjectName)){
                                        subjects.get(s).addCourse(course);
                                    }
                                }
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
                Toast.makeText(mContext.getApplicationContext(),
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
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    private void loadUser() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, getUserUrl,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONObject ObjectData = obj.getJSONObject("data");
                            String name = ObjectData.getString("name");
                            String email = ObjectData.getString("email");
                            String tagId = ObjectData.getString("tagId");
                            String photo = ObjectData.getString("photo");
                            Customization.initUser(name, email, mobile, tagId,photo, subjects);

                            saveAndGo();

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
        })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " +  MainActivity.token);
                return headers;
            }
        };

        // Access the RequestQueue through your singleton class.
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        //adding the string request to request queue
        requestQueue.add(stringRequest);

    }

    public void loadGroups() {

        //Clear groups list
        Groups.groups.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, groupsUrl,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);
                            JSONArray responseArray = obj.getJSONArray("data");

                            for (int i = 0; i < responseArray.length(); i++) {


                                ArrayList<Student> students = new ArrayList<>();

                                JSONObject ObjectData = responseArray.getJSONObject(i);

                                Integer groupId = ObjectData.getInt("id");
                                String groupName = ObjectData.getString("name");

                                JSONArray responseArrayStudent = ObjectData.getJSONArray("students");

                                //GET STUDENTS

                                Student student = null;



                                for (int j = 0; j < responseArrayStudent.length(); j++) {

                                    Integer studentId = responseArrayStudent.getJSONObject(j).getInt("id");
                                    String studentName = responseArrayStudent.getJSONObject(j).getString("name");
                                    String email = responseArrayStudent.getJSONObject(j).getString("email");
                                    String tagId = responseArrayStudent.getJSONObject(j).getString("tagId");
                                    String hash = responseArrayStudent.getJSONObject(j).getString("hash");


                                    student = new Student(studentId, studentName, email,hash, tagId, false, null );

                                    students.add(student);
                                }

                                Group group = new Group(groupName, students);

                                Groups.groups.add(group);
                            }

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
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    public void loadExams() {

        //Clear exams list
        Exams.exams.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, examsUrl,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);
                            JSONArray responseArray = obj.getJSONArray("data");

                            for (int i = 0; i < responseArray.length(); i++) {

                                JSONObject ObjectData = responseArray.getJSONObject(i);

                                Integer examId = ObjectData.getInt("id");
                                String examTitle = ObjectData.getString("title");
                                String examHash = ObjectData.getString("hash");

                                Exam exam = new Exam(examId, examTitle, examHash, null);
                                Exams.exams.add(exam);
                            }

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
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    public void loadMaterials() {

        //Clear exams list
        Materials.courses.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, materialsUrl,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject obj = new JSONObject(response);
                            JSONArray responseArray = obj.getJSONArray("data");

                            for (int i = 0; i < responseArray.length(); i++) {
                                JSONObject ObjectData = responseArray.getJSONObject(i);

                                ArrayList<Material> materials = new ArrayList<>();

                                String courseName = ObjectData.getString("course_title");

                                JSONArray responseArrayActivities = ObjectData.getJSONArray("activities");

                                for (int j = 0; j < responseArrayActivities.length(); j++) {
                                    JSONObject ObjectDataActivity = responseArrayActivities.getJSONObject(j);

                                    JSONArray responseArrayMaterials = ObjectDataActivity.getJSONArray("materials");

                                    for (int w = 0; w < responseArrayMaterials.length(); w++) {
                                        JSONObject ObjectDataMaterial = responseArrayMaterials.getJSONObject(w);

                                        String title = ObjectDataMaterial.getString("title");
                                        String material_date = ObjectDataMaterial.getString("material_date");
                                        String attachment_url = ObjectDataMaterial.getString("attachment_url");

                                        Material material = new Material(title, material_date, attachment_url);

                                        materials.add(material);
                                    }
                                }

                                Course course = new Course(courseName, null);
                                course.setMaterials(materials);
                                Materials.courses.add(course);
                            }

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
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    public void saveAndGo() {



        if (Customization.user != null) {

            SharedPreferences sharedPreferences = mContext.getSharedPreferences("shared preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(Customization.user);
            editor.putString("User", json);
            editor.apply();

        }

    }

    public String generateString(Random random, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }
}