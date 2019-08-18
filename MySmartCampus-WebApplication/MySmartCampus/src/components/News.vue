<template>
   <div class="container-fluid navbar-offset-top">
      <div class="row">
        <section class="col-12 section_header">
          <h1>News</h1>
        </section>
        <carousel-3d @after-slide-change="onAfterSlideChange" :perspective="0" :style="{ height: article_height}" :space="400" :display="3" class="news_slider">
          <slide v-for="(news_one, index) in news" :index="index" :key="index" v-bind:class="{ current: index==news.length-1 && !was_changed}" :style="{ height: article_height}">
            <article class="news" ref="articles_height">
              <img src="../assets/news2.jpg" alt="MySmartCampus-news">
              <h2>{{news_one.title}}</h2>
              <span class="date">{{news_one.news_date}}</span>
              <p>{{news_one.content}}</p>
            </article>
          </slide>
        </carousel-3d>
        </section>
        <div class="col-12 navigation_buttons">
          <ul>
              <li><router-link class="nav-button" :to="'/news/add/' + subject_id">Add News</router-link></li>
          </ul>
       </div>
      </div>
    </div>
</template>

<script>

import axios from 'axios';
import { Carousel3d, Slide } from 'vue-carousel-3d';


export default {
  name: 'News',
  components: {
    Carousel3d,
    Slide
  },
  data () {
    return {
      news: [],
      article_height: 'auto',
      subject_id: this.$route.params.id,
      slides: 5,
      height: 0,
      was_changed: false
    }
  },
  methods: {
      getNews(){
        axios.get(this.$store.getters.getUrl + '/news/')
        .then(function (response) {
            this.news = response.data.data;
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
        this.article_height = this.height + 'px!important';
        console.log(this.article_height);
      },
      onAfterSlideChange(index) {
        this.was_changed = true;
      },
      deleteNews(id){
         axios.delete(this.$store.getters.getUrl + '/news/'+ id)
         .then(function (response) {
            alert(response.data['deleted']);
            this.getGroups();
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
  background: rgba(0, 0, 0, 0.1);
}

.carousel-3d-slider{
  .current{
    background: rgba(0, 0, 0, 0.3);
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
