<template>
   <div class="container navbar-offset-top">
      <Subject class="single_hidden" v-if="showSingleContent" v-bind:class="{show_single_content: showSingleContent}" :subject_props="subject_props" v-on:closeSingleContent="closeSingleContent()" @setAlert="setAlert"/>
      <AddSubject class="single_hidden" v-if="showAddFormContent" v-bind:class="{show_add_form_content: showAddFormContent}" v-on:closeSingleContent="closeSingleContent()" @setAlert="setAlert" />
      <div class="row">
        <section class="col-12 section_header">
          <h1>Subjects</h1>
        </section>
        <carousel-3d @after-slide-change="onAfterSlideChange" :style="{ height: article_height}" :controls-visible="true" :controls-next-html="'&#10093;'" :controls-prev-html="'&#10092;'">
          <slide v-for="(subject, index) in subjects" :index="index" :style="{ height: article_height}" v-bind:class="{ current: index==subjects.length-1 && !was_changed}" :key="index">
            <article  class="subject" ref="articles_height" :style="{ height: article_height}">
              <h2>{{subject.name}}</h2>
              <ul class="subject_courses">
                  <h4>Courses:</h4>
                  <li v-for="(course, index) in subject.courses" v-if="index < 2 ">
                    <router-link :to="'/course/' + course.hash ">
                      {{course.name}}
                    </router-link>
                  </li>
                  <router-link v-on:click="setActivities(subject.courses)" v-if="subject.courses.length > 2" class="more" :to="'/courses'">More Courses <i class="fa fa-angle-double-right" aria-hidden="true"></i></router-link>
              </ul>
              <a v-if="is_professor" v-on:click="deleteSubject(subject.id)" class="button_delete"><i class="fa fa-trash-o" aria-hidden="true"></i> Delete</a>

              <a class="button_more" v-on:click="showSingle(subject)" >More <i class="fa fa-angle-double-right" aria-hidden="true"></i></a>

              <a v-if="is_professor" class="button_edit"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> Edit</a>
            </article>
          </slide>
        </carousel-3d>
        </section>
        <div class="col-12 navigation_buttons">
          <ul>
              <li v-if="is_professor" class="button_normal_white"><a class="nav-button" v-on:click="addFormContent()">Add Subject <i class="fa fa-plus" aria-hidden="true"></i></a></li>
          </ul>
       </div>
      </div>
    </div>
</template>

<script>

import axios from 'axios';
import { Carousel3d, Slide } from 'vue-carousel-3d';
import Subject from './Subject.vue';
import AddSubject from './add_forms/AddSubject.vue';


export default {
  name: 'Subjects',
  components: {
    Carousel3d,
    Slide,
    Subject,
    AddSubject
  },
  data () {
    return {
      subjects: [],
      subject_props: null,
      showSingleContent: false,
      subject_id: this.$route.params.id,
      activity_id: 0,
      article_height: 'auto',
      showAddFormContent: false,
      height: 0,
      was_changed: false,
      is_professor: false
    }
  },
  methods: {
    getSubjects(){
      if(window.$cookies.get('user_role') == "professor"){
        this.is_professor = true;
      }
      axios.get(this.$store.getters.getUrl + '/subjects')
      .then(function (response) {
          this.subjects = response.data.data;
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
    deleteSubject(id){
       axios.delete(this.$store.getters.getUrl + '/subjects/'+ id)
       .then(function (response) {
          let alert = {content: response.data['deleted'], alertClass: "danger"};
          this.$emit('setAlert', alert);
          this.getSubjects();
      }.bind(this))
      .catch((error)=>{
        this.$router.push('/login'); 
      });
    },
    showSingle(subject_props){
      this.subject_props = subject_props;
      this.showSingleContent = true;
    },
    closeSingleContent(){
      this.subject_props = null;
      this.showSingleContent = false;
      this.showAddFormContent = false;
    },
    addFormContent(){
      this.showAddFormContent = true;
    },
    setAlert(alert){
      this.$emit('setAlert', alert);
      this.getSubjects();
    }
  },
  created: function () {
    if(!window.$cookies.get('token')){
      this.$router.push('/login'); 
    }
    if(parseInt(localStorage.getItem('Expiration')) + 600000 < new Date().getTime() ){
      alert('Your token get expiered! Please login again :)');
      this.$router.push('/login'); 
    }
    this.getSubjects();
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

.carousel-3d-slide {
  .subject{
    border: 1px solid $light_grey;
    padding: 15px;
    text-align: center;

    h2{
      color: $dark_grey;
      text-align: center;
    }

    h4{
      color: $basic_blue;
    }
  }

}
.current{
  background: $light_grey;
  opacity: 0.8!important;
}

</style>
