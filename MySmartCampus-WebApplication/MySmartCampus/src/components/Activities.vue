<template>
  <div class="container navbar-offset-top">
    <Activity class="single_hidden" v-if="showSingleContent" v-bind:class="{show_single_content: showSingleContent}" :activity_props="activity_props" v-on:closeSingleContent="closeSingleContent()"/>
    <div class="row" v-bind:class="{hide: showSingleContent}">
      <div class="col-12 col-sm-6 activitiesCalendar">
          <CalendarShow />
       </div>
       <div class="add_activity col-12 col-lg-6">
         <h1 @click="setDates()">Add your activity</h1>     
         <section class="navigation_buttons">
           <h3 class="nav-button" @click="showRecurent = !showRecurent; showSimple = false">{{ calendars[1].title }}</h3>
           <h3 class="nav-button" @click="showSimple = !showSimple; showRecurent = false">{{ calendars[0].title }}</h3>
         </section>
        <div v-if="showSimple" class="activity simpleActivity col-12"> 
            <span class="label">Date</span>
            <Calendar       
            v-model="calendars[0].value"
            :range="calendars[0].range"
            :lang="calendars[0].lang"
            :firstDayOfWeek="calendars[0].firstDayOfWeek"
            :input-class="calendars[0].inputClass"
            :position="calendars[0].position"
            :disabled-start-date="calendars[0].disabledStartDate"
            :text-format="calendars[0].textFormat"
            :date-format="calendars[0].dateFormat"
            :disabled-end-date="calendars[0].disabledEndDate"/>
            <span class="label">Time & Duration(min)</span>
            <div class="datetime">
              <div class="time">
                <input type="number" placeholder="hh" v-model="hh" min="0" max="23" @change="evaluate({pointer: 'hh'})" />
                <input type="number" placeholder="mm" v-model="mm" min="0" max="59" step="30" @change="evaluate({pointer: 'mm'})" />
                <span v-show="notNull" @click="clear">&times;</span>
              </div>
              <div class="duration">
                <input type="number" placeholder="Duration" v-model="activity.duration" min="0" max="600" step="15" @change="evaluate({pointer: 'hh'})" />
              </div>
            </div>
            <div class="content">
              <input v-model="activity.title" type="text" placeholder="Activity title" class="title"/>
              <select v-model="activity.course_id" class="courseId">    
                <option value="0" selected>Free activity</option>   
                <option v-for="course in courses" :value="course.id">
                  {{course.name}}
                </option>                                                   
              </select>
              <input v-model="activity.class_room" type="text" placeholder="Activity classroom" class="title"/>
              <span class="error" v-for="error in errors">{{error}}</span>
              <button class="u-full-width button-primary" type="submit" v-on:click="addActivity(false)" >Add activity</button>
            </div>
          </div>
          <div v-if="showRecurent" class="activity recurentActivity col-12"> 
            <span class="label">Date</span>
            <Calendar
            v-model="calendars[1].value"
            :range="calendars[1].range"
            :lang="calendars[1].lang"
            :firstDayOfWeek="calendars[1].firstDayOfWeek"
            :input-class="calendars[1].inputClass"
            :position="calendars[1].position"
            :disabled-start-date="calendars[1].disabledStartDate"
            :text-format="calendars[1].textFormat"
            :date-format="calendars[1].dateFormat"
            :disabled-end-date="calendars[1].disabledEndDate"/>
            <span class="label">Time & Duration(min)</span>
            <div class="datetime">
              <div class="time">
                <input type="number" placeholder="hh" v-model="hh" min="0" max="23" @change="evaluate({pointer: 'hh'})" />
                <input type="number" placeholder="mm" v-model="mm" min="0" max="59" step="30" @change="evaluate({pointer: 'mm'})" />
                <span v-show="notNull" @click="clear">&times;</span>
              </div>
              <div class="duration">
                <input type="number" placeholder="Duration" v-model="activity.duration" min="0" max="600" step="15" @change="evaluate({pointer: 'hh'})" />
              </div>
            </div>
            <div class="content">
              <select v-model="recurrence" class="recurrence"> 
                <option value="0" selected>Every week</option>    
                <option value="1" selected>Every day</option> 
              </select>
              <input v-model="activity.title" type="text" placeholder="Activity title" class="title"/>
              <select v-model="activity.course_id" class="courseId">    
                <option value="0" selected>Free activity</option>   
                <option v-for="course in courses" :value="course.id">
                  {{course.name}}
                </option>                                                   
              </select>
              <input v-model="activity.class_room" type="text" placeholder="Activity classroom" class="title"/>
              <span class="error" v-for="error in errors">{{error}}</span>
              <button class="u-full-width button-primary" type="submit" v-on:click="addActivity(true)">Add activity</button>
            </div>
          </div>
        </div>
     </div>
     <div class="row" v-bind:class="{hide: showSingleContent}">
       <section class="col-12 activities_show">
        <h2>Activities</h2>
        <table class="table table-hover">
          <tr class="header_table">
              <th>Id</th>
              <th>Title</th>
              <th>Date</th>
              <th>Hour</th>
              <th>Duration</th>
              <th>Classroom</th>
              <th>Check</th>
              <th>Delete</th>
          </tr>
          <tr v-for="activitySelected in activitiesSelected">
              <td>{{activitySelected.id}}</td>
              <td>{{activitySelected.title}}</td>
              <td>{{activitySelected.activityDate.split(" ")[0]}}</td>
              <td>{{activitySelected.activityDate.split(" ")[1]}}</td>
              <td>{{activitySelected.duration}}</td>
              <td>{{activitySelected.class_room}}</td>
              <td><a class="more" v-on:click="showSingle(activitySelected)">more..</a></td>
              <td><a v-on:click="deleteActivity(activitySelected.id)" class="delete"><i class="fa fa-trash-o" aria-hidden="true"></i></a></td>
          </tr>
        </table>
        {{takeActivitiesSelected}}
      </section>
    </div>
  </div>
