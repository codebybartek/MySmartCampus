<?php

use App\Group;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;

class GroupsTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {

        DB::table('groups')->delete();

        //Create Groups

        $groups = array(
            ['id' => 1, 'name' => '12A', 'hash' => md5(time()).rand(0, 999)],
            ['id' => 2, 'name' => '12B', 'hash' => md5(time()).rand(0, 999)],
            ['id' => 3, 'name' => '13A', 'hash' => md5(time()).rand(0, 999)],
            ['id' => 4, 'name' => '13B', 'hash' => md5(time()).rand(0, 999)],
            ['id' => 5, 'name' => '14A', 'hash' => md5(time()).rand(0, 999)],
        );

        foreach ($groups as $group) {
            Group::create($group);

        }

        //Create group_user

        DB::table('group_user')->delete();

        DB::table('group_user')->insert(['id' => 1, 'group_id' => 1, 'user_id' => 1]);
        DB::table('group_user')->insert(['id' => 2, 'group_id' => 2, 'user_id' => 1]);
        DB::table('group_user')->insert(['id' => 3, 'group_id' => 3, 'user_id' => 1]);
    }
}
