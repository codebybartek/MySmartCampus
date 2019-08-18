<?php

namespace App\Http\Controllers;

use App\Course;
use App\News;
use App\Subject;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

use App\Http\Resources\News as NewsResources;

class NewsController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $user = Auth::user();
        $courses = $user::with('courses')->first()->courses;

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
        $professor = Auth::user();
        $isAdmin = $professor->isAdmin;
        if(!$isAdmin) {
            News::create($request->all());

            return response()->json([
                'created' => 'News was added'
            ], 201);
        }else{
            return "unauthorized";
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        $professor = Auth::user();
        $isAdmin = $professor->isAdmin;
        if(!$isAdmin) {
            $news = News::all()->where('news_id', $id);
            return $news;
        }else{
            return "unauthorized";
        }
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
        $professor = Auth::user();
        $isAdmin = $professor->isAdmin;
        if(!$isAdmin) {
            $news = News::where('news_id', $id);
            $news->delete();

            return response()->json([
                'deleted' => 'News was deleted'
            ], 200);
        }else{
            return "unauthorized";
        }
    }
}