</template>

<script>


import axios from 'axios';
import Calendar from './calendar.vue';
import CalendarShow from './CalendarShow.vue';
import Activity from './Activity.vue';


export default {
  name: 'Activities',
  components: {
    Calendar,
    CalendarShow,
    Activity
  },
  data() {
    return {
      showSimple: false,
      showRecurent: false,
      showSingleContent: false,
      newdate: null,
      courses: [],
      dates: [],
      errors: [],
      success: "",
      activities: [],
      activitiesSelected: [],
      recurrence: 0,
      activity_props: null,
      hour: null,
      activity: {
        activityDate: "",
        title: '',
        duration: null,
        class_room: '',
        course_id: 0
      },
      calendars: [
        { title: 'Simple activity',
          inputClass: 'exampleDatePicker',
          lang: 'en',
          position: 'left',
          range: false,
          value: new Date(),
          firstDayOfWeek: 'monday'
        },
        { title: 'Recurrent activity',
          inputClass: 'exampleDatePicker',
          lang: 'en',
          position: 'right',
          range: true,
          value: [new Date(), new Date(new Date().getTime() + ( 9 * 24 * 60 * 60 * 1000))]
        }
      ],
      hh: null,
      mm: null,

    }
  },
  computed: {
    takeDate() {
      return this.newdate;
    },
    notNull(){
      return (this.hh !== null || this.mm !== null);
    },
    takeActivitiesSelected() {
      if(this.activitiesSelected.length != 0){
        this.activitiesSelected = [];
      }
      if(this.activities.length != 0){
        let selectedDate = this.$store.getters.getSelectedDate.replace("/", "-");
        selectedDate = selectedDate.replace("/", "-");
        for(var i = 0; i < this.activities.length; i++){
          let date = this.activities[i].activityDate.split(" ");
          if(date[0] === selectedDate){
            this.activitiesSelected.push(this.activities[i]);
          }
          console.log(selectedDate + "------" + this.activities[i].activityDate);
        }
      }
      
    }
  },
  methods: {
    addActivity(isRecurrent) {
      this.errors = [];
      if(!this.activity.title){
        this.errors.push("Activity title is required");
        return;
      }
      if(this.activity.duration===0){
        this.errors.push("Duration is required");
        return;
      }
       if(!this.hh || !this.mm){
        this.errors.push("Time is required");
        return;
      }
      if(this.activity.course_id === 0){
        this.activity.course_id = null;
      }

      this.hour = this.hh + ":" + this.mm + ":00";

      if(isRecurrent){
        this.setDates();
        let length = this.dates.length;
        let recurrence = [{dateStart: this.dates[0], dateEnd: this.dates[length-1], everyDay: 0}];

        for(var i = 0; i < length; i++){
          let activityTemp = {activityDate: this.dates[i] + " " + this.hour, title: this.activity.title, duration: this.activity.duration, class_room: this.activity.class_room, course_id: this.activity.course_id};
          axios.post(this.$store.getters.getUrl + '/activities', activityTemp).then((response) =>{
            let alert = {content: response.data['created'], alertClass: "success"};
            this.$emit('setAlert', alert);
          })
          .catch(function(error){
            this.errors.push("Something went wrong");
          }.bind(this));
        }

      }else{
        let newdate = this.calendars[0].value;
        this.activity.activityDate = (newdate.getFullYear() + "-" + (newdate.getMonth()+1) + "-" + newdate.getDate());
        this.activity.activityDate = this.activity.activityDate + " " + this.hour;

        axios.post(this.$store.getters.getUrl + '/activities', this.activity).then((response) =>{
          let alert = {content: response.data['created'], alertClass: "success"};
          this.$emit('setAlert', alert);
        })
        .catch(function(error){
          this.errors.push("Something went wrong");
        }.bind(this));
      }
    },
    setDates() {
     let newdate = this.calendars[1].value[0];
     this.dates.push(newdate.getFullYear() + "-" + (newdate.getMonth()+1) + "-" + newdate.getDate());
     let rec;
     if(this.recurrence === 0){
        rec = 7;
     }else{
        rec = 1;
     }
     while(1){
      newdate.setDate(newdate.getDate() + rec);
      if(newdate > this.calendars[1].value[1]){
        break;
      }
      this.dates.push(newdate.getFullYear() + "-" + (newdate.getMonth()+1) + "-" + newdate.getDate());
     }
    },
    clear() {
       this.hh = this.mm = null;
    },
    evaluate(settings) {
      // Using the pointer to avoid having a method for each input
      var value = this[settings.pointer];
      if(!isNaN(settings.max) && this[settings.pointer] > settings.max)
      {
        value =  settings.max;
      }
      if(!isNaN(settings.min) && this[settings.pointer] < settings.min)
      {
        value = settings.min;
      }
      this[settings.pointer] = value
      // Always good to emit changes, no?
      this.$emit('change', settings.pointer, value)
    },
    getActivities(){
      axios.get(this.$store.getters.getUrl + '/activities')
        .then(function (response) {
            this.activities= response.data.data;
            this.$store.commit("setActivities", {
              activities: this.activities
            })
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/login'); 
        });
    },
    getCourses(){
      axios.get(this.$store.getters.getUrl + '/courses')
      .then(function (response) {
          this.courses = response.data.data;
      }.bind(this))
      .catch((error)=>{
        this.$router.push('/login'); 
      });
    },
    deleteActivity(id){
      axios.delete(this.$store.getters.getUrl + '/activities/'+ id)
      .then(function (response) {
        let alert = {content: response.data['deleted'], alertClass: "danger"};
        this.$emit('setAlert', alert);
        this.getActivities();
      }.bind(this))
      .catch((error)=>{
        this.$router.push('/login'); 
      });
    },
    showSingle(activity_props){
      this.activity_props = activity_props;
      this.showSingleContent = true;
    },
    closeSingleContent(){
      this.activity_props = null;
      this.showSingleContent = false;
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
    this.getCourses();
    
  }
}
</script>

<style scoped>
.offset_top{
  margin-top: 70px;
}
.datetime{
  display: table;
  margin: 0 auto;
}
input::placeholder{
  color: #c3cbd6;
}
.time, .duration{
  display: inline-block;
  border: 1px solid #fff;
  float: left;
  margin: 30px 10px;
  margin-top: 0;
}
.calendars {
  display: grid;
  width: 100%;
  grid-template-columns: repeat(auto-fill, minmax(350px,1fr));
}
.activity{
  margin-top: 30px;
}
.error{
  color: red;
  display: block;
}
.label{
  display: block;
  margin-top: 15px;
}
.simpleActivity{
  text-align: center;
}
.time span {
  position: relative;
  display: inline-block;
  height: 20px;
  width: 20px;
  cursor: pointer;
  border: none;
  border-radius: 50%;
  text-align: center;
}
input[type=number]{
  border: none;
  text-align: center;
  background-color: transparent;
  width: 110px;
  color: #fff;
}
.nav-button{
  display: inline-block;
  padding-left: 15px;
  padding-right: 15px;
  margin-top: 5px;
}
.navigation_buttons{
  padding: 0;
  margin: 0;
  cursor: pointer;
}
.button-primary{
  margin-bottom: 50px;
  display: inline-block;
  padding: 10px 40px;
  background: transparent;
  border: 1px solid #fff;
  color: #fff;
}
.title, .courseId, .recurrence{
  margin-bottom: 15px;
}
.nav{
  margin: 15px auto;
}
.nav h3{
  border-bottom: 1px solid #c4c4ef;
  width: 45%;
  margin: 0 auto;
  cursor: pointer;
  font-size: 25px;
  padding: 15px 0;
}
.activities_show{
  margin-bottom: 50px;
}
.activitiesCalendar, .add_activity{
  margin-top: 50px;
}
.activities_show .more{
  color: #36a832;
  cursor: pointer;
}
.activities_show .more:hover{
  color: #c3cbd6;  
}
.hide{
  opacity: 0!important;
}

</style>
