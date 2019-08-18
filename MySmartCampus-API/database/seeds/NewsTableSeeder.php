<?php

use App\News;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class NewsTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('news')->delete();

        //Create news

        $news = array(
            ['id' => 1, 'title' => 'News1', 'news_date' => date('Y-m-d H:i:s'), 'img_src' => 'http//df.com', 'content' => 'dsadas asd asd', 'hash' => md5(time()).rand(0, 999)],
            ['id' => 2, 'title' => 'News2', 'news_date' => date('Y-m-d H:i:s'), 'img_src' => 'http//df.com', 'content' => 'asda asd asd asd', 'hash' => md5(time()).rand(0, 999)],
            ['id' => 3, 'title' => 'News3', 'news_date' => date('Y-m-d H:i:s'), 'img_src' => 'http//df.com', 'content' => 'asdasd asd asd', 'hash' => md5(time()).rand(0, 999)],
            ['id' => 4, 'title' => 'News4', 'news_date' => date('Y-m-d H:i:s'), 'img_src' => 'http//df.com', 'content' => 'asdasd asd as', 'hash' => md5(time()).rand(0, 999)],
        );

        foreach ($news as $newsSingle) {
            News::create($newsSingle);

        }

        //Create course_news

        DB::table('course_news')->delete();

        DB::table('course_news')->insert(['id' => 1, 'course_id' => 1, 'news_id' => 1]);
        DB::table('course_news')->insert(['id' => 2, 'course_id' => 2, 'news_id' => 2]);
        DB::table('course_news')->insert(['id' => 3, 'course_id' => 2, 'news_id' => 3]);
        DB::table('course_news')->insert(['id' => 4, 'course_id' => 3, 'news_id' => 3]);
    }
}
