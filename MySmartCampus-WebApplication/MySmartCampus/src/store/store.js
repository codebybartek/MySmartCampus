import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
     name: '',
     token: null,
     serverUrl: 'http://vps740401.ovh.net/api',
     activities: [],
     course: '',
     selectedDate: ""
  },
  getters: {
    	getName(state){
    		return state.name;
    	},
      getToken(state){
          return state.token;
      },
      getUrl(state){
          return state.serverUrl;
      },
      getActivities(state){
          return state.activities;
      },
      getSelectedDate(state){
          return state.selectedDate;
      }
  },
  mutations: {
  	setName(state, payload){
  		state.name = payload.name;
  	},
    setToken(state, payload){
        state.token = payload.token;
    },
    setActivities(state, payload){
        state.activities = payload.activities;
    },
    setSelectedDate(state, payload){
        state.selectedDate = payload.selectedDate;
    }
}
});