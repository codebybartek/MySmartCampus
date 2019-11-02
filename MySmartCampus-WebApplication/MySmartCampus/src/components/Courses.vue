<template>
   <div class="container navbar-offset-top">
      <Course class="single_hidden" v-if="showSingleContent" v-bind:class="{show_single_content: showSingleContent}" :course_props="course_props" v-on:closeSingleContent="closeSingleContent()"/>
      <AddCourse class="single_hidden" v-if="showAddFormContent" v-bind:class="{show_add_form_content: showAddFormContent}" v-on:closeSingleContent="closeSingleContent()" @setAlert="setAlert" />
      <div class="row">
        <section class="col-12 section_header">
          <h1>Courses</h1>
        </section>
        <carousel-3d @after-slide-change="onAfterSlideChange" :style="{ height: article_height}" :controls-visible="true" :controls-next-html="'&#10093;'" :controls-prev-html="'&#10092;'">
          <slide v-for="(course, index) in courses" :index="index" :style="{ height: article_height}" v-bind:class="{ current: index==courses.length-1 && !was_changed}" :key="index">
            <article  class="course" ref="articles_height" :style="{ height: article_height}">
              <h2>{{course.name}}</h2>
              <ul class="course_activities">
                  <h4>Activities:</h4>
                  <li v-for="(activity, index) in course.activities" v-if="index < 2 ">
                    <router-link :to="'./activity/' + activity.hash ">
                      {{activity.title}} - 
                      <span>{{activity.activityDate}}</span>
                    </router-link>
                  </li>
              </ul>
              <ul class="course_news">
                  <h4>News:</h4>
                  <li v-for="(news, index) in course.news" v-if="index < 5 ">
                    <router-link :to="'./news/' + news.hash ">{{news.title}}</router-link>
                  </li>
              </ul>
              <a v-if="is_professor" v-on:click="deleteCourse(course.id)" class="button_delete"><i class="fa fa-trash-o" aria-hidden="true"></i> Delete</a>

              <a class="button_more" v-on:click="showSingle(course)" >More <i class="fa fa-angle-double-right" aria-hidden="true"></i></a>

              <a v-if="is_professor" class="button_edit"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> Edit</a>
            </article>
          </slide>
        </carousel-3d>
        </section>
        <div class="col-12 navigation_buttons">
          <ul>
              <li v-if="is_professor" class="button_normal_white"><a class="nav-button" v-on:click="addFormContent()">Add Course <i class="fa fa-plus" aria-hidden="true"></i></a></li>
          </ul>
       </div>
      </div>
    </div>
</template>

<script>

import axios from 'axios';
import { Carousel3d, Slide } from 'vue-carousel-3d';
import Course from './Course.vue';
import AddCourse from './add_forms/AddCourse.vue';

export default {
  name: 'Courses',
  components: {
    Carousel3d,
    Slide,
    Course,
    AddCourse
  },
  data () {
    return {
      courses: [],
      course_props: null,
      subject_id: this.$route.params.id,
      activity_id: 0,
      article_height: 'auto',
      showSingleContent: false,
      showAddFormContent: false,
      height: 0,
      was_changed: false,
      is_professor: false
    }
  },
  methods: {
      getCourses(){
        if(window.$cookies.get('user_role') == "professor"){
          this.is_professor = true;
        }
        axios.get(this.$store.getters.getUrl + '/courses')
        .then(function (response) {
            this.courses = response.data.data;
            setTimeout(this.matchHeight, 50);
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/login'); 
        });
      },
      matchHeight() {
        for(let i = 0; i < this.$refs.articles_height.length; i++){
          if(this.$refs.articles_height[i].clientHeight > this.height){
            this.height = this.$refs.articles_height[i].clientHeight;
          } 
        }
        this.height = this.height + 30;
        this.article_height = this.height + 'px';
        console.log(this.article_height);
      },
      onAfterSlideChange(index) {
        this.was_changed = true;
      },
      deleteCourse(id){
         axios.delete(this.$store.getters.getUrl + '/courses/'+ id)
         .then(function (response) {
            let alert = {content: response.data['deleted'], alertClass: "danger"};
            this.$emit('setAlert', alert);
            this.getCourses();
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/login'); 
        });
      },
      showSingle(course_props){
        this.course_props = course_props;
        this.showSingleContent = true;
      },
      closeSingleContent(){
        this.course_props = null;
        this.showSingleContent = false;
        this.showAddFormContent = false;
      },
      addFormContent(){
        this.showAddFormContent = true;
      },
      setAlert(alert){
        this.$emit('setAlert', alert);
        this.getCourses();
      }
  },
  created: function () {
    if(!window.$cookies.get('token')){
      this.$router.push('/login'); 
    }
    if(parseInt(localStorage.getItem('Expiration')) + 600000 < new Date().getTime() ){
      let alert = {content: 'Your token get expiered. Please login again.)', alertClass: "warning"};
      this.$emit('setAlert', alert);
      this.$router.push('/login'); 
    }
    this.getCourses();
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

@import '@/styles/variables.scss';

h1{
  margin: 50px 0;
  font-size: 3em;
}
h2{
  margin-bottom: 20px;
  font-size: 2.2em;
}

</style>
