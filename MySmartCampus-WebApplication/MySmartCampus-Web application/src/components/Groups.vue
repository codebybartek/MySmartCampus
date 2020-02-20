<template>
   <div class="container navbar-offset-top">

      <Group class="single_hidden" v-if="showSingleContent" v-bind:class="{show_single_content: showSingleContent}" :group_props="group_props" v-on:closeSingleContent="closeSingleContent()"/>

      <AddGroup class="single_hidden" v-if="showAddFormContent" v-bind:class="{show_add_form_content: showAddFormContent}" v-on:closeSingleContent="closeSingleContent()" @setAlert="setAlert" />

      <div class="row">
        <section class="col-12 section_header">
          <h1>Groups</h1>
        </section>
        <carousel-3d @after-slide-change="onAfterSlideChange" :style="{ height: article_height}" :controls-visible="true" :controls-next-html="'&#10093;'" :controls-prev-html="'&#10092;'">
          <slide v-for="(group, index) in groups" :index="index" :style="{ height: article_height}" v-bind:class="{ current: index==groups.length-1 && !was_changed}" :key="index">
            <article  class="group" ref="articles_height" :style="{ height: article_height}">
              <h2>{{group.name}}</h2>
              <ul class="group_students">
                  <h4>Students:</h4>
                  <li v-for="(student, index) in group.students" v-if="index < 5 ">
                    <router-link :to="'./student/' + student.id ">
                      <span>{{index + 1}}. </span> {{student.name}}
                    </router-link>
                  </li>
              </ul>
              <a v-if="is_professor" v-on:click="deleteGroup(group.id)" class="button_delete"><i class="fa fa-trash-o" aria-hidden="true"></i> Delete</a>
              <a class="button_more" v-on:click="showSingle(group)" >More <i class="fa fa-angle-double-right" aria-hidden="true"></i></a>
            </article>
          </slide>
        </carousel-3d>
        </section>
        <div class="col-12 navigation_buttons">
          <ul>
              <li v-if="is_professor" class="button_normal_white"><a class="nav-button" v-on:click="addFormContent()">Add Group <i class="fa fa-plus" aria-hidden="true"></i></a></li>
          </ul>
       </div>
      </div>
    </div>
</template>

<script>

import axios from 'axios';
import { Carousel3d, Slide } from 'vue-carousel-3d';
import Group from './Group.vue';
import AddGroup from './add_forms/AddGroup.vue';


export default {
  name: 'Groups',
  components: {
    Carousel3d,
    Slide,
    Group,
    AddGroup
  },
  data () {
    return {
      groups: [],
      group_props: null,
      subject_id: this.$route.params.id,
      activity_id: 0,
      article_height: 'auto',
      showSingleContent: false,
      showAddFormContent: false,
      showEditFormContent: false,
      height: 0,
      was_changed: false,
      is_professor: false
    }
  },
  methods: {
    getGroups(){
      if(window.$cookies.get('user_role') == "professor"){
        this.is_professor = true;
      }
      axios.get(this.$store.getters.getUrl + '/groups')
      .then(function (response) {
          this.groups = response.data.data;
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
    deleteGroup(id){
       axios.delete(this.$store.getters.getUrl + '/groups/'+ id)
       .then(function (response) {
          alert(response.data['deleted']);
          this.getGroups();
      }.bind(this))
      .catch((error)=>{
        this.$router.push('/login'); 
      });
    },
    showSingle(group_props){
      this.group_props = group_props;
      this.showSingleContent = true;
    },
    closeSingleContent(){
      this.group_props = null;
      this.showSingleContent = false;
      this.showAddFormContent = false;
      this.showEditFormContent = false;
    },
    editFormContent(group_props){
      this.group_props = group_props;
      this.showEditFormContent = true;
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
    this.getGroups();
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

  .group{
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

</style>
