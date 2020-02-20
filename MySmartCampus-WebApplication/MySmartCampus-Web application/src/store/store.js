import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
     serverUrl: 'http://vps740401.ovh.net/api',
     activities: [],
     course: '',
     selectedDate: "",
     request_add_to_group: null
  },
  getters: {
    	getRequestAddToGroup(state){
    		return state.request_add_to_group;
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
  	addGroupRequest(state, payload){
  		state.request_add_to_group = payload.request_add_to_group;
  	},
    setActivities(state, payload){
        state.activities = payload.activities;
    },
    setSelectedDate(state, payload){
        state.selectedDate = payload.selectedDate;
    }
}
});