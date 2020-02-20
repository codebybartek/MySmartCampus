<template>
  <div class="popup_request">
        <aside class="col-12 about_service offset-50">
            <h3>Send request for enrolling to group</h3>
            <p>You aren't attached to any group. Please send request to professor, and then he will enroll you to group.</p>
        </aside>
        <form class="form-add align-center custom-padding">      
          <select v-model="enroll_request.group_id" id="select">
            <option value="0" disabled selected>Chose prefered group...</option>
             <option v-for="group in groups" :value="group.id">
              {{group.name}}
            </option>    
          </select>
          <select v-model="enroll_request.professor_id" id="select">
            <option value="0" disabled selected>Chose professor...</option>
             <option v-for="professor in professors" :value="professor.professor_id">
              {{professor.professor_name}}
            </option>    
          </select>
          <button class="u-full-width add_button" v-on:click="addRequest" >Add request</button>
        </form>
        <div class="popup" v-bind:class="{show_pop: errors.length > 0 || success}">
          <span class="error" v-for="error in errors">{{error}}</span>            
        </div>
  </div>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'SendRequestToProfessor',
      data() {
        return {
        enroll_request: {
          group_id: 0,
          student_id: 0,
          professor_id: 0
        },
        groups: [],
        professors: [],
        errors: [],
        }
      },
      methods: {
        getUser(){
          axios.get(this.$store.getters.getUrl + '/user')
          .then(function (response) {
            this.enroll_request.student_id = response.data.data.id;
          }.bind(this))
          .catch((error)=>{
            this.$router.push('/login'); 
          });
        },
        getProfessors(){
          axios.get(this.$store.getters.getUrl + '/professors')
          .then(function (response) {
              this.professors = response.data.data;
              console.log(this.professors)
          }.bind(this))
          .catch((error)=>{
            this.$router.push('/login'); 
          });
        },
        getGroups(){
          axios.get(this.$store.getters.getUrl + '/groups')
          .then(function (response) {
              this.groups = response.data.data;
              console.log(this.professors)
          }.bind(this))
          .catch((error)=>{
            this.$router.push('/login'); 
          });
        },
        addRequest(){
          event.preventDefault();
          this.errors = [];
          console.log(this.enroll_request);
          if(this.enroll_request.group_id===0){
            this.errors.push("Group is required");
          }
          if(this.enroll_request.professor_id===0){
            this.errors.push("Professor is required");
          }
          if(this.errors.length == 0){
            axios.post(this.$store.getters.getUrl + '/requestprofessor', this.enroll_request).then((response) =>{
              console.log(response.data['created']);
              let alert = {content: response.data['created'], alertClass: "success"};
              this.$emit('setAlert', alert);
              this.$router.go('/')
            })
            .catch(function(error){
              popup('You were logged out');
              this.$router.push('/login'); 
            }.bind(this));
          } 
        },
      },
      created: function(){
        if(window.$cookies.get('token') || window.$cookies.get('user_role') != "student"){
          this.getGroups();
          this.getProfessors();
          this.getUser();
        }else{
          this.$router.push('/login'); 
        }
      }
  }
</script>

<style scoped lang="scss">

@import '@/styles/variables.scss';

.popup_request{
  color: $basic_color;
  position: fixed;
  top: 70px;
  left: 10vw;
  right: 10vw;
  bottom: 70px;
  z-index: 9999;
  background-color: #1d1d1d;
  padding: 50px;

  h2{
    margin-top: 20px;
    color: $light_grey;
  }

  .how_to_start{
    h2{
      color: $basic_green;
    }
    span{
      color: $basic_grey;
    }
  }
}



</style>
