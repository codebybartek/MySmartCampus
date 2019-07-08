<?php

namespace App\Http\Controllers\professorController;

use App\Course;
use App\Material;
use App\Subject;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
class MaterialsController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $professor = Auth::user();
        $professorId = $professor->id;
        $isAdmin = $professor->isAdmin;
        $subjectId = Subject::all()->where('professor_id', $professorId)->pluck("subject_id");
        $allCoursesId = array();
        $c = 0;
        for($i=0; $i<count($subjectId); $i++){
            $coursesId = Course::all()->where('subject_id', $subjectId[$i])->pluck("course_id");
            for($j=0; $j<count($coursesId); $j++){
                $allCoursesId[$c] = $coursesId[$j];
                $c++;
            }
        }

        $d = 0;
        $c = 0;
        $allMaterials = array();
        for($i=0; $i<count($allCoursesId); $i++){
            $materials[$d] = Material::all()->where('course_id', $allCoursesId[$i])->values()->all();
            for($j = 0; $j < count($materials[$d]) ; $j++){
                $allMaterials[$c] =  $materials[$d][$j];
                $c++;
            }
            $d++;
        }

        if(!$isAdmin) {
            return $allMaterials;
        }
        return "unauthorized";
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
            Material::create($request->all());

            return response()->json([
                'created' => 'Material was added'
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
            $material = Material::all()->where('material_id', $id);
            return $material;
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
            $material = Material::all()->where('material_id', $id)->first();
            return $material;
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
            $material = Material::where('material_id', $id)->first();
            $material->title = $request->title;
            $material->content = $request->contentt;
            $material->material_date = $request->material_date;
            $material->attachment_url = $request->attachment_url;
            $material->course_id = $request->course_id;
            $material->save();

            return response()->json([
                'updated' => 'Material was updated'
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
            $material = Material::where('material_id', $id);
            $material->delete();

            return response()->json([
                'deleted' => 'Material was deleted'
            ], 200);
        }else{
            return "unauthorized";
        }
    }
}