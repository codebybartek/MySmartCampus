<?php

namespace App\Http\Controllers;

use App\Group;
use Illuminate\Http\Request;
use App\Http\Resources\Groups as GroupsResource;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;

class GroupsController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $user = Auth::user();

        
        if($user->roles->first()->name == "professor"){
            $groups= $user->groups;
        }else{
            $groups= Group::all();
        }

        return GroupsResource::collection($groups);
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

        $user = Auth::user();

        $group = new Group();
        $group->name = $request->name;
        $group->hash = md5(time()).rand(0, 999);

        Group::create($group->toArray());

        $groupId = Group::all()->where('hash', $group->hash)->pluck('id')->first();

        DB::table('group_user')->insert(
            ['user_id' => $user->id, 'group_id' => $groupId]
        );

        return response()->json([
            'created' => 'Group was added'
        ], 201);
    }

    /**
     * Display the specified resource.
     *
     * @param  String  $hash
     * @return \Illuminate\Http\Response
     */
    public function show($hash)
    {
        $groups = Group::all()->where('hash', $hash);
        return GroupsResource::collection($groups);
    }

    /**
     * Display the specified resource.
     *
     * @param  Int  $id
     * @return \Illuminate\Http\Response
     */
    public function showWithId($id)
    {
        $groups = Group::all()->where('id', $id);
        return GroupsResource::collection($groups);
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
        
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $group = Group::where('id', $id);
        $group->delete();

        return response()->json([
            'deleted' => 'Group was deleted'
        ], 200);
    }
}
