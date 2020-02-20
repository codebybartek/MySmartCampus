package com.example.mysmartcampus;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysmartcampus.Models.Activity;
import com.example.mysmartcampus.Models.Course;
import com.example.mysmartcampus.Models.Student;
import com.example.mysmartcampus.Models.User;
import com.example.mysmartcampus.R;
import com.example.mysmartcampus.Views.Activities;
import com.example.mysmartcampus.Views.CheckAttendanceList;
import com.example.mysmartcampus.Views.Courses;
import com.example.mysmartcampus.Views.CurrentActivities;
import com.example.mysmartcampus.Views.Exams;
import com.example.mysmartcampus.Views.Groups;
import com.example.mysmartcampus.Views.Main;
import com.example.mysmartcampus.Views.Materials;
import com.example.mysmartcampus.Views.News;
import com.example.mysmartcampus.Views.Subjects;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Thread.sleep;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    public static String token;

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    public static Context context;
    public static boolean failedLogin = false;
    public static ArrayList<Student> studentsPresent = new ArrayList<>();
    public Activity activity;
    public static FragmentManager manager;
    public static boolean isInternet = false;

    public static AttendanceList attendanceList;

    private String[][] techList = new String[][] { new String[] {
            "android.nfc.tech.MifareUltralight",
            //"android.nfc.tech.MifareClassic"
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        setContentView(R.layout.activity_main);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View hView =  navigationView.getHeaderView(0);
        TextView userName = (TextView)hView.findViewById(R.id.navHeaderText);
        TextView userEmail = (TextView)hView.findViewById(R.id.textView);
        ImageView userPicture = (ImageView)hView.findViewById(R.id.imageView);

        attendanceList = new AttendanceList();

   /*     SharedPreferences sharedPreferences = this.getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(null);
        editor.putString("User", json);
        editor.apply();


*/

        if(isInternetConnection()){

            isInternet = true;

           if(Customization.user == null){
                SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                Gson gson = new Gson();
                String json = sharedPreferences.getString("User", null);

                if(json !=null) {
                    Type type = new TypeToken<User>() {
                    }.getType();
                    Customization.user = gson.fromJson(json, type);

                    if(Customization.user != null) {
                        Picasso.get().load(Customization.user.getPhoto_src()).into(userPicture);
                        userName.setText(Customization.user.getName());
                        userEmail.setText(Customization.user.getEmail());
                    }
                }

                if(Customization.user == null){
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(this, LoginWithCard.class);
                    this.startActivity(intent);
                }
           }else{
                Intent intent = new Intent(this, LoginWithCard.class);
                this.startActivity(intent);
           }

            manager = getSupportFragmentManager();

        }else {
            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("User", null);

            if (json != null) {
                Type type = new TypeToken<User>() {
                }.getType();
                Customization.user = gson.fromJson(json, type);
            }

            for (int i = 0; i < Customization.user.getSubjects().size(); i++) {
                for (int j = 0; j < Customization.user.getSubjects().get(i).getCourses().size(); j++) {
                    Main.courses.add(Customization.user.getSubjects().get(i).getCourses().get(j));
                }
            }

            Toast.makeText(this, "No  internet connection!", Toast.LENGTH_SHORT).show();

            manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.api_view, new Main()).commit();
        }





        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isInternetConnection()) {
                        FragmentManager manager = getSupportFragmentManager();
                        manager.beginTransaction().replace(R.id.api_view, new CurrentActivities()).commit();
                    }else{
                        FragmentManager manager = getSupportFragmentManager();
                        manager.beginTransaction().replace(R.id.api_view, new CurrentActivities()).commit();
                    }
                }
            });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.subjects) {
            manager.beginTransaction().replace(R.id.api_view, new Subjects()).commit();
        } else if (id == R.id.courses) {
            manager.beginTransaction().replace(R.id.api_view, new Courses()).commit();
        } else if (id == R.id.activities) {
            manager.beginTransaction().replace(R.id.api_view, new Activities()).commit();
        } else if (id == R.id.groups) {
            manager.beginTransaction().replace(R.id.api_view, new Groups()).commit();
        } else if (id == R.id.news) {
            manager.beginTransaction().replace(R.id.api_view, new News()).commit();
        } else if (id == R.id.materials) {
            manager.beginTransaction().replace(R.id.api_view, new Materials()).commit();
        }else if (id == R.id.exams) {
            manager.beginTransaction().replace(R.id.api_view, new Exams()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void setDefaultView(){
        manager.beginTransaction().replace(R.id.api_view, new Main()).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction(NfcAdapter.ACTION_TAG_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_TECH_DISCOVERED);

        nfcAdapter.enableForegroundDispatch(this, pendingIntent, new IntentFilter[]{filter}, techList);
    }

    @Override
    protected void onPause() {
        super.onPause();

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent.getAction().equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
            String tag = ByteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));
            Toast.makeText(this, tag, Toast.LENGTH_SHORT).show();
            int searchListLength = CheckAttendanceList.studentsAll.size();
            for (int i = 0; i < searchListLength; i++) {
                if (CheckAttendanceList.studentsAll.get(i).getTagId().contains(tag)) {
                    boolean isOnList = false;
                    Date date = Calendar.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String strDate = dateFormat.format(date);
                    CheckAttendanceList.studentsAll.get(i).setTagDate(strDate);
                    for(int w = 0; w < CheckAttendanceList.studentsPresent.size(); w++){
                        if(CheckAttendanceList.studentsAll.get(i).getTagId().equals(CheckAttendanceList.studentsPresent.get(w).getTagId())){
                            isOnList = true;
                        }
                    }
                    if(!isOnList) {
                        CheckAttendanceList.studentsPresent.add(CheckAttendanceList.studentsAll.get(i));
                    }
                    CheckAttendanceList.ShowList();
                }
            }
        }
    }

    private String ByteArrayToHexString(byte [] inarray) {
        int i, j, in;
        String [] hex = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
        String out= "";

        for(j = 0 ; j < inarray.length ; ++j)
        {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }

    public static void initAttendanceList(){
        attendanceList.loadActivities();
    }

    private boolean isInternetConnection() {

        ConnectivityManager connectivityMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (connectivityMgr != null) {
            activeNetwork = connectivityMgr.getActiveNetworkInfo();
        }
        return activeNetwork != null;
    }

}
