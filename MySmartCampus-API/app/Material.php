<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Material extends Model
{
    protected $fillable = ['title', 'content', 'material_date', 'attachment_url', 'hash'];

    protected $primaryKey = 'id';

    protected $hidden = ['pivot'];
}
