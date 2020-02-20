<?php

namespace App\Http\Controllers;

use App\User;
use Illuminate\Http\Request;
use App\Subject;
use App\Course;
use App\Http\Resources\Subjects as SubjectResource;
use App\Http\Resources\Subjects as SubjectsResource;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;

class SubjectsController extends Controller
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

        return SubjectResource::collection($user->subjects->unique());
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
        $subject = new Subject();
        $subject->name = $request->name;
        $subject->hash = md5(time()).rand(0, 999);

        Subject::create($subject->toArray());

        $user = Auth::user();
        $subjectId = Subject::all()->where('hash', $subject->hash)->pluck('id')->first();

        DB::table('course_user')->insert(
            ['user_id' => $user->id, 'course_id' => null, 'subject_id' => $subjectId]
        );

        return response()->json([
            'created' => 'Subject was added'
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
        $subject = Subject::all()->where('hash', $hash);
        return SubjectsResource::collection($subject);
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        
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
        $subject = Subject::where('id', $id)->first();
        $subject->name = $request->name;
        $subject->save();
        return response()->json([
            'updated' => 'Subject was updated'
        ], 201);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $subject = Subject::where('id', $id);
        $subject->delete();

        return response()->json([
            'deleted' => 'Subject was deleted'
        ], 200);
    }
}
