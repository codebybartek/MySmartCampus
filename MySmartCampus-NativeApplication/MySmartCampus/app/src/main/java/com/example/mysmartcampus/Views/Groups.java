package com.example.mysmartcampus.Views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.mysmartcampus.Adapters.GroupsListAdapter;
import com.example.mysmartcampus.Models.Group;
import com.example.mysmartcampus.R;

import java.util.ArrayList;

public class Groups extends Fragment {

    View myView;

    GroupsListAdapter adapterListGroups;
    ListView mListViewGroups;

    public static ArrayList<Group> groups = new ArrayList<>();
    public static Group group;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.groups, container, false);

        mListViewGroups = (ListView) myView.findViewById(R.id.listViewGroups);


        if(groups != null) {
            ShowListGroups();
        }

        return myView;
    }

    public void ShowListGroups(){
        final FragmentManager manager = getActivity().getSupportFragmentManager();
        adapterListGroups = new GroupsListAdapter(myView.getContext(), R.layout.adapter_view_courses, groups);
        mListViewGroups.setAdapter(adapterListGroups);
        mListViewGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                group =  groups.get(position);
                manager.beginTransaction().replace(R.id.api_view, new GroupContent()).commit();
            }
        });
    }
}
