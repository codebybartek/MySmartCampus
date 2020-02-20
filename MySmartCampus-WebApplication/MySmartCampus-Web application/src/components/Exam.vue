<template>
  <div class="single_content">
    <div class="center_content">
      <span class="close_btn" v-on:click="closeBtn()">&times;</span>
      <h1>{{exam_name}}</h1>
      <a v-on:click="deleteExam(grade.exam_id)" class="button_delete"><i class="fa fa-trash-o" aria-hidden="true"></i> Delete</a>
      <h4>Grades:</h4>
      <table class="table table-hover">
        <tr class="header_table">
          <th>Student id</th>
          <th>Student name</th>
          <th>Grade</th>
          <th>Date</th>
          <th>Delete</th>
        </tr>
        <tr v-for="(grade) in grades">
          <td>{{grade.user.id}}</td>
          <td>{{grade.user.name}}</td>
          <td class="grade" v-bind:class="{green: grade.grade > 2}" >{{grade.grade}}</td>
          <td>{{grade.grade_date}}</td>
          <td><span class="delete" v-on:click=deleteGrade(grade.id)><i class="fa fa-trash-o" aria-hidden="true"></i> </span></td>
        </tr>
      </table>
    </div>
    <section class="col-12 addGrade">
      <h2>Add new grade:</h2>
      <select v-model="grade.student_id" class="studentId">    
        <option value="0" selected>Chose student</option>   
        <option v-for="student in students" :value="student.id">
          {{student.id}} - {{student.name}}
        </option>                                                   
      </select>
      <select v-model="grade.grade" class="gradeId">   
        <option value="0" selected>Chode grade</option>    
        <option value="2">2</option>   
        <option value="3">3</option> 
        <option value="4">4</option> 
        <option value="5">5</option> 
        </option>                                                   
      </select>
      <li class="button_normal_white"><a class="nav-button" v-on:click="addExam()">Add Exam <i class="fa fa-plus" aria-hidden="true"></i></a></li>
      <div class="popup" v-bind:class="{show_pop: errors.length > 0 || success}">
        <span class="error" v-for="error in errors">{{error}}</span>            
      </div>
    </section>
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
        exam_hash: this.$route.params.hash,
        grades: null,
        exam_name: '',
        group_id: 0,
        errors: [],
        students: [],
        grade: {
          student_id: 0,
          grade: 0,
          exam_id: 0,
          grade_date: ''
        }
      }
    },
    methods: {
      getExam(){
      console.log("Sdfsd");
        axios.get(this.$store.getters.getUrl + '/exams/' + this.exam_hash)
        .then(function (response) {
            console.log(response.data.data[0]);
            this.grades = response.data.data[0].grades;
            this.exam_name = response.data.data[0].exam_name;
            this.grade.exam_id = response.data.data[0].exam_id;
            this.group_id = response.data.data[0].group_id;
            this.getStudentsAll();
        }.bind(this))
        .catch((error)=>{
          //this.$router.push('/login'); 
        });
        //console.log(this.group_id);
      },
      closeBtn(){
        this.$router.push('/exams'); 
      },
      deleteExam(id){
         axios.delete(this.$store.getters.getUrl + '/exams/'+ id)
         .then(function (response) {
            alert(response.data['deleted']);
            this.getexams();
        }.bind(this))
        .catch((error)=>{
          //this.$router.push('/login'); 
        });
      },
      deleteGrade(id){
         axios.delete(this.$store.getters.getUrl + '/grades/'+ id)
         .then(function (response) {
            alert(response.data['deleted']);
            this.getExam();
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/login'); 
        });
      },
      getStudentsAll(){
          axios.get(this.$store.getters.getUrl + '/group/' + this.group_id)
          .then(function (response) {
          console.log(response.data);
              this.students = response.data.data[0].students;

          }.bind(this))
          .catch((error)=>{
            //this.$router.push('login'); 
          });
      },
      addExam(){
        event.preventDefault();
        this.errors = [];

        //Generate date
        let genDate = new Date();
        genDate = (genDate.getFullYear() + "-" + (genDate.getMonth()+1) + "-" + genDate.getDate() + " " + genDate.getHours() + ":" + genDate.getMinutes() + ":" + genDate.getSeconds());

        this.grade.grade_date = genDate;

        console.log(this.grade);

        if(this.grade.student_id===0){
          this.errors.push("Student is required");
        }
        if(this.grade.grade===0){
          this.errors.push("Grade is required");
        }
        if(this.errors.length == 0){
          axios.post(this.$store.getters.getUrl + '/grades', this.grade).then((response) =>{
            let alert = {content: response.data['created'], alertClass: "success"};
            this.$emit('setAlert', alert);
            this.getExam();
          })
          .catch(function(error){
            popup('You were logged out');
            this.$router.push('/login'); 
          }.bind(this));
        } 
      },
    },
    created: function(){
      if(window.$cookies.get('token')){
        if(parseInt(localStorage.getItem('Expiration')) + 600000 < new Date().getTime() ){
          let alert = {content: 'Your token get expiered. Please login again.)', alertClass: "warning"};
          this.$emit('setAlert', alert);
          this.$router.push('/login'); 
        }
        this.getExam();
        
      }else{
        this.$router.push('/login'); 
      }
      if(window.$cookies.get('user_role') != "professor"){
          this.$router.push('/'); 
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
