<template>
  <div class="single_content">
    <div class="center_content">
      <span class="close_btn" v-on:click="closeBtn()">&times;</span>
      <h1>Add new material: </h1>
      <form class="form-add align-center custom-padding">      
        <input v-model="material.title" type="text" placeholder="Material title" id="title"/>
        <textarea v-model="material.content" placeholder="Material content" id="content"></textarea>
        <select v-model="material.activity_id" class="courseId">  
          <option value="0" disabled selected>Chose activity...</option>  
          <option v-for="activity in activities" :value="activity.id">
            {{activity.title}}
          </option>                                                   
        </select>
        <input type="file" v-on:change="onImageChange">
        <button class="u-full-width add_button" v-on:click="addMaterial()" >Add material</button>
      </form>
      <div class="popup" v-bind:class="{show_pop: errors.length > 0 || success}">
        <span class="error" v-for="error in errors">{{error}}</span>
        <span class="success">{{success}}</span>             
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'AddMaterial',
    data() {
      return {
        material: {
          title: '',
          content: '',
          activity_id: 0,
          attachment: null
        },
        activities: [],
        errors: [],
        success: ''
      }
    },
    methods: {
      onImageChange(e){
          this.material.attachment = e.target.files[0];
      },
      addMaterial(){
        event.preventDefault();
        let currentObj = this;

        const config = {
          headers: { 'content-type': 'multipart/form-data' }
        }

        let material = new FormData();
        material.append('title', this.material.title);
        material.append('body', this.material.content);
        material.append('activity_id', this.material.activity_id);
        material.append('attachment', this.material.attachment);
        this.errors = [];
        if(this.errors.length == 0){
          axios.post(this.$store.getters.getUrl + '/materials', material, config).then((response) =>{
            this.success = response.data['created'];
          })
          .catch(function(error){
            popup('You were logged out');
            this.$router.push('/login'); 
          }.bind(this));
        } 
      },
      getActivities(){
      axios.get(this.$store.getters.getUrl + '/activities')
        .then(function (response) {
            this.activities = response.data.data;
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
      if(window.$cookies.get('token')){
       this.getActivities();
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
  position: fixed;
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
      margin: 10px 0;
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
