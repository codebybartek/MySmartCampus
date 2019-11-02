<?php

namespace App;

use Illuminate\Auth\Authenticatable;
use Illuminate\Contracts\Auth\Authenticatable as auth;
use Illuminate\Auth\Passwords\CanResetPassword;
use Illuminate\Notifications\Notifiable;
use Zizaco\Entrust\Traits\EntrustUserTrait;
use Illuminate\Database\Eloquent\Model as Eloquent;
use Tymon\JWTAuth\Contracts\JWTSubject;


class User extends Eloquent implements JWTSubject, auth
{
    use Authenticatable, CanResetPassword, EntrustUserTrait;

    use Notifiable;

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'name', 'email', 'password', 'tagId', 'hash', 'photo'
    ];

    protected $primaryKey = 'id';



    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    protected $hidden = [
        'password', 'remember_token', 'pivot', 'role'
    ];

    /**
     * The attributes that should be cast to native types.
     *
     * @var array
     */
    protected $casts = [
        'email_verified_at' => 'datetime',
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
            'id'              => $this->id,
            'name'            => $this->name,
            'email'           => $this->email,
            'password'        => $this->password

        ];
    }

    public function subjects() {
        return $this->belongsToMany('App\Subject', 'course_user');
    }

    public function courses() {
        return $this->belongsToMany('App\Course', 'course_user');
    }

    public function groups() {
        return $this->belongsToMany('App\Group', 'group_user');
    }

    public function role() {
        return $this->belongsToMany('App\Role', 'role_user');
    }

}
