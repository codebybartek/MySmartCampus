<?php

namespace App\Http\Controllers;

use App\News;
use App\User;
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
        $courses = $user->courses;

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
        $image_resize->save(public_path('images/' .$imageName));

        $news = new News();
        $news->title = $request->title;
        $news->content = $request->body;
        $news->img_src = 'http://'.request()->getHost().':8000/images/'.$imageName;
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
        $professor = Auth::user();
        $isAdmin = $professor->isAdmin;
        if(!$isAdmin) {
            $news = News::all()->where('news_id', $id)->first();
            return $news;
        }else{
            return "unauthorized";
        }
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
        $professor = Auth::user();
        $isAdmin = $professor->isAdmin;
        if(!$isAdmin) {
            $news = News::where('news_id', $id)->first();
            $news->title = $request->title;
            $news->content = $request->contentt;
            $news->news_date = $request->news_date;
            $news->duration = $request->duration;
            $news->img_src = $request->img_src;
            $news->course_id = $request->course_id;
            $news->save();

            return response()->json([
                'updated' => 'News was updated'
            ], 201);
        }else{
            return "unauthorized";
        }
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
