<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class AlterTableCourse extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::table('courses', function (Blueprint $table) {
            $table->integer('group_id')
                ->unsigned();
            $table->integer('subject_id')
                ->unsigned();
            $table->foreign('group_id')
                ->references('group_id')
                ->on('groups')
                ->onDelete('cascade');
            $table->foreign('subject_id')
                ->references('subject_id')
                ->on('subjects')
                ->onDelete('cascade');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        //
    }
}
