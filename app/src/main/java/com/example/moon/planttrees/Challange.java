package com.example.moon.planttrees;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableListDialog;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.HashMap;
import java.util.Map;

public class Challange extends AppCompatActivity {


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SearchableSpinner searchableSpinner;
    TextView tvListData;
    private String TAG = "MyTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challange);

        init_views();
        init_variables();
        init_functions();
        init_listeners();
    }

    private void init_views() {
        searchableSpinner = (SearchableSpinner)findViewById(R.id.searchableSpinner);
        tvListData = (TextView)findViewById(R.id.tv_list_data);
    }

    private void init_variables() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Info.treeTable);
        searchableSpinner.setTitle("Select Item");
        searchableSpinner.setPositiveButton("OK");
    }

    private void init_functions() {

    }

    private void init_listeners() {

    }

    public void search(View view) {
        String selectedItem = (String)searchableSpinner.getSelectedItem();
        Log.i(TAG, "search: "+selectedItem);
        DatabaseReference child = databaseReference.child(selectedItem).child("about");
        child.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              String v = dataSnapshot.getValue(String.class);
              tvListData.setText(v);

//                String tv_ = dataSnapshot.getChildren().getValue(String.class);
//                tvListData.setText(tv_);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
