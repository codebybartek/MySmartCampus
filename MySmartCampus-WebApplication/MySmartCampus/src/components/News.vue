<template>
   <div class="container-fluid navbar-offset-top">
      <NewsSingle class="single_hidden" v-if="showSingleContent" v-bind:class="{show_single_content: showSingleContent}" :news_props="news_props" v-on:closeSingleContent="closeSingleContent()"/>
      <AddNews class="single_hidden" v-if="showAddFormContent" v-bind:class="{show_add_form_content: showAddFormContent}" v-on:closeSingleContent="closeSingleContent()" @setAlert="setAlert" />
      <div class="row">
        <section class="col-12 section_header">
          <h1>News</h1>
        </section>
        <carousel-3d @after-slide-change="onAfterSlideChange" :perspective="0" :style="{ height: article_height}" :space="400" :display="3" class="news_slider">
          <slide v-for="(news_one, index) in news" :index="index" :key="index" v-bind:class="{ current: index==news.length-1 && !was_changed}" :style="{ height: article_height}">
            <article class="news" ref="articles_height">
              <img v-bind:src="news_one.img_src" alt="MySmartCampus-news">
              <h2>{{news_one.title}}</h2>
              <span class="date">{{news_one.news_date}}</span>
              <p>{{news_one.content}}</p>
              <a v-if="is_professor" v-on:click="deleteNews(news_one.id)" class="button_delete"><i class="fa fa-trash-o" aria-hidden="true"></i> Delete</a>
              <a class="button_more" v-on:click="showSingle(news_one)" >More <i class="fa fa-angle-double-right" aria-hidden="true"></i></a>
              <a v-if="is_professor" class="button_edit"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> Edit</a>
            </article>
          </slide>
        </carousel-3d>
        </section>
        <div class="col-12 navigation_buttons">
           <ul>
              <li v-if="is_professor" class="button_normal_white"><a class="nav-button" v-on:click="addFormContent()">Add News <i class="fa fa-plus" aria-hidden="true"></i></a></li>
          </ul>
       </div>
      </div>
    </div>
</template>

<script>

import axios from 'axios';
import { Carousel3d, Slide } from 'vue-carousel-3d';
import NewsSingle from './NewsSingle.vue';
import AddNews from './add_forms/AddNews.vue';

export default {
  name: 'News',
  components: {
    Carousel3d,
    Slide,
    NewsSingle,
    AddNews
  },
  data () {
    return {
      news: [],
      news_props: null,
      article_height: 'auto',
      subject_id: this.$route.params.id,
      slides: 5,
      showAddFormContent: false,
      showSingleContent: false,
      height: 0,
      was_changed: false,
      is_professor: false
    }
  },
  methods: {
    getNews(){
      if(window.$cookies.get('user_role') == "professor"){
        this.is_professor = true;
      }
      axios.get(this.$store.getters.getUrl + '/news')
        .then(function (response) {
          this.news = response.data.data;
          setTimeout(this.matchHeight, 100);
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
      this.article_height = this.height + 'px!important';
      console.log(this.article_height);
    },
    onAfterSlideChange(index) {
      this.was_changed = true;
    },
    deleteNews(id){
       axios.delete(this.$store.getters.getUrl + '/news/'+ id)
       .then(function (response) {
          let alert = {content: response.data['deleted'], alertClass: "danger"};
          this.$emit('setAlert', alert);
          this.getNews();
      }.bind(this))
      .catch((error)=>{
        this.$router.push('/login'); 
      });
    },
    showSingle(news_props){
      this.news_props = news_props;
      this.showSingleContent = true;
    },
    closeSingleContent(){
      this.news_props = null;
      this.showSingleContent = false;
      this.showAddFormContent = false;
    },
    addFormContent(){
      this.showAddFormContent = true;
    },
    setAlert(alert){
      this.$emit('setAlert', alert);
      this.getNews();
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
    this.getNews();
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
  margin: 15px 0;
  font-size: 2.2em;
  color: $basic_color;
}

.carousel-3d-slide{
  width: 100%!important;
  opacity: 0.4!important;
  color: $basic_color;
  height: auto!important;
  background: rgba(0, 0, 0, 0.4);
}

.carousel-3d-slider{
  .current{
    background: rgba(0, 0, 0, 0.6);
    opacity: 1!important;
  }

  .news{
    padding: 15px;

    .date{
      padding: 5px;
      background: rgba(0, 0, 0, 0.5);
      right: 0;
      top: 0;
      position: absolute;
    }
  }

}


</style>
