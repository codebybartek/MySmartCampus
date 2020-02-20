<template>
  <div class="single_content">
    <div class="center_content">
      <h1>{{exam_name}}</h1>
      <h4>Grades:</h4>
      <table class="table table-hover">
        <tr class="header_table">
          <th>Id</th>
          <th>Exam</th>
          <th>Grade</th>
          <th>Grade date</th>
        </tr>
        <tr v-for="(grade, index) in grades">
          <td>{{index+1}}</td>
          <td>{{grade.exam_title}}</td>
          <td class="grade" v-bind:class="{green: grade.grade > 2}" >{{grade.grade}}</td>
          <td>{{grade.grade_date}}</td>
        </tr>
      </table>
    </div>
    </section>
    
  </div>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'Exam',
    props: ['exam_props'],
    data() {
      return {
        grades: [],

      }
    },
    methods: {
      getExamsStudent(){
      axios.get(this.$store.getters.getUrl + '/students')
      .then(function (response) {
          this.grades = response.data.data;
      }.bind(this))
      .catch((error)=>{
        this.$router.push('/login'); 
      });
      },
      
    },
    created: function(){
      if(window.$cookies.get('token')){
        if(parseInt(localStorage.getItem('Expiration')) + 600000 < new Date().getTime() ){
          let alert = {content: 'Your token get expiered. Please login again.)', alertClass: "warning"};
          this.$emit('setAlert', alert);
          this.$router.push('/login'); 
        }
        this.getExamsStudent();
        
      }else{
        this.$router.push('/login'); 
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
  width: 100%;
  top: 0;
  left: 0;
  background-color: $dark_grey;
  z-index: 5;
  position: relative;

  .center_content{
    padding: 50px;
    margin: 0 auto;
    margin-top: 100px;
    max-width: 700px;
    border: 1px solid $basic_color;
  }

  .grade{
    color: $basic_red;
  }

  .green{
    color: $basic_green;
  }

  h4{
  margin-top: 100px;
  }

  h1{
    float: left;
  }

  .button_delete{
    position: relative;
    float: right;
    margin-top: 10px!important;
    display: inline-block;
  }

  .addGrade{
    position: relative;
    margin: 0 auto;
    padding-top: 30px;
    max-width: 650px;

    h2{
      display: block;
    }

    select{
      max-width: 280px;
      display: inline-block;
      float: left;
      margin: 10px;
    }

    .button_normal_white{
      list-style-type: none;
      margin-top: 30px;
      display: inline-block;
    }

    .error{
      color: $basic_red;
      margin: 10px;
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
}


</style>
