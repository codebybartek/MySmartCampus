<?php

namespace App\Http\Controllers;

use App\News;
use App\User;
use App\Course;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Intervention\Image\ImageManagerStatic as Image;


use App\Http\Resources\News as NewsResources;
use Illuminate\Support\Facades\DB;

class NewsController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $AuthUser = Auth::user();
        $user = User::all()->where('id', $AuthUser->id)->first();
        
         if($user->roles->first()->name == "professor"){
            $courses = $user->courses;
        }else{
            $courses = [];
            $groupId = DB::table('group_user')->where('user_id', $user->id)->pluck('group_id')->first();
            $coursesTemp = Course::all()->where('group_id', $groupId);
            $i = 0;
            foreach ($coursesTemp as $key => $value) {
                $courses[$i] = $value;
                $i++;
            }
        }

        $newsAll = [];
        $w = 0;
        for($i = 0; $i < count($courses); $i++){
            $news = $courses[$i]->news;
            for($j = 0; $j < count($news); $j++) {
                $newsAll[$w]=  $news[$j];
                $w++;
            }
        }

        $news = collect($newsAll);

        return NewsResources::collection($news);
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        /*$imageName = $request->image->getClientOriginalName();
        $request->image->move(public_path('images'), $imageName);*/

        $image       = $request->file('image');
        $imageName    = $image->getClientOriginalName();

        $image_resize = Image::make($image->getRealPath());
        $image_resize->resize(400, 400);
        $image_resize->save('images/news/'.$imageName);

        $news = new News();
        $news->title = $request->title;
        $news->content = $request->body;
        $news->img_src = 'http://'.request()->getHost().'/images/news/'.$imageName;
        $news->news_date = date('Y-m-d H:i:s');
        $news->hash = md5(time()).rand(0, 999);

        News::create($news->toArray());

        $newsId = News::all()->where('hash', $news->hash)->pluck('id')->first();

        DB::table('course_news')->insert(
            ['course_id' => $request->course_id, 'news_id' => $newsId]
        );

        return response()->json([
            'created' => 'News was added'
        ], 201);
    }

    /**
     * Display the specified resource.
     *
     * @param  string  $hash
     * @return \Illuminate\Http\Response
     */
    public function show($hash)
    {
        $news = News::all()->where('hash', $hash);
        return NewsResources::collection($news);
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
       //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $news = News::where('id', $id);
        $news->delete();

        return response()->json([
            'deleted' => 'News was deleted'
        ], 200);
    }
}
