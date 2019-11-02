<template>
  <div class="container-fluid" v-bind:class="{fixedTop: fixedTop}">
    <div class="row top" v-bind:class="{showTopMobile: showMenu}">
      <header class="col-12 col-md-3 col-lg-3 col-xl-3 header padding-30">
        <router-link to="/"><img src="../../assets/logo.png" alt="B.Koziel"></router-link>
      </header>
      <div id="burger" v-bind:class="{open: isActive}" v-on:click="clickBurger()">
        <span></span>
        <span></span>
        <span></span>
      </div>
      <nav class="col-12 col-md-9 col-lg-7 col-xl-6 navbar" v-bind:class="{show: showMenu}" key="navbar">
        <ul>
          <li key="1"><router-link to="/">Home <i class="fa fa-home" aria-hidden="true"></i></router-link></li>
          <li key="2"><router-link to="/subjects">Subjects <i class="fa fa-university" aria-hidden="true"></i></router-link></li>
          <li key="3"><router-link to="/courses">Courses <i class="fa fa-map-o" aria-hidden="true"></i></router-link></li>
          <li key="4"><router-link to="/activities">Activities <i class="fa fa-calendar-check-o" aria-hidden="true"></i></router-link></li>
          <li key="5"><router-link to="/news">News <i class="fa fa-newspaper-o" aria-hidden="true"></i></router-link></li>
          <li key="6"><router-link to="/materials">Materials <i class="fa fa-map-o" aria-hidden="true"></i></router-link></li>
        </ul>
      </nav>
      <aside class="col-md-3 col-lg-2 col-xl-3 left_menu">
        <ul>
          <li>
            <div class="search_form" v-if="searchForm">
                <input type="text" name="search" class="search_input">
                <a class="search_submit"><i class="fa fa-search" aria-hidden="true"></i></a>
            </div>
            <a class="search_btn" v-on:click="showSearch()"><i class="fa fa-search" aria-hidden="true" v-show="!searchForm"></i><i class="fa fa-times" aria-hidden="true" v-show="searchForm"></i></router-link></a>
          </li>
          <li class="user">
            <i class="fa fa-user-o" aria-hidden="true"></i>
            <div class="profile_info">
              <span class="user_data"><img src="../../assets/user.jpg" alt="user image">{{user_name}}</span>
              <span class="edit_account"><i class="fa fa-pencil-square-o" aria-hidden="true"></i> Edit account</span>
              <span @click="logout()" class="log_out"><i class="fa fa-sign-out" aria-hidden="true"></i> Logout</span>
            </div>
          </li>
        </ul>
      </aside>
    </div>
  </div>
</template>

