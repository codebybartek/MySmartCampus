<?php

namespace App\Http\Controllers;

use App\Material;
use App\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

use App\Http\Resources\Materials as MaterialResources;
use Illuminate\Support\Facades\DB;

class MaterialsController extends Controller
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

        $courses = collect($courses);

        return MaterialResources::collection($courses);
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
        $attachmentName = $request->attachment->getClientOriginalName();
        $request->attachment->move(public_path('materials'), $attachmentName);

        $material = new Material();
        $material->title = $request->title;
        $material->content = $request->body;
        $material->attachment_url = 'http://'.request()->getHost().':8000/images/'.$attachmentName;
        $material->material_date = date('Y-m-d H:i:s');
        $material->hash = md5(time()).rand(0, 999);

        Material::create($material->toArray());

        $materialId = Material::all()->where('hash', $material->hash)->pluck('id')->first();

        DB::table('activity_material')->insert(
            ['activity_id' => $request->activity_id, 'material_id' => $materialId]
        );

        return response()->json([
            'created' => 'Material was added'
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
        $material = Material::all()->where('hash', $hash)->first();
        return $material;
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
        $material = Material::where('id', $id);
        $material->delete();

        DB::table('activity_material')->where('material_id', $id)->delete();

        return response()->json([
            'deleted' => 'Material was deleted'
        ], 200);
    }
}
