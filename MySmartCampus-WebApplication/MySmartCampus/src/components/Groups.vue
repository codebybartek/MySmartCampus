<template>
   <div class="container navbar-offset-top">
      <Group class="single_hidden" v-bind:class="{show_single_content: showSingleContent}" :group="group" v-on:closeSingleContent="closeSingleContent()"/>
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
                  <li v-for="(student, index) in group.students" v-if="index < 2 ">
                    <router-link :to="'./students/' + student.id ">
                      <span>{{index + 1}}. </span> {{student.name}}
                    </router-link>
                  </li>
              </ul>
              <a v-on:click="deletegroup(group.id)" class="button_delete"><i class="fa fa-trash-o" aria-hidden="true"></i> Delete</a>
              <a class="button_more" v-on:click="showSingle(group)" >More <i class="fa fa-angle-double-right" aria-hidden="true"></i></a>
              <a class="button_edit"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> Edit</a>
            </article>
          </slide>
        </carousel-3d>
        </section>
        <div class="col-12 navigation_buttons">
          <ul>
              <li><router-link class="nav-button" :to="'/groups/add/' + subject_id">Add group</router-link></li>
          </ul>
       </div>
      </div>
    </div>
</template>

<script>

import axios from 'axios';
import { Carousel3d, Slide } from 'vue-carousel-3d';
import Group from './Group.vue';


export default {
  name: 'Groups',
  components: {
    Carousel3d,
    Slide,
    Group
  },
  data () {
    return {
      groups: [],
      group: null,
      subject_id: this.$route.params.id,
      activity_id: 0,
      article_height: 'auto',
      showSingleContent: false,
      height: 0,
      was_changed: false
    }
  },
  methods: {
    getGroups(){
      axios.get(this.$store.getters.getUrl + '/groups/')
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
    showSingle(group){
      this.group = group;
      this.showSingleContent = true;
    },
    closeSingleContent(){
      this.group = null;
      this.showSingleContent = false;
    }
  },
  created: function () {
    if(!localStorage.getItem('token')){
      //this.$router.push('/login'); 
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
  background: $light_grey;
  opacity: 0.2!important;

  .group{
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

    .more{
      color: $basic_green;

      &:hover{

        i{
          margin-right: -10px!important;
          margin-left: 10px!important;
        }
      }
    }

    a{
      color: $basic_color;
      margin-bottom: 5px;
      display: inline-block;
      transition: $standart_transition;
      font-size: 0.9em;

      span{
        color: $basic_green;
        transition: $standart_transition;
        padding: 5px;
      }

      &:hover{
        color: $basic_blue;

        span{
          color: $basic_blue;
        }
      }
    }
  }

}
.current{
  background: $light_grey;
  opacity: 0.8!important;
}

</style>
