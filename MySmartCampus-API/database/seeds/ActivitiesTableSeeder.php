<?php

use App\Activity;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class ActivitiesTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('activities')->delete();

        //Create activities

        $activities = array(
            ['id' => 1, 'title' => 'Activity1', 'activityDate' => date('Y-m-d H:i:s'), 'duration' => 120, 'class_room' => '11df', 'hash' => md5(time()).rand(0, 999)],
            ['id' => 2, 'title' => 'Activity2', 'activityDate' => date('Y-m-d H:i:s'), 'duration' => 120, 'class_room' => '12df', 'hash' => md5(time()).rand(0, 999)],
            ['id' => 3, 'title' => 'Activity3', 'activityDate' => date('Y-m-d H:i:s'), 'duration' => 120, 'class_room' => '11df', 'hash' => md5(time()).rand(0, 999)],
            ['id' => 4, 'title' => 'Activity4', 'activityDate' => date('Y-m-d H:i:s'), 'duration' => 120, 'class_room' => '11df', 'hash' => md5(time()).rand(0, 999)],
        );

        foreach ($activities as $activity) {
            Activity::create($activity);

        }

        //Create activity_course

        DB::table('activity_course')->delete();

        DB::table('activity_course')->insert(['id' => 1, 'activity_id' => 1, 'course_id' => 1]);
        DB::table('activity_course')->insert(['id' => 2, 'activity_id' => 2, 'course_id' => 1]);
        DB::table('activity_course')->insert(['id' => 3, 'activity_id' => 3, 'course_id' => 2]);
        DB::table('activity_course')->insert(['id' => 4, 'activity_id' => 4, 'course_id' => 2]);
    }
}
