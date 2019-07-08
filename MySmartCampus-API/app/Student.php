<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Notifications\Notifiable;
use Tymon\JWTAuth\Contracts\JWTSubject;
use Illuminate\Foundation\Auth\User as Authenticatable;

class Student extends Authenticatable  implements JWTSubject
{
    use Notifiable;

    protected $fillable = [
        'name', 'email', 'tagId', 'group_id', 'password', 'photo'
    ];
    protected $primaryKey = "student_id";

    protected $hidden = [
        'password', 'remember_token',
    ];


    public function getJWTIdentifier()
    {
        return $this->getKey();
    }

    /**
     * Return a key value array, containing any custom claims to be added to the JWT.
     *
     * @return array
     */
    public function getJWTCustomClaims()
    {
        return [
            'student_id'              => $this->student_id,
            'name'            => $this->name,
            'email'           => $this->email,
            'password'        => $this->password

        ];
    }
}
