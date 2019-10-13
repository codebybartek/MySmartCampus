<template>
 <div class="single_content">
    <div class="center_content">
      <span class="close_btn" v-on:click="closeBtn()">&times;</span>
      <div class="news content">
        <img src="../assets/news2.jpg" alt="MySmartCampus-news">
        <h1>{{news.title}}</h1>
        <span>{{news.news_date}}</span>
        <p>{{news.content}}</p>
      </div>
    </div>
  </div>
</template>

<script>

import axios from 'axios';

export default {
  name: 'NewsSingle',
  props: ['news_props'],
  data () {
    return {
      news_hash: this.$route.params.hash,
      news: null
    }
  },
  methods: {
    getNews(){
      axios.get(this.$store.getters.getUrl + '/news/'+ this.news_hash)
       .then(function (response) {
          this.news = response.data.data[0];
          console.log(this.news);
      }.bind(this))
      .catch((error)=>{
        //this.$router.push('/login'); 
      });
    },
    deleteNews(id){
       axios.delete(this.$store.getters.getUrl + '/news/'+ this.news_hash)
       .then(function (response) {
          alert(response.data['deleted']);
          this.getNews();
      }.bind(this))
      .catch((error)=>{
        this.$router.push('/login'); 
      });
    },
    closeBtn(){
      if(!this.news_hash){
        this.$emit('closeSingleContent');
      }else{
        this.$router.push('/'); 
      }
    }
  },
  created: function () {
    if(window.$cookies.get('token')){
      if(parseInt(localStorage.getItem('Expiration')) + 600000 < new Date().getTime() ){
        let alert = {content: 'Your token get expiered. Please login again.)', alertClass: "warning"};
        this.$emit('setAlert', alert);
        this.$router.push('/login'); 
      }
      if(this.news_hash){
          console.log(this.news_hash);
          this.getNews();
      }else{
        this.news = this.news_props;
      }
    }else{
      this.$router.push('/login'); 
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

  @import '@/styles/variables.scss';

h1{

  text-align: center;
  margin-top: 15px;
  font-size: 2.5em;  
  span{
    float: right;
    font-size: 0.7em;
    line-height: 2em;
    vertical-align: bottom;
  }
}

.single_content{
  padding-top: 75px;
  position: absolute;
  min-height: 100vh;
  width: 100%;
  top: 0;
  left: 0;
  background-color: $dark_grey;
  z-index: 5;
  display: flex;
  align-items: center;
  justify-content: center;

  .center_content{
    padding: 50px;
    margin-top: 30px;
    border: 1px solid $basic_color;
  }
}

.close_btn{
  position: absolute;
  right: 30px;
  top: 105px;
  font-size: 4em;
  line-height: 0;
  cursor: pointer;
}
</style>
