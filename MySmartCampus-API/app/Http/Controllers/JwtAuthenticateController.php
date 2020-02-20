<?php

namespace App\Http\Controllers;

use App\Permission;
use App\Role;
use App\User;
use Illuminate\Support\Facades\Hash;
use \Validator;
use Illuminate\Http\Request;

use App\Http\Requests;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Auth;
use Tymon\JWTAuth\Exceptions\JWTException;
use Log;
use Tymon\JWTAuth\Facades\JWTAuth;
use Intervention\Image\ImageManagerStatic as Image;

class JwtAuthenticateController extends Controller
{

    public function index()
    {
        return response()->json(['data' => Auth::user()]);
    }

    public function register(Request $request)
    {
        $val = Validator::make($request->all() , [
            'name' => 'required|string|max:255',
            'email' => 'required|string|email|max:255|unique:users',
            'password' => 'required|string|min:6',
            'tagId' => 'required|string|max:15',
        ]);
        if($val->fails()){
            return response()->json($val->errors()->toJson(), 400);
        }

        /*$image       = $request->image;
        $imageName    = $image->getClientOriginalName();*/

        $image       = $request->file('image');
        $imageName    = $image->getClientOriginalName();

        $image_resize = Image::make($image->getRealPath());
        $image_resize->resize(30, 30);
        $image_resize->save('images/admin/'.$imageName);



        $user = User::create([
            'name' => $request->name,
            'email' => $request->email,
            'password' => Hash::make($request->password),
            'tagId' => $request->tagId,
            'hash' => md5(time()).rand(0, 999),
            'photo' => 'http://'.request()->getHost().'/images/admin/'.$imageName
        ]);

        if($request->role == 1){
            $role = "user";
        }else{
            $role = "professor";
        }

        $token = JWTAuth::fromUser($user);
        $user_name = $request->name;

        return $this->assignRole($request->email, $role, $user_name, $token);
    }

    public function authenticate(Request $request)
    {
        $credentials = $request->only('email', 'password');

        try {
            // verify the credentials and create a token for the user
            if (!$token = JWTAuth::attempt($credentials)) {
                return response()->json(['error' => 'Your credentials are incorrect!'], 401);
            }
        } catch (JWTException $e) {
            // something went wrong
            return response()->json(['error' => 'could_not_create_token'], 500);
        }

        // if no errors are encountered we can return a JWT with user role

        $user = User::all()->where('email', $credentials['email'])->first();
        $user_name = $user->name;
        $user_picture = $user->photo;
        $user_role = $user->role->first()->name;

        if($request->mobile != null){
            $user->mobile_token = Hash::make($request->mobile); 
            $user->save();
        }

        return response()->json(compact(['token', 'user_name', 'user_role', 'user_picture']));
        //return response()->json(compact('token'));
    }

    public function authenticateMobileApp(Request $request)
    {
        // grab credentials from the request
        $credentials = $request->only('tag', 'mobile_token');

        $token = Hash::make($credentials['mobile_token']);

       $user=User::where('tagId','=', $credentials['tag'])->first();

        if(Hash::check($credentials['mobile_token'], $user->mobile_token)) {

            //Try to add token
            if (!$token=JWTAuth::fromUser($user)) {
                return response()->json(['error' => 'invalid_credentials'], 401);
            }

        }
        return response()->json(compact('token'));

    }

    public function createRole(Request $request)
    {
        $role = new Role();
        $role->name = $request->input('name');
        $role->save();

        return response()->json("created");
    }

    public function createPermission(Request $request){

        $viewUsers = new Permission();
        $viewUsers->name = $request->input('name');
        $viewUsers->save();

        return response()->json("created");

    }

    public function assignRole($email, $name, $user_name, $token)
    {
        $user = User::where('email', '=', $email)->first();

        $role = Role::where('name', '=', $name)->first();
        //$user->attachRole($request->input('role'));
        $user->roles()->attach($role->id);

        return response()->json(compact(['token', 'user_name', 'user_role']));
    }

    public function attachPermission(Request $request){
        $role = Role::where('name', '=', $request->input('role'))->first();
        $permission = Permission::where('name', '=', $request->input('name'))->first();
        $role->attachPermission($permission);

        return response()->json("created");
    }
}
