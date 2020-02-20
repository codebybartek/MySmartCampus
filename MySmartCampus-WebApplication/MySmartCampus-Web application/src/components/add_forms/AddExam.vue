<template>
  <div class="single_content">
    <div class="center_content">
      <span class="close_btn" v-on:click="closeBtn()">&times;</span>
      <h1>Add new exam: </h1>
      <form class="form-add align-center custom-padding">      
        <input v-model="exam.title" type="text" placeholder="Exam title" id="name"/>
        <select v-model="exam.activity_id" id="select">
          <option value="0" disabled selected>Chose activity...</option>
           <option v-for="activity in activities" :value="activity.id">
            {{activity.activityDate}}
          </option>    
        </select>
        <button class="u-full-width add_button" v-on:click="addExam" >Add Exam</button>
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
    name: 'AddExam',
    data() {
      return {
        exam: {
          title: '',
          activity_id: 0,
        },
        activities: [],
        errors: [],
      }
    },
    methods: {
      getActivities(){
        axios.get(this.$store.getters.getUrl + '/activities')
        .then(function (response) {
            this.activities = response.data.data;
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/login'); 
        });
      },
      addExam(){
        event.preventDefault();
        this.errors = [];
        if(!this.exam.title){
          this.errors.push("Exam title is required");
        }
        if(this.exam.activity_id===0){
          this.errors.push("Activity id is required");
        }
        if(this.errors.length == 0){
          axios.post(this.$store.getters.getUrl + '/exams', this.exam).then((response) =>{
            let alert = {content: response.data['created'], alertClass: "success"};
            this.$emit('setAlert', alert);
          })
          .catch(function(error){
            this.$router.push('/login'); 
          }.bind(this));
        } 
      },
      closeBtn(){
        if(!this.exam_hash){
          this.$emit('closeSingleContent');
        }else{
          this.$router.push('/'); 
        }
      }
    },
    created: function(){
      if(window.$cookies.get('token') || window.$cookies.get('user_role') != "professor"){
        if(parseInt(localStorage.getItem('Expiration')) + 600000 < new Date().getTime() ){
          alert('Your token get expiered! Please login again :)');
          this.$router.push('/login'); 
        }
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
