<?php

use App\Subject;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class SubjectsTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('subjects')->delete();

        //Create Groups

        $subjects = array(
            ['id' => 1, 'name' => 'Webapplications', 'hash' => md5(time()).rand(0, 999)],
            ['id' => 2, 'name' => 'Maths', 'hash' => md5(time()).rand(0, 999)],
            ['id' => 3, 'name' => 'Algorithms', 'hash' => md5(time()).rand(0, 999)]
        );

        foreach ($subjects as $subject) {
            Subject::create($subject);

        }
    }
}
