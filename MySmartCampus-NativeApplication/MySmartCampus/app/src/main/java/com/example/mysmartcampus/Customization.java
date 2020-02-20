package com.example.mysmartcampus;

import com.example.mysmartcampus.Models.Activity;
import com.example.mysmartcampus.Models.Course;
import com.example.mysmartcampus.Models.Exam;
import com.example.mysmartcampus.Models.Group;
import com.example.mysmartcampus.Models.Material;
import com.example.mysmartcampus.Models.News;
import com.example.mysmartcampus.Models.Student;
import com.example.mysmartcampus.Models.Subject;
import com.example.mysmartcampus.Models.User;


import java.util.ArrayList;

public class Customization {

    public static User user = null;
    public static ArrayList<Exam> exams = new ArrayList<>();

    public static void initUser(String name, String email, String mobileToken,String tagId, String photo, ArrayList<Subject> subjects){
        user = new User(name, email, mobileToken, tagId, photo, subjects);

        MainActivity.setDefaultView();
        //addStudents();



    }

    /*public static void addActivities(Course course){
        Activity activity1 = new Activity(1, "Activity1", "2019-07-02","12:20:00",200, 0);
        Activity activity2 = new Activity(2,"Activity2", "2019-07-01","14:20:00",200, 0);
        Activity activity3 = new Activity(3,"Activity3", "2019-07-03","13:20:00",200, 0);
        Activity activity4 = new Activity(4, "Activity4", "2019-06-02","10:20:00",200, 0);
        Activity activity5 = new Activity(5,"Activity5", "2019-07-02","12:20:00",200, 0);
        Activity activity6 = new Activity(6,"Activity6", "2019-07-01","14:20:00",200, 0);
        Activity activity7 = new Activity(7,"Activity7", "2019-07-03","13:20:00",200, 0);
        Activity activity8 = new Activity(8,"Activity8", "2019-06-02","10:20:00",200, 0);
        Activity activity14 = new Activity(9,"Activit9", "2019-07-01","14:20:00",200, 0);
        Activity activity9 = new Activity(10,"Activity10", "2019-07-03","13:20:00",200, 0);
        Activity activity10 = new Activity(11,"Activity11", "2019-06-02","10:20:00",200, 0);
        Activity activity11 = new Activity(12,"Activity12", "2019-07-01","14:20:00",200, 0);
        Activity activity12 = new Activity(13,"Activity13", "2019-07-03","13:20:00",200, 0);
        Activity activity13 = new Activity(14,"Activity14", "2019-06-02","10:20:00",200, 0);

        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(activity1);
        activities.add(activity2);
        activities.add(activity3);
        activities.add(activity4);
        activities.add(activity5);
        activities.add(activity6);
        activities.add(activity7);
        activities.add(activity8);
        activities.add(activity9);
        activities.add(activity10);
        activities.add(activity11);
        activities.add(activity12);
        activities.add(activity13);
        activities.add(activity14);

        course.setActivities(activities);

    }

    public static void addNews(Course course){
        News news1 = new News("News1", "2019-07-02","Info asd as sad sd asd asd asd asdas sa asd asd sad asd sadsd adsa sad asds dasd assd sadsa sad sad asd sd asd asdasasdsaasd asd as","https://i.imgur.com/DvpvklR.png");
        News news2 = new News("News2", "2019-07-01","Info","https://i.imgur.com/DvpvklR.png");
        News news3 = new News("News3", "2019-07-03","Info","https://i.imgur.com/DvpvklR.png");
        News news4 = new News("News4", "2019-06-02","Info","https://i.imgur.com/DvpvklR.png");

        ArrayList<News> newsAll  = new ArrayList<>();
        newsAll.add(news1);
        newsAll.add(news2);
        newsAll.add(news3);
        newsAll.add(news4);

        course.setNews(newsAll);

    }

    public static void addMaterials(Course course){
        Material material1 = new Material("Material1", "2019-07-02","https://achilles.tu.kielce.pl/portal/Members/f738f50087954a87b5dee67e9ebac606/algorymty-grafiki-komputerowej/tematy-projektow-2019.pdf");
        Material material2 = new Material("Material2", "2019-07-01","https://achilles.tu.kielce.pl/portal/Members/9487853b374349e88094142d8428c1f9/2018-2019-semestr-letni/systemy-odporne-na-bledy/laboratorium/systemy-odporne-na-bledy.pdf/@@download/file/Systemy odporne na błędy.pdf");
        Material material3 = new Material("Material3", "2019-07-03","https://achilles.tu.kielce.pl/portal/Members/f738f50087954a87b5dee67e9ebac606/algorymty-grafiki-komputerowej/tematy-projektow-2019.pdf");
        Material material4 = new Material("Material4", "2019-06-02","https://achilles.tu.kielce.pl/portal/Members/f738f50087954a87b5dee67e9ebac606/algorymty-grafiki-komputerowej/tematy-projektow-2019.pdf");

        ArrayList<Material> materials = new ArrayList<>();
        materials.add(material1);
        materials.add(material2);
        materials.add(material3);
        materials.add(material4);

        course.setMaterials(materials);

    }

    public static void addGroups(ArrayList<Student> students){

        Group group = new Group("12A", students);

        addCourses(group);
    }

    public static void addStudents(){
        Student student1 = new Student("Bartek Kozieł1", "bartekkoziel1@gmail.com", "1234", "042E8DBADA5A80", false, null);
        Student student2 = new Student("Bartek Kozieł2", "bartekkoziel2@gmail.com", "1234","047B5092E05B80", false, null);
        Student student3 = new Student("Bartek Kozieł3", "bartekkoziel3@gmail.com", "1234","39@DA123DG%", false, null);
        Student student4 = new Student("Bartek Kozieł4", "bartekkoziel4@gmail.com", "1234","042E8DBADA5A80", false, null);
        Student student5 = new Student("Bartek Kozieł5", "bartekkoziel5@gmail.com", "1234","59@DA123DG%", false, null);

        ArrayList<Student> students = new ArrayList<>();

        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);

        Exam exam1 = new Exam("exam1", "2019-07-02", (char) 5, student1);
        Exam exam2 = new Exam("exam2", "2019-08-22", (char) 5, student2);
        Exam exam3 = new Exam("exam3", "2019-09-10", (char) 5, student3);

        exams.add(exam1);
        exams.add(exam2);
        exams.add(exam3);


        addGroups(students);

    }

    public static void addCourses(Group group){

        Course course = new Course("Course1", group);
        Course course2 = new Course("Course2", group);

        addActivities(course);
        addMaterials(course);
        addNews(course);
        ArrayList<Course> courses = new ArrayList<>();

        course.setExams(exams);

        courses.add(course);
        courses.add(course2);

        addSubjects(courses);

    }

    public static void addSubjects(ArrayList<Course> courses){
        ArrayList<Course> courses2 = new ArrayList<>();
        Student student1 = new Student("Bartek Kozieł1", "bartekkoziel1@gmail.com", "1234", "19@DA123DG%", false, null);
        ArrayList<Student> students = new ArrayList<>();
        students.add(student1);
        Group group = new Group("17A", students);
        Course course55 = new Course("Course25", group);
        Course course56 = new Course("Course26", group);
        Activity activity1 = new Activity(15, "Activity1222", "2019-07-02","12:20:00",200, 0);
        Material material2 = new Material("Material2", "2019-07-01","https://achilles.tu.kielce.pl/portal/Members/9487853b374349e88094142d8428c1f9/2018-2019-semestr-letni/systemy-odporne-na-bledy/laboratorium/systemy-odporne-na-bledy.pdf/@@download/file/Systemy odporne na błędy.pdf");
        News news1 = new News("News1", "2019-07-02","Info asd as sad sd asd asd asd asdas sa asd asd sad asd sadsd adsa sad asds dasd assd sadsa sad sad asd sd asd asdasasdsaasd asd as","https://i.imgur.com/DvpvklR.png");
        course56.addActivity(activity1);
        course56.addMaterial(material2);
        course56.addNews(news1);
        courses2.add(course55);
        courses2.add(course56);
        Subject subject = new Subject("Subject1", courses);
        Subject subject2 = new Subject("Subject2", courses2);
        user.addSubject(subject);
        user.addSubject(subject2);

    }*/
}