<script>
export default {
  name: 'NavbarComponent',
  data () {
    return {
    	user: null,
      isActive: false,
      showMenu: false,
      opacity: 1,
      lastScrollPosition: 0,
      fixedTop: false,
      searchForm: false,
      user_role: '',
      user_name: ''
    }
  },
  methods: {
    clickBurger: function(){
      this.isActive = !this.isActive;
      this.showMenu = !this.showMenu;

    },
    handleScroll () {
      const currentScrollPosition = window.pageYOffset || document.documentElement.scrollTop
      if (currentScrollPosition < 0) {
        return
      }
      // Stop executing this function if the difference between
      // current scroll position and last scroll position is less than some offset
      if (Math.abs(currentScrollPosition - this.lastScrollPosition) < 0) {
        return
      }
      this.showNavbar = currentScrollPosition < this.lastScrollPosition
      this.lastScrollPosition = currentScrollPosition
      if(currentScrollPosition > 1){
          this.fixedTop = true;
      }else{
        this.fixedTop = false;
      }
    },
    showSearch(){
      this.searchForm = !this.searchForm;
    },
    isLogged(){
			axios.get(this.$store.getters.getUrl + '/user')
		    .then(function (response) {
		        this.user = response.data;
		    }.bind(this))
		    .catch((error)=>{
		      this.$router.push('/login'); 
		    });
	  },
    logout(){
      window.$cookies.remove("token");
      window.$cookies.remove("user_name");
      window.$cookies.remove("user_role");
      localStorage.clear();
      this.$router.push('/login'); 
    }
  },
  created () {
    window.addEventListener('scroll', this.handleScroll);
    this.user_name = window.$cookies.get('user_name');
    this.user_role = window.$cookies.get('user_role');
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">

@import '@/styles/variables.scss';

h1, h2 {
  font-weight: normal;
}

a:hover{
  text-decoration: none;
}

ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
}

li {
  display: inline-block;
  padding: 0 10px;
}

a {
  color: #42b983;
}

.top{
  align-items: center;
  padding: 10px 0;
  position: absolute;
  width: 100%;
  height: 75px;
  z-index: 999;
  background: $dark_grey;
  transition: $standart_transition;

  a{
    color: $basic_color;
  }

  &:after{
    height: 1px;
    background-color: $basic_color;
    content: "";
    position: absolute;
    display: block;
    z-index: 999;
    bottom: 0;
    right: 0;
    left: 0;
    animation-name: showLinie2;
    animation-duration: 0.1s;
  }
}

.fixedTop{
  .top{
    position: fixed;
    top: 0;
    width: 100%;
    max-width: 1700px;
    background: rgba(0, 0, 0, 0.25);
  }
}

.header{
  a{
    position: relative;
    height: 100%;
    display: inline-block;
    float: left;

    img{
      max-width: $logo_width;
    }
  }
}

.navbar{
  justify-content: center;
  padding-left: 0;
  padding-right: 0;

  li{
    position: relative;
    display: inline-block;

    &:first-child a:hover > i{
      transition: 1s;
      transition-delay: 0.5s;
    }
  }
  li:hover a:before{
    width: 100%;
  }
  a{
    font-family: 'Dancing Script', cursive;
    font-size: $basic;

    &:before{
      left: 0;
      right: 0;
      width: 0;
      content: "";
      margin: 0 auto;
      position: absolute;
      bottom: -20px;
      height: 1.2px;
      background-color: $basic_blue;
      transition: 1.2s;
      opacity: 0.7;
      }

      &:hover > i{
        color: $basic_blue;
        transition: 1s;
      };
  }
}

.left_menu{
  padding: 0 30px;
  
  ul{
    display: flex;
    justify-content: flex-end;
  }

  li{
    position: relative;
  }

  a{

    &:hover{
      color: $basic_blue;
    }
  }

  .search_btn{
    cursor: pointer;
  }

  .user{
    z-index: 9999;
    i{
      cursor: pointer;

      &:hover{
        color: $basic_blue;
      }
    }

    .profile_info{
      display: none;
      font-size: $basic_small;
      position: absolute;
      right: 5px;
      width: 130px;
      background: rgba(0, 0, 0, 0.25);
      padding: 15px;

      .edit_account{
        cursor: pointer;

        &:hover{
          color: #f5c242;
        }
      }

      .log_out{
        cursor: pointer;

        &:hover{
          color: $basic_red;
        }
      }

      span{
        display: block;
        width: 100%;

        img{
          max-width: 50px;
          border-radius: 100%;
          padding-right: 10px;
          padding-left: 10px;
          margin-bottom: 10px;
          margin-top: 5px;
        }  
      }

      &:hover{
        display: block;
      }
    }

    &:hover > .profile_info{
      display: block;
    }

  }

  .search_form{
    display: flex;
    position: absolute;
    align-items: center;
    left: -237px;
    top: -3px;
    z-index: 999;
    transition: 1s;
    i{
      padding: 5px 7px;
      position: relative;
      cursor: pointer;
    }

    input{
      background: transparent;
      border: 0;
      border-bottom: 1px solid $basic_color;
      color: $basic_color;
      padding: 2px 5px;
    }

    a{
      background-color: $basic_blue;
      height: 100%;
      position: absolute;
      right: 0;
      display: flex;
      align-items: center;

      &:hover{
        color: $basic_color;
        background-color: $basic_red;  
      }
    }
  }
}

/*BURGER STYLE*/ 

#burger{
  width: 30px;
  height: 20px;
  position: relative;
  margin: 15px auto;
  -webkit-transform: rotate(0deg);
  -moz-transform: rotate(0deg);
  -o-transform: rotate(0deg);
  transform: rotate(0deg);
  -webkit-transition: .5s ease-in-out;
  -moz-transition: .5s ease-in-out;
  -o-transition: .5s ease-in-out;
  transition: .5s ease-in-out;
  cursor: pointer;
  display: none;
  z-index: 999;

  span {
    display: block;
    position: absolute;
    height: 4px;
    width: 100%;
    background: $basic_color;
    background: linear-gradient(90deg, $basic_color 0%, $basic_color 0%, rgba(202,205,198,1) 100%, rgba(0,212,255,1) 100%);
    border-radius: 6px;
    opacity: 1;
    left: 0;
    -webkit-transform: rotate(0deg);
    -moz-transform: rotate(0deg);
    -o-transform: rotate(0deg);
    transform: rotate(0deg);
    -webkit-transition: .25s ease-in-out;
    -moz-transition: .25s ease-in-out;
    -o-transition: .25s ease-in-out;
    transition: .25s ease-in-out;

    &:nth-child(1) {
      top: 0px;
      -webkit-transform-origin: left center;
      -moz-transform-origin: left center;
      -o-transform-origin: left center;
      transform-origin: left center;
    }
    &:nth-child(2) {
      top: 10px;
      -webkit-transform-origin: left center;
      -moz-transform-origin: left center;
      -o-transform-origin: left center;
      transform-origin: left center;
    }
    &:nth-child(3) {
      top: 20px;
      -webkit-transform-origin: left center;
      -moz-transform-origin: left center;
      -o-transform-origin: left center;
      transform-origin: left center;
    }
  }
}

