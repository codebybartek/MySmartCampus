<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class AlterTableActivity extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::table('activities', function (Blueprint $table) {
            $table->integer('course_id')
                ->unsigned();
            $table->integer('recurrence_id')
                ->unsigned()
                ->nullable();
            $table->foreign('course_id')
                ->references('course_id')
                ->on('courses')
                ->onDelete('cascade');
            $table->foreign('recurrence_id')
                ->references('recurrence_id')
                ->on('recurrences')
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
