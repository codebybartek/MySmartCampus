<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Exam extends Model
{
    protected $fillable = ['title', 'hash'];

    public $primaryKey = 'id';

    protected $hidden = ['pivot'];
}
