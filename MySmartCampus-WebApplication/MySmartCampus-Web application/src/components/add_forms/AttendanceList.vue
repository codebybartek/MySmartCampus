<template>
   <div class="container">
        <div class="row">
          <section class="col-12 header">
            <h1>Select activity:</h1>
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
          <h3>Check   list: </h3>
            <table class="table table-hover">
                <tr class="header_table">
                    <th>Id</th>
                    <th>Name</th>
                    <th>Tag id</th>
                    <th>Presence</th>
                </tr>
                <tr v-for="student in studentsGroup">
                    <td>{{student.student_id}}</td>
                    <td>{{student.name}}</td>
                    <td>{{student.tagId}}</td>
                    <td><button class="presence" v-on:click=addToList(student.student_id)><span v-show="isOnList(student.student_id)">&#x274C;</span></button></td>
                </tr>
            </table>
            <div class="addList col-12">
              <button class="u-full-width button-primary" type="submit" v-on:click="addList()">Add attandance list</button>
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
              this.activities= response.data.data;
              this.students = response.data.data[0].students
          }.bind(this))
          .catch((error)=>{
            //this.$router.push('/login'); 
          });
      },
      /*isToday(activitiesAll){
        var isToday = false;
        for(var i = 0; i < activitiesAll.length; i++){
          let dateActivity = new Date(activitiesAll[i].activityDate);
          let currentDate = new Date();
          if(dateActivity.getFullYear() === currentDate.getFullYear() && dateActivity.getMonth() === currentDate.getMonth() && dateActivity.getDate() === currentDate.getDate()){
            this.activities.push(activitiesAll[i]);
            isToday = true;
          }
        }
        return isToday;
      },
      getStudentsAll(){
        axios.get(this.$store.getters.getUrl + 'api/groups/' + this.activity.course.group_id)
        .then(function (response) {
            this.studentsGroup = response.data.data[0].students;
            return true;
        }.bind(this))
        .catch((error)=>{
          this.$router.push('login'); 
        });
      },
      addToList(id){
        let dateTime = new Date();
        dateTime = (dateTime.getFullYear() + "-" + (dateTime.getMonth()+1) + "-" + dateTime.getDate() + " " + dateTime.getHours() + ":" + dateTime.getMinutes() + ":" + dateTime.getSeconds());
        this.attandanceList.push({student_id: id, activity_id: this.activity.id, data_presence: dateTime});
        console.log(this.attandanceList);
      },
      isOnList(id){
        for(var i = 0; i < this.attandanceList.length; i++){
          if(this.attandanceList[i].student_id === id){
            return true;
          }
        }
        return false;
      },
      addList(){
        this.activity.checked = true;
        axios.put(this.$store.getters.getUrl + 'api/activities/' + this.activity.id, this.activity).then((response) =>{
            this.success = response.data['created'];
          })
          .catch(function(error){
            this.errors.push("Something went wrong");
          }.bind(this));

        for(var i = 0; i < this.attandanceList.length; i++){
          axios.post(this.$store.getters.getUrl + 'api/attandancelist', this.attandanceList[i]).then((response) =>{
            this.success = response.data['created'];
          })
          .catch(function(error){
            this.errors.push("Something went wrong");
          }.bind(this));
        }
        alert("Attandance list was added");
      },
     isChecked(){
      axios.get(this.$store.getters.getUrl + 'api/activities/'+ this.activity.id)
      .then(function (response) {
          this.isCheckedBool = response.data.data[0].checked;
          if(this.isCheckedBool === 1){
            alert("This activity was checked before!");
            this.$router.push('/attandancelist/add'); 
          }
      }.bind(this))
      .catch((error)=>{
        //this.$router.push('/login'); 
      });
      if(this.isCheckedBool === 1){
        return false;
      }else{
        return true;
      }
    }*/
  },
  created: function () {
    if(!window.$cookies.get('token')){
      //this.$router.push('/login'); 
    }
    if(parseInt(localStorage.getItem('Expiration')) + 600000 < new Date().getTime() ){
      let alert = {content: 'Your token get expiered. Please login again.)', alertClass: "warning"};
      this.$emit('setAlert', alert);
      //this.$router.push('/login'); 
    }
    this.getActivities();
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

@import '@/styles/variables.scss';

</style>
