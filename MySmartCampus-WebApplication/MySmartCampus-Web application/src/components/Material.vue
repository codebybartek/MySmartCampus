<template>
  <div class="single_content">
    <div class="center_content">
      <span class="close_btn" v-on:click="closeBtn()">&times;</span>
      <h1>{{material.course_title}}</h1>
        <h4>Activities:</h4>
        <ul class="course_activities">
          <li v-for="activity in material.activities" class="activity_item">
            <router-link :to="'./activity/' + activity.activity_hash ">
              {{activity.activity_title}} - <span>{{activity.activity_date.split(" ")[0]}}</span>
            </router-link>
            <ul class="materials">
              <li v-for="material in activity.materials" class="material_item">
                <a class="more" v-on:click="showSingle(material)" ><i class="fa fa-file" aria-hidden="true"></i> {{material.title}}</a>
                <a class="attachment" v-bind:href="material.attachment_url"><i class="fa fa-paperclip" aria-hidden="true"></i></a>
                <span class="material_content">{{material.content}}</span>
              </li>
            </ul>
          </li>   
        </ul>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'Material',
    props: ['material_props'],
    data() {
      return {
        material_hash: this.$route.params.hash,
        material: null,
      }
    },
    methods: {
      getMaterial(){
        
        let material_url;
        if(window.$cookies.get('user_role') == "professor"){
            material_url = '/materials/';
        }else{
            material_url = '/material/';
        }

        axios.get(this.$store.getters.getUrl + material_url + this.material_hash)
        .then(function (response) {
            this.material = response.data;
        }.bind(this))
        .catch((error)=>{
          console.log(error);
          this.$router.push('/login'); 
        });
      },
      closeBtn(){
        if(!this.material_hash){
          this.$emit('closeSingleContent');
        }else{
          this.$router.push('/'); 
        }
      }

    },
    created: function(){
      if(window.$cookies.get('token')){
        if(parseInt(localStorage.getItem('Expiration')) + 600000 < new Date().getTime() ){
          let alert = {content: 'Your token get expiered. Please login again.)', alertClass: "warning"};
          this.$emit('setAlert', alert);
          this.$router.push('/login'); 
        }
        if(this.material_hash){
            console.log(this.material_hash);
            this.getMaterial();
        }else{
          console.log(this.material_props);
          this.material = this.material_props;
        }
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

  .course_activities{
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    width: 600px;

    .activity_item{
      width: 300px;
      margin-top: 30px;

      .material_item{
        a{
          i{
            margin-left: 5px;
          }
        }
        .material_content{
          display: block;
        }
      }
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
