<template>
  <div class="popup_request">
        <aside class="col-12 about_service offset-50">
            <h3>Add Student to group</h3>
            <p>You can enroll requested student to group. Mention it, student position is the same as requested group position.[1-1, 2-2 ...]. But you could chode another group.</p>
        </aside>
        <form class="form-add align-center custom-padding">      
          <select v-model="enroll_request.user_id" id="select">
            <option value="0" disabled selected>Chose student...</option>
             <option v-for="student in students" :value="student.id">
              {{student.name}}
            </option>    
          </select>
          <select v-model="enroll_request.group_id" id="select">
            <option value="0" disabled selected>Chose prefered group...</option>
             <option v-for="group in groups" :value="group.id">
              {{group.name}}
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
          user_id: 0
        },
        groups: [],
        students: [],
        errors: [],
        }
      },
      methods: {
        loadData(){
          let request_add_to_group = this.$store.getters.getRequestAddToGroup;
          this.groups = request_add_to_group.groups;
          this.students = request_add_to_group.students;
        },
        addRequest(){
          event.preventDefault();
          this.errors = [];
          console.log(this.enroll_request);
          if(this.enroll_request.group_id===0){
            this.errors.push("Group is required");
          }
          if(this.enroll_request.user_id===0){
            this.errors.push("Student is required");
          }
          if(this.errors.length == 0){
            axios.post(this.$store.getters.getUrl + '/students', this.enroll_request).then((response) =>{
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
          this.loadData();
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
  bottom: 30px;
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
