<template>
  <div class="single_content">
    <div class="center_content">
      <span class="close_btn" v-on:click="closeBtn()">&times;</span>
      <div class="row">
        <div class="col-xs-6 col-sm-6">
          <h1><span>Title: </span>{{activity.title}} <span>Date: </span>{{activity.activityDate.split(' ')[0]}}</h1>
        </div>
        <div class="col-xs-6 col-sm-6">
          <h2><span>Duration: </span>{{activity.duration}} <span>Classroom: </span>{{activity.class_room}}</h2>
        </div>
      </div>
        <h4>Materials:</h4>
        <table class="table table-hover">
          <tr class="header_table">
            <th>Id</th>
            <th>Title</th>
            <th>Content</th>
            <th>Date</th>
            <th>Attachment</th>
            <th v-if="is_professor">Delete</th>
            <th>More</th>
          </tr>
          <tr v-if="activity.materials.length > 0" v-for="(material, index) in activity.materials">
            <td>{{index + 1}}</td>
            <td>{{material.title}}</td>
            <td>{{material.content}}</td>
            <td>{{material.material_date}}</td>
            <td>{{material.attachment_url}}</td>
            <td v-if="is_professor"><span class="delete" v-on:click=deleteMaterial(material.id)><i class="fa fa-trash-o" aria-hidden="true"></i> </span></td>
            <td><a :href="material.attachment_url">view <i class="fa fa-angle-double-right" aria-hidden="true"></i></a></td>
          </tr>
        </table>
        <h4 v-if="is_professor">Exams:</h4>
        <table v-if="is_professor" class="table table-hover">
          <tr class="header_table">
            <th>Id</th>
            <th>Title</th>
            <th>Questions</th>
            <th>Date</th>
            <th>Duration</th>
            <th>Delete</th>
            <th>More</th>
          </tr>
          <tr v-if="activity.exams.length > 0" v-for="(exam, index) in activity.exams">
            <td>{{index + 1}}</td>
            <td>{{exam.title}}</td>
            <td>{{exam.questions}}</td>
            <td>{{exam.exam_date}}</td>
            <td>{{exam.duration}}</td>
            <td><span class="delete" v-on:click=deleteExam(exam.id)><i class="fa fa-trash-o" aria-hidden="true"></i> </span></td>
            <td><router-link :to="'./exam/' + exam.hash ">view <i class="fa fa-angle-double-right" aria-hidden="true"></i></router-link></td>
          </tr>
        </table>
        <h4 v-if="is_professor">Present students:</h4>
        <table v-if="is_professor" class="table table-hover">
          <tr class="header_table">
            <th>Id</th>
            <th>Name</th>
            <th>Tag Id</th>
            <th>Date</th>
            <th>Delete</th>
          </tr>
          <tr v-if="activity.students.length > 0" v-for="(student, index) in activity.students">
            <td>{{index + 1}}</td>
            <td>{{student.name}}</td>
            <td>{{student.tagId}}</td>
            <td>{{student.datePresence}}</td>
            <td><span class="delete" v-on:click=deleteStudentAttendance(student.id)><i class="fa fa-trash-o" aria-hidden="true"></i> </span></td>
        
          </tr>
        </table>
        <div class="addStudent">
            <h4>Add student to attendance list</h4>
             <select v-model="attendanceStudent.user_id" class="studentId">    
                 <option value="0" selected>Chose student</option>   
                  <option v-for="student in studentsAll" :value="student.id">
                      {{student.id}} - {{student.name}}
                    </option>                                                   
              </select>
              <li class="button_normal_white"><a class="nav-button" v-on:click="addStudentToList()">Add Student <i class="fa fa-plus" aria-hidden="true"></i></a></li>
            </div>
      </div>
  </div>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'Activity',
    props: ['activity_props'],
    data() {
      return {
        activity_hash: this.$route.params.hash,
        activity: null, 
        is_professor: false,
        studentsAll: [],
        attendanceStudent: {
          user_id: 0,
          activity_id: 0,
          data_presence: ''
        },
        student_activity: {
          student_id: 0,
          activity_id: 0
        },
      }
    },
    methods: {
      getActivity(){

        let activity_url;
        if(window.$cookies.get('user_role') == "professor"){
            activity_url = '/activities/';
        }else{
            activity_url = '/activity/';
        }
        axios.get(this.$store.getters.getUrl + activity_url + this.activity_hash)
        .then(function (response) {
            this.activity = response.data.data[0];
            console.log(this.activity);

            this.getStudents();
        }.bind(this))
        .catch((error)=>{
          //this.$router.push('/login'); 
        });
      },
      closeBtn(){
        if(!this.activity_hash){
          this.$emit('closeSingleContent');
        }else{
          this.$router.push('/activities'); 
        }
      },
      addStudentToList(){
        let dateTime = new Date();
        dateTime = (dateTime.getFullYear() + "-" + (dateTime.getMonth()+1) + "-" + dateTime.getDate() + " " + dateTime.getHours() + ":" + dateTime.getMinutes() + ":" + dateTime.getSeconds());

        this.attendanceStudent.activity_id = this.activity.id;
        this.attendanceStudent.data_presence = "2020-02-02 19:34:44";

        console.log(this.attendanceStudent);

        if(this.attendanceStudent.user_id===0){
          alert("USer is required");
        }

        axios.post(this.$store.getters.getUrl + '/attandancelist', this.attendanceStudent).then((response) =>{
            let alert = {content: response.data['created'], alertClass: "success"};
            this.$emit('setAlert', alert);
            this.getActivity();
          })
          .catch(function(error){
            popup('You were logged out');
            this.$router.push('/login'); 
          }.bind(this));
      },
      getStudents(){
        axios.get(this.$store.getters.getUrl + '/group/'+ this.activity.course.group_id)
        .then(function (response) {
            this.studentsAll = response.data.data[0].students;
            console.log(this.studentsAll);
        }.bind(this))
        .catch((error)=>{
          //this.$router.push('/login'); 
        });
      },
      deleteMaterial(id){
        axios.delete(this.$store.getters.getUrl + '/materials/'+ id)
         .then(function (response) {
            alert(response.data['deleted']);
            this.getActivity();
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/login'); 
        });
      },
      deleteExam(id){
        axios.delete(this.$store.getters.getUrl + '/exams/'+ id)
         .then(function (response) {
            alert(response.data['deleted']);
            this.getActivity();
        }.bind(this))
        .catch((error)=>{
          //this.$router.push('/login'); 
        });
      },
      deleteStudentAttendance(id){
        this.student_activity.student_id = id;
        this.student_activity.activity_id = this.activity.id;
        console.log(this.student_activity);
        axios.delete(this.$store.getters.getUrl + '/attandancelist', {params: {id: this.student_activity}})
        .then(function (response) {
            alert(response.data['deleted']);
            this.getActivity();

            for(let i = 0; i < this.activity.students.length; i++){
              if(this.activity.students[i].id === id){
                this.activity.students.splice(i, 1);
              }
            }

        }.bind(this))
        .catch((error)=>{
          console.log(error);
          //this.$router.push('/login'); 
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
        if(window.$cookies.get('user_role') == "professor"){
          this.is_professor = true;
        }
        if(this.activity_hash){
            this.getActivity();
        }else{
          this.activity = this.activity_props;
        }
      }
    }
  }
</script>

<style scoped lang="scss">

@import '@/styles/variables.scss';

h1, h2{

  text-align: center;
  font-size: 2em;  

  span{
    font-size: 0.5em;
    margin-top: 10px;
    display: block;
    width: 100%;
    color: $basic_blue;
  }
}
h4{
  color: $basic_blue;
  margin-top: 20px;
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
