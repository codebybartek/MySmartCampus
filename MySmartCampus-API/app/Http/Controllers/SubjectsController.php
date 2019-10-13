<?php

namespace App\Http\Controllers;

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
        $user = Auth::user();

        return SubjectResource::collection($user::with('subjects')->first()->subjects->unique());
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
        $professor = Auth::user();
        $isAdmin = $professor->isAdmin;
        if(!$isAdmin) {
            $subject = Subject::all()->where('subject_id', $id);
            return $subject;
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
            $subject = Subject::where('subject_id', $id)->first();
            $subject->name = $request->name;
            $subject->save();
            return response()->json([
                'updated' => 'Subject was updated'
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
        $subject = Subject::where('id', $id);
        $subject->delete();

        return response()->json([
            'deleted' => 'Subject was deleted'
        ], 200);
    }
}
