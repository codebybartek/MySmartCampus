<template>
  <div class="single_content">
    <div class="center_content">
      <span class="close_btn" v-on:click="closeBtn()">&times;</span>
      <h1>Add new news: </h1>
      <form class="form-add align-center custom-padding">      
        <input v-model="news.title" type="text" placeholder="News title" id="title"/>
        <textarea v-model="news.content" placeholder="News content" id="content"></textarea>
        <select v-model="news.course_id" class="courseId">  
          <option value="0" disabled selected>Chose course...</option>  
          <option v-for="course in courses" :value="course.id">
            {{course.name}}
          </option>                                                   
        </select>
        <picture-input 
          ref="pictureInput" 
          @change="onImageChange" 
          width="400" 
          height="400" 
          crop= true
          margin="16" 
          accept="image/jpeg,image/png" 
          size="10" 
          buttonClass="btn"
          :customStrings="{
            upload: '<h1>Uploading</h1>',
            drag: 'Add a picture'
          }">
        </picture-input>
        <button class="u-full-width add_button" v-on:click="addNews()" >Add news</button>
      </form>
      <div class="popup" v-bind:class="{show_pop: errors.length > 0 || success}">
        <span class="error" v-for="error in errors">{{error}}</span> 
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';

  import PictureInput from 'vue-picture-input'


  export default {
    name: 'AddNews',
    data() {
      return {
        news: {
          title: '',
          content: '',
          course_id: 0,
          image: null
        },
        courses: [],
        errors: []
      }
    },
    components: {
      PictureInput
    },
    methods: {
      onImageChange(){
          this.news.image = this.$refs.pictureInput.file;
          console.log(this.news.image);
      },
      addNews(){
        event.preventDefault();
        let currentObj = this;

        const config = {
          headers: { 'content-type': 'multipart/form-data' }
        }

        let news = new FormData();
        news.append('title', this.news.title);
        news.append('body', this.news.content);
        news.append('course_id', this.news.course_id);
        news.append('image', this.news.image);
        this.errors = [];
        console.log(news);
        if(this.errors.length == 0){
          axios.post(this.$store.getters.getUrl + '/news', news, config).then((response) =>{
            let alert = {content: response.data['created'], alertClass: "success"};
            this.$emit('setAlert', alert);
          })
          .catch(function(error){
            popup('You were logged out');
            this.$router.push('/login'); 
          }.bind(this));
        } 
      },
      getCourses(){
        axios.get(this.$store.getters.getUrl + '/courses')
        .then(function (response) {
            this.courses = response.data.data;
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/login'); 
        });
      },
      closeBtn(){
        if(!this.group_hash){
          this.$emit('closeSingleContent');
        }else{
          this.$router.push('/'); 
        }
      }

    },
    created: function(){
      if(window.$cookies.get('token') || window.$cookies.get('user_role') != "professor"){
       this.getCourses();
      }else{
        this.$router.push('/login'); 
      }
    }
  }
</script>

<style scoped lang="scss">

@import '@/styles/variables.scss';
h1{
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
  padding-left: 30px;
  padding-right: 30px;
  position: absolute;
  min-height: 100vh;
  width: 100%;
  margin: 0 auto;
  right: 0;
  top: 0;
  left: 0;
  background-color: $dark_grey;
  z-index: 5;
  display: flex;
  align-items: center;
  justify-content: center;

  .center_content{
    padding: 50px;
  }

  .form-add{
    text-align: center;

    input, textarea, select{
      width: 100%;
      max-width: 600px;
      text-align: center;
    }

    .add_button{
      display: block;
      margin: 0 auto;
      margin-top: 20px;
    }
  }
}

.close_btn{
  position: absolute;
  right: 30px;
  top: 105px;
  font-size: 4em;
  line-height: 0;
  cursor: pointer;
  z-index: 999;
}


</style>
