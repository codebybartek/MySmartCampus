
<template>
  <div class="single_content">
    <div class="center_content">
      <span class="close_btn" v-on:click="closeBtn()">&times;</span>
      <h1>Course: {{course.name}}</h1>
      <h4>Presence:</h4>
      <table class="table table-hover">
        <tr class="header_table">
          <th>Id</th>
          <th>Name</th>
          <th>Email</th>
          <th>Tag id</th>
          <th>Presence[%]</th>
        </tr>
        <tr v-for="(attendance, index) in attendances">
          <td>{{index + 1}}</td>
          <td>{{attendance.student.name}}</td>
          <td>{{attendance.student.email}}</td>
          <td>{{attendance.student.tagId}}</td>
          <td class="presence" v-bind:class="{green: attendance.total.toFixed(2) > 0.5}">{{attendance.total.toFixed(2)}} %</td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'Frequence',
    props: ['course_props'],
    data() {
      return {
        course: null,
        attendances: []
      }
    },
    methods: {
      getAttendance(){
        axios.get(this.$store.getters.getUrl + '/attandancelist/' + this.course.id)
        .then(function (response) {
            this.attendances = response.data.data;
            console.log(this.group);
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/login'); 
        });
      },
      closeBtn(){
        this.$emit('closeSingleContent');
      }
    },
    created: function(){
      if(window.$cookies.get('token')){
        if(parseInt(localStorage.getItem('Expiration')) + 600000 < new Date().getTime() ){
          let alert = {content: 'Your token get expiered. Please login again.)', alertClass: "warning"};
          this.$emit('setAlert', alert);
          this.$router.push('/login'); 
        }
        this.course = this.course_props;
        this.getAttendance();
      }
    }
  }
</script>

<style scoped lang="scss">

@import '@/styles/variables.scss';

h1{

  text-align: left;
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
  min-height: 100vh;
  position: absolute;
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

  .presence{
    color: $basic_red;
  }

  .green{
    color: $basic_green;
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
