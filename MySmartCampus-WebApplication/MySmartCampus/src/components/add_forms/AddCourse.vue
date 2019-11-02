<template>
  <div class="single_content">
    <div class="center_content">
      <span class="close_btn" v-on:click="closeBtn()">&times;</span>
      <h1>Add new course: </h1>
      <form class="form-add align-center custom-padding">      
        <input v-model="course.name" type="text" placeholder="Course name" id="name"/>
        <select v-model="course.group_id" id="select">
          <option value="0" disabled selected>Chose group...</option>
           <option v-for="group in groups" :value="group.id">
            {{group.name}}
          </option>    
        </select>
        <select v-model="course.subject_id" id="select">
          <option value="0" disabled selected>Chose subject...</option>
           <option v-for="subject in subjects" :value="subject.id">
            {{subject.name}}
          </option>    
        </select>
        <button class="u-full-width add_button" v-on:click="addCourse" >Add course</button>
      </form>
      <div class="popup" v-bind:class="{show_pop: errors.length > 0 || success}">
        <span class="error" v-for="error in errors">{{error}}</span>            
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'AddCourse',
    data() {
      return {
        course: {
          name: '',
          group_id: 0,
          subject_id: 0
        },
        groups: [],
        subjects: [],
        errors: [],
      }
    },
    methods: {
      getGroups(){
        axios.get(this.$store.getters.getUrl + '/groups')
        .then(function (response) {
            this.groups = response.data.data;
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/login'); 
        });
      },
      getSubjects(){
        axios.get(this.$store.getters.getUrl + '/subjects')
        .then(function (response) {
            this.subjects = response.data.data;
            console.log(this.subjects)
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/login'); 
        });
      },
      addCourse(){
        event.preventDefault();
        this.errors = [];
        if(!this.course.name){
          this.errors.push("Course name is required");
        }
        if(this.course.group_id===0){
          this.errors.push("Group id is required");
        }
        if(this.course.subject_id===0){
          this.errors.push("Group id is required");
        }
        if(this.errors.length == 0){
          axios.post(this.$store.getters.getUrl + '/courses', this.course).then((response) =>{
            let alert = {content: response.data['created'], alertClass: "success"};
            this.$emit('setAlert', alert);
          })
          .catch(function(error){
            popup('You were logged out');
            this.$router.push('/login'); 
          }.bind(this));
        } 
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
       this.getGroups();
       this.getSubjects();
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
  position: fixed;
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
  z-index: 999;
}


</style>
