<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Material extends Model
{
    protected $fillable = ['title', 'content', 'material_date', 'attachment_url', 'course_id'];
    public $primaryKey = 'material_id';
}
