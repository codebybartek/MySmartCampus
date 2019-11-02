<template>
   <div class="container navbar-offset-top">
      <Material class="single_hidden" v-if="showSingleContent" v-bind:class="{show_single_content: showSingleContent}" :material_props="material_props" v-on:closeSingleContent="closeSingleContent()"/>
       <AddMaterial class="single_hidden" v-if="showAddFormContent" v-bind:class="{show_add_form_content: showAddFormContent}" v-on:closeSingleContent="closeSingleContent()" />
      <div class="row">
        <section class="col-12 section_header">
          <h1>Materials</h1>
        </section>
        <carousel-3d @after-slide-change="onAfterSlideChange" :style="{ height: article_height}" :controls-visible="true" :controls-next-html="'&#10093;'" :controls-prev-html="'&#10092;'">
          <slide v-for="(material, index) in materials" :index="index" :style="{ height: article_height}" v-bind:class="{ current: index==materials.length-1 && !was_changed}" :key="index">
            <article class="material" ref="articles_height" :style="{ height: article_height}">
              <h2>{{material.course_title}}</h2>
              <ul class="course_activities">
                <h4>Activities:</h4>
                <li v-for="(activity, index) in material.activities">
                  <router-link :to="'./activity/' + activity.activity_hash ">
                    {{activity.activity_title}} - <span>{{activity.activity_date.split(" ")[0]}}</span>
                  </router-link>
                  <ul class="materials">
                    <li v-for="(material, index) in activity.materials">
                      <span class="material_title"><i class="fa fa-file" aria-hidden="true"></i> {{material.title}}</span>
                      <a class="attachment" v-bind:href="material.attachment_url"><i class="fa fa-paperclip" aria-hidden="true"></i></a>
                      <span class="material_content">{{material.content}}</span>
                    </li>
                  </ul>
                </li>   
              </ul>
              <a class="button_more" v-on:click="showSingle(material)" >More <i class="fa fa-angle-double-right" aria-hidden="true"></i></a>

              <a v-if="is_professor" class="button_edit"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> Edit</a>
            </article>
          </slide>
        </carousel-3d>
        </section>
        <div class="col-12 navigation_buttons">
          <ul>
            <li v-if="is_professor" class="button_normal_white"><a class="nav-button" v-on:click="addFormContent()">Add Material <i class="fa fa-plus" aria-hidden="true"></i></a></li>
          </ul>
       </div>
      </div>
    </div>
</template>

<script>

import axios from 'axios';
import { Carousel3d, Slide } from 'vue-carousel-3d';
import Material from './Material.vue';
import AddMaterial from './add_forms/AddMaterial.vue';

export default {
  name: 'Materials',
  components: {
    Carousel3d,
    Slide,
    Material,
    AddMaterial
  },
  data () {
    return {
      materials: [],
      material_id: this.$route.params.id,
      activity_id: 0,
      article_height: 'auto',
      material_props: null,
      showSingleContent: false,
      showAddFormContent: false,
      height: 0,
      was_changed: false,
      is_professor: false
    }
  },
  methods: {
    getmaterials(){
      if(window.$cookies.get('user_role') == "professor"){
        this.is_professor = true;
      }
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
    showSingle(material_props){
      this.material_props = material_props;
      this.showSingleContent = true;
    },
    closeSingleContent(){
      this.material_props = null;
      this.showSingleContent = false;
      this.showAddFormContent = false;
    },
    addFormContent(){
      this.showAddFormContent = true;
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

    .material_title{
      color: $basic_blue;
    }
    .material_content{
      display: block;
      margin-bottom: 5px;
      padding-bottom: 5px;
      border-bottom: 1px solid $basic_color;
    }

  /*  a{
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
    }*/

    .attachment{
      margin-left: 50px;
      color: $basic_green;
      font-size: 1.2em;
    }
  }

}
</style>
