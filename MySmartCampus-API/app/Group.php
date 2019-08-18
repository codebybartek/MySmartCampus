<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Group extends Model
{
    protected $fillable = ['name', 'hash'];

    protected $primaryKey = "id";

    protected $hidden = ['pivot'];

    public function users() {
        return $this->belongsToMany('App\User', 'group_user');
    }
}
