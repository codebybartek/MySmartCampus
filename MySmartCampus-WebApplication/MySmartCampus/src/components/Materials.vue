<template>
   <div class="container navbar-offset-top">
      <div class="row">
        <section class="col-12 section_header">
          <h1>Materials</h1>
        </section>
        <carousel-3d @after-slide-change="onAfterSlideChange" :style="{ height: article_height}" :controls-visible="true" :controls-next-html="'&#10093;'" :controls-prev-html="'&#10092;'">
          <slide v-for="(material, index) in materials" :index="index" :style="{ height: article_height}" v-bind:class="{ current: index==materials.length-1 && !was_changed}" :key="index">
            <article  class="material" ref="articles_height" :style="{ height: article_height}">
              <h2>{{material.course_title}}</h2>
              <h4>Course: {{material.course_title}}</h4>
              <ul class="course_activities">
                  <h4>Activities:</h4>
                  <li v-for="(activity, index) in material.activities" v-if="index < 2 ">
                    <router-link :to="'./activity/' + activity.id ">
                      {{activity.activity_title}} - <span>{{activity.activity_date.split(" ")[0]}}</span>
                    </router-link>
                    <ul class="materials">
                      <li v-for="(material, index) in activity.materials" v-if="index < 2 ">
                        <router-link :to="'./material/' + material.id "><i class="fa fa-file" aria-hidden="true"></i> {{material.title}}</router-link>
                      </li>
                    </ul>
                  </li>
                  
              </ul>
              <a v-on:click="deletematerial(material.id)" class="button_delete"><i class="fa fa-trash-o" aria-hidden="true"></i> Delete</a>
              <a class="button_edit"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> Edit</a>
            </article>
          </slide>
        </carousel-3d>
        </section>
        <div class="col-12 navigation_buttons">
          <ul>
              <li><router-link class="nav-button" :to="'/materials/add/' + material_id">Add material</router-link></li>
          </ul>
       </div>
      </div>
    </div>
</template>

<script>

import axios from 'axios';
import { Carousel3d, Slide } from 'vue-carousel-3d';


export default {
  name: 'Materials',
  components: {
    Carousel3d,
    Slide
  },
  data () {
    return {
      materials: [],
      material_id: this.$route.params.id,
      activity_id: 0,
      article_height: 'auto',
      height: 0,
      was_changed: false
    }
  },
  methods: {
      getmaterials(){
        axios.get(this.$store.getters.getUrl + '/materials')
        .then(function (response) {
            this.materials = response.data.data;
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
      deletematerial(id){
         axios.delete(this.$store.getters.getUrl + '/materials/'+ id)
         .then(function (response) {
            alert(response.data['deleted']);
            this.getmaterials();
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/login'); 
        });
      },
  },
  created: function () {
    if(!localStorage.getItem('token')){
      //this.$router.push('/login'); 
    }
    this.getmaterials();
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

  .material{
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
