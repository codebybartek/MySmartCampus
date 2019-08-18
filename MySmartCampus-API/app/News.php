<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class News extends Model
{
    protected $fillable = ['title', 'content', 'news_date', 'img_src', 'hash'];
    public $primaryKey = 'id';

    protected $hidden = ['pivot'];
}
