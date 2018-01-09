package com.lingayatpanchasanagam.sbjm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.lingayatpanchasanagam.sbjm.adapter.MembersListAdapter;
import com.lingayatpanchasanagam.sbjm.model.Child;
import com.lingayatpanchasanagam.sbjm.model.Parent;

import java.util.ArrayList;

public class ListOfMembersActivity extends AppCompatActivity {

    ExpandableListView membersListview;
    MembersListAdapter adapter;
    ArrayList<Parent> membersList;
    ArrayList<Child> members;
    Parent parent;
    Child child;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_members);
        membersListview = findViewById(R.id.membersList);
        membersList = new ArrayList<>();
        adapter= new MembersListAdapter(this , populateData());
        membersListview.setAdapter(adapter);

    }

    private ArrayList<Parent> populateData(){
        membersList = new ArrayList<>();
        for (int i =0; i< 6; i++) {
            members = new ArrayList<>();
            child = new Child();
            child.setName("basavaraj");
            child.setNumber("9731276143");
            child.setTaluk("Bijapur");
            members.add(child);

            child = new Child();
            child.setName("basavaraj");
            child.setNumber("9731276143");
            child.setTaluk("Bijapur");
            members.add(child);
            child = new Child();
            child.setName("basavaraj");
            child.setNumber("9731276143");
            child.setTaluk("Bijapur");
            members.add(child);

            child = new Child();
            child.setName("basavaraj");
            child.setNumber("9731276143");
            child.setTaluk("Bijapur");
            members.add(child);

            parent = new Parent();
            parent.setDivisionName("Bijapur");
            parent.setChildren(members);
            membersList.add(parent);
        }
        return membersList;
    }
}