#burger.open {
  span{
    &:nth-child(1) {
      -webkit-transform: rotate(45deg);
      -moz-transform: rotate(45deg);
      -o-transform: rotate(45deg);
      transform: rotate(45deg);
      top: -1px;
      left: 4px;
    }
    &:nth-child(2) {
      width: 0%;
      opacity: 0;
    }
    &:nth-child(3) {
      -webkit-transform: rotate(-45deg);
      -moz-transform: rotate(-45deg);
      -o-transform: rotate(-45deg);
      transform: rotate(-45deg);
      top: 20px;
      left: 3px;
    }
  }
}

/*ANIMATIONS*/

@-webkit-keyframes showLinie {
  from {width: 0}
  to {width: 100%}
}
@-webkit-keyframes showLinie2 {
  from {width: 0}
  to {width: 100%}
}

@media screen and (max-width: 992px){
.top{
  a{
    font-size: 16px;
  }
}
.header{
  a{
    img{
      max-width: 180px;
    }
  }
}
.navbar{
  justify-content: flex-end;
  padding: 0;
}
.padding-30{
  padding: 0 15px;
}
.left_menu{
  position: absolute;
  right: 0;
  padding: 0;
  top: 50vh;
  transform: translateY(-50px);
  width: auto;
  z-index: 999;

  ul{
    display: block;
  }

  li{
    display: block;
  }

  a{
    font-size: 22px;
    padding: 5px 5px;
    display: inline-block;

    &:hover{
        margin-top: -5px;
        margin-right: -5px;
        margin-bottom: 5px;
        margin-left: 5px;
    }
  }

  .search_form{
    background: rgba(0, 0, 0, 0.25);
    top: 5px;
    left: -225px;

    input{
      padding: 5px;
    }

    a{
      &:hover{
        margin: 0;
      }
    }
  }
}

}

@media screen and (max-width: 767px){

.top{
  padding-bottom: 0;
  padding-top: 0;
  &:after{
    display: none;
  }
}
.showTopMobile{
  background: #1d1d1d!important;

  .search_form{
    input{
      background-color: #fff;
    }
  }
}
#burger{
  display: inline-block;
  top: 12px;
  right: 15px;
  position: absolute;
}
.navbar{
  width: 0;
  height: 0;
  position: absolute;
  height: 0;
  margin-top: 35px;

  ul{
    display: table;
    width: 100%;

    a{
      font-size: 18px;
      display: inline-block;
      padding: 7px 0;

      &:before{
        bottom: 1px;
      }
    }

    &:after{
      height: 1px;
      background-color: #c8c8c8;
      content: "";
      position: absolute;
      bottom: 0;
      display: block;
      z-index: 999;
      right: 0;
      left: 0;
      animation-name: showLinie;
      animation-duration: 2s;
    }   

    li{
      width: 100%;
      text-align: center;
      opacity: 0;
      transition: 0.5s;
    }
  }
}

.show{
  width: 100%;
  height: auto;

  .navbar{
    position: relative;
  }

  ul{
	  position: absolute;
	  top: 0;
	  left: 0;
	  z-index: 999;

	  &:after{
	    animation-name: showLinie2;
	    animation-duration: 2s;
	    right: 0;
	    bottom: 0;
	  }

	  li{
	    &:nth-child(1){
	      transition-delay: 0.3s;
	      opacity: 1;
	      background: #1d1d1d;
	    }
	    &:nth-child(2){
	      transition-delay: 0.5s;
	      opacity: 1;
	      background: #1d1d1d;
	    }
	    &:nth-child(3){
	      transition-delay: 0.7s;
	      opacity: 1;
	      background: #1d1d1d;
	    }
	    &:nth-child(4){
	      transition-delay: 0.9s;
	      opacity: 1;
	      background: #1d1d1d;
	    }
	    &:nth-child(5){
	      transition-delay: 1.1s;
	      opacity: 1;
	      background: #1d1d1d;
	    }
	    &:nth-child(6){
	      transition-delay: 1.3s;
	      opacity: 1;
	      background: #1d1d1d;
	      padding-bottom: 10px;
	    }
  	}
  }
}

}

</style>
