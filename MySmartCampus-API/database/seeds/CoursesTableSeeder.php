<?php

use App\Course;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class CoursesTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('courses')->delete();

        //Create Courses

        $courses = array(
            ['id' => 1, 'name' => 'Course1', 'group_id' => 2, 'hash' => md5(time()).rand(0, 999)],
            ['id' => 2, 'name' => 'Course2', 'group_id' => 1, 'hash' => md5(time()).rand(0, 999)],
            ['id' => 3, 'name' => 'Course3', 'group_id' => 2, 'hash' => md5(time()).rand(0, 999)],
            ['id' => 4, 'name' => 'Course4', 'group_id' => 4, 'hash' => md5(time()).rand(0, 999)],
        );

        foreach ($courses as $course) {
            Course::create($course);

        }

        //Create Courses_user

        DB::table('course_user')->delete();

        DB::table('course_user')->insert(['id' => 1, 'course_id' => 1, 'user_id' => 1, 'subject_id' => 1]);
        DB::table('course_user')->insert(['id' => 2, 'course_id' => 1, 'user_id' => 1, 'subject_id' => 1]);
        DB::table('course_user')->insert(['id' => 3, 'course_id' => 1, 'user_id' => 1, 'subject_id' => 2]);

    }
}
