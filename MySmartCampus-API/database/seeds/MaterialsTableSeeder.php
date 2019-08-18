<?php

use App\Material;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class MaterialsTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('materials')->delete();

        //Create materials

        $materials = array(
            ['id' => 1, 'title' => 'Material1', 'material_date' => date('Y-m-d H:i:s'), 'attachment_url' => 'http//df.com', 'content' => 'dsadas asd asd', 'hash' => md5(time()).rand(0, 999)],
            ['id' => 2, 'title' => 'Material2', 'material_date' => date('Y-m-d H:i:s'), 'attachment_url' => 'http//df.com', 'content' => 'asda asd asd asd', 'hash' => md5(time()).rand(0, 999)],
            ['id' => 3, 'title' => 'Material3', 'material_date' => date('Y-m-d H:i:s'), 'attachment_url' => 'http//df.com', 'content' => 'asdasd asd asd', 'hash' => md5(time()).rand(0, 999)],
            ['id' => 4, 'title' => 'Material4', 'material_date' => date('Y-m-d H:i:s'), 'attachment_url' => 'http//df.com', 'content' => 'asdasd asd as', 'hash' => md5(time()).rand(0, 999)],
        );

        foreach ($materials as $material) {
            Material::create($material);

        }

        //Create activity_material

        DB::table('activity_material')->delete();

        DB::table('activity_material')->insert(['id' => 1, 'activity_id' => 1, 'material_id' => 1]);
        DB::table('activity_material')->insert(['id' => 2, 'activity_id' => 2, 'material_id' => 2]);
        DB::table('activity_material')->insert(['id' => 3, 'activity_id' => 2, 'material_id' => 3]);
        DB::table('activity_material')->insert(['id' => 4, 'activity_id' => 3, 'material_id' => 3]);
    }
}
