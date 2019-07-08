<?php

namespace App\Http\Controllers\studentController;

use App\Student;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Tymon\JWTAuth\Facades\JWTAuth;

class StudentsController extends Controller
{
    public function login(Request $request)
    {
        // grab credentials from the request
        $credentials = $request->only('email', 'password');

        $studentId = Student::all()->where('email', $credentials['email'])->pluck('student_id');
        $studentName = Student::all()->where('email', $credentials['email'])->pluck('name');

        try {
            // attempt to verify the credentials and create a token for the user
            if (! $token =  auth('api_stud')->attempt($credentials)) {
                return response()->json(['error' => 'invalid_credentials'], 401);
            }
        } catch (JWTException $e) {
            // something went wrong whilst attempting to encode the token
            return response()->json(['error' => 'could_not_create_token'], 500);
        }



        // all good so return the token
        return response()->json(compact(['token', 'studentId', 'studentName']));
    }

    public function loginMobileApp(Request $request)
    {
        // grab credentials from the request
        $credentials = $request->only('tagId', 'mobile_token');

        $token = bcrypt($credentials['mobile_token']);

        $user=Professor::where('tagId','=', $credentials['tagId'])->first();

        if(Hash::check($credentials['mobile_token'], $user->mobile_token)) {

            //Try to add token
            if (!$userToken=JWTAuth::fromUser($user)) {
                return response()->json(['error' => 'invalid_credentials'], 401);
            }

        }
        return response()->json(compact('userToken'));

    }
}
