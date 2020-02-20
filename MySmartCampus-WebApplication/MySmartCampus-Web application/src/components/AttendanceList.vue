<template>
   <div class="container navbar-offset-top">
        <div class="row">
          <section class="col-12 header">
              <h1>Check Attandance list:</h1>
              <h2>Chose activity:</h2>
              <select v-model="activity" class="courseId" @change="getStudentsAll()">    
                <option value="0" selected>Chose activity</option>   
                <option v-for="activityEach in activities" :value="activityEach">
                  {{activityEach.activityDate}} - {{activityEach.title}}
                </option>                                                   
              </select>
            </section>
          </section>
        </div>
        <section v-if="isChecked()" class="row top-50">
          <h3>Check   list:</h3>
            <table class="table table-hover">
                <tr class="header_table">
                    <th>Id</th>
                    <th>Name</th>
                    <th>Tag id</th>
                    <th>Presence</th>
                </tr>
                <tr v-for="(student, index) in studentsGroup">
                    <td>{{index + 1}}</td>
                    <td>{{student.name}}</td>
                    <td>{{student.tagId}}</td>
                    <td><button class="presence" v-on:click=addToList(student.id)><span v-show="isOnList(student.id)">&#x274C;</span></button></td>
                </tr>
            </table>
            <div class="col-12 navigation_buttons">
               <ul>
                  <li class="button_normal_white"><a class="nav-button" v-on:click="addList()">Add Attendance list <i class="fa fa-plus" aria-hidden="true"></i></a></li>
              </ul>
            </div>
          </section>
    </div>
</template>

<script>

import axios from 'axios';

export default {
  name: 'AttendanceList',
  data () {
    return {
      id: this.$route.params.id,
      activities: [],
      activity: 0,
      isCheckedBool: 0,
      students: [],
      attandanceList: [],
      studentsGroup: []
    }
  },
  methods: {
    getActivities(){
      axios.get(this.$store.getters.getUrl + '/activities')
        .then(function (response) {
            let activities = response.data.data;
            this.students = response.data.data[0].students
            this.isToday(activities);
        }.bind(this))
        .catch((error)=>{
          //this.$router.push('/login'); 
        });
    },
    isToday(activitiesAll){
      var isToday = false;
      for(var i = 0; i < activitiesAll.length; i++){
        let dateActivity = new Date(activitiesAll[i].activityDate.split(" ")[0]);
        let currentDate = new Date();
        if(dateActivity.getFullYear() === currentDate.getFullYear() && dateActivity.getMonth() === currentDate.getMonth() && dateActivity.getDate() === currentDate.getDate()){
          this.activities.push(activitiesAll[i]);
          isToday = true;
        }
      }
      return isToday;
    },
    getStudentsAll(){
      axios.get(this.$store.getters.getUrl + '/group/' + this.activity.course.group_id)
      .then(function (response) {
          this.studentsGroup = response.data.data[0].students;
          console.log(response.data.data);

      }.bind(this))
      .catch((error)=>{
        this.$router.push('login'); 
      });
    },
    addToList(id){
      if(this.isOnList(id)){
        this.attandanceList = this.attandanceList.filter(function(el) { return el.user_id != id; }); 
      }else{
        let genDate = new Date();
        genDate = (genDate.getFullYear() + "-" + (genDate.getMonth()+1) + "-" + genDate.getDate() + " " + genDate.getHours() + ":" + genDate.getMinutes() + ":" + genDate.getSeconds());
        this.attandanceList.push({user_id: id, activity_id: this.activity.id, data_presence: genDate});
        console.log(this.attandanceList);
      }
    },
    isOnList(id){
      for(var i = 0; i < this.attandanceList.length; i++){
        if(this.attandanceList[i].user_id === id){
          return true;
        }
      }
      return false;
    },
    addList(){
      this.activity.checked = true;

      for(var i = 0; i < this.attandanceList.length; i++){
        axios.post(this.$store.getters.getUrl + '/attandancelist', this.attandanceList[i]).then((response) =>{
          let alert = {content: response.data['created'], alertClass: "success"};
          this.$emit('setAlert', alert); 
        })
        .catch(function(error){
          let alert = {content: "Something went wrong!", alertClass: "danger"};
          this.$emit('setAlert', alert); 
        }.bind(this));
      }
    },
    isChecked(){
      axios.get(this.$store.getters.getUrl + '/activities/'+ this.activity.hash)
      .then(function (response) {
          this.isCheckedBool = response.data.data[0].checked;
          if(this.isCheckedBool === 1){
            let alert = {content: 'This activity was checked before!', alertClass: "warning"};
            this.$emit('setAlert', alert);
            this.$router.push('/attendance'); 
          }
      }.bind(this))
      .catch((error)=>{
        this.$router.push('/attendance'); 
      });
      if(this.isCheckedBool === 1){
        return false;
      }else{
        return true;
      }
    }
  },
  created: function () {
    if(!window.$cookies.get('token')){
      this.$router.push('/login'); 
    }
    if(parseInt(localStorage.getItem('Expiration')) + 600000 < new Date().getTime() ){
      let alert = {content: 'Your token get expiered. Please login again.)', alertClass: "warning"};
      this.$emit('setAlert', alert);
      this.$router.push('/login'); 
    }
    this.getActivities();
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

@import '@/styles/variables.scss';

h1{
  margin: 50px 0;
}
h2, h3{
  color: $basic_blue;
  font-size: 1.5em;
  text-align: center;
  display: block;
  width: 100%;
}
h3{
  margin-top: 50px;
}

select{
  background: transparent;
  color: $basic_color;
  max-width: 400px;
}

.presence{
  background: transparent;
  border: 1px solid $basic_color;
  color: $basic_green;
  width: 35px;
  height: 35px;
  font-size: 16px;
  text-align: center;
  vertical-align: middle;
}

</style>
