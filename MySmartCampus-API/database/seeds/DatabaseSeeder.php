<?php

use App\User;
use App\Role;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;

class DatabaseSeeder extends Seeder
{
    /**
     * Seed the application's database.
     *
     * @return void
     */
    public function run()
    {
        Model::unguard();

        // $this->call(UserTableSeeder::class);
        DB::table('users')->delete();

        //Create Users

        $users = array(
            ['id' => 1, 'name' => 'bartek', 'email' => 'bartek@gmail.com', 'password' => Hash::make('secret'), 'tagId' => '1234', 'hash' => md5(time()).rand(0, 999)],
            ['id' => 2, 'name' => 'wladek', 'email' => 'wladek@gmail.com', 'password' => Hash::make('secret'), 'tagId' => '1234', 'hash' => md5(time()).rand(0, 999)],
        );

        // Loop through each user above and create the record for them in the database
        foreach ($users as $user) {
            User::create($user);

        }

        //Create Roles

        DB::table('roles')->delete();

        $roles = array(
            ['id' => 1, 'name' => 'admin'],
            ['id' => 2, 'name' => 'professor'],
            ['id' => 3, 'name' => 'user'],
        );

        foreach ($roles as $role)
        {
            Role::create($role);
        }

        //Relate users/roles

        DB::table('role_user')->delete();

        DB::table('role_user')->insert([
            'user_id' => 1,
            'role_id' => 2
        ]);

        DB::table('role_user')->insert([
            'user_id' => 2,
            'role_id' => 3
        ]);

        $this->call([
            GroupsTableSeeder::class,
            SubjectsTableSeeder::class,
            CoursesTableSeeder::class,
            ActivitiesTableSeeder::class,
            MaterialsTableSeeder::class,
            NewsTableSeeder::class,
        ]);


        Model::reguard();
    }
}
