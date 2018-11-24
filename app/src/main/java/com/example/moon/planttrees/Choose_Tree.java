package com.example.moon.planttrees;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Choose_Tree extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ListView listView ;
    ArrayList<String> arrayList;
    ArrayAdapter arrayAdapter;
    private String TAG = "myTag";
    String soil_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose__tree);
        listView = (ListView)findViewById(R.id.list_view);
        arrayList = new ArrayList<>();
        Intent intent = getIntent();
        soil_type = intent.getStringExtra("soil");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Info.soiltable).child(soil_type).child("tree");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ObjectForTrees objectForTrees = dataSnapshot.getValue(ObjectForTrees.class);
                if(objectForTrees!=null) {



                    String one = objectForTrees.getTreeOne();
                    String two = objectForTrees.getTreeTwo();
                    String[] list_one = one.split(",");
                    String[] list_two = two.split(",");

                    for(int i=0;i<list_one.length;i++){
                        arrayList.add(list_one[i]);
                    }

                    for (int j=0;j<list_two.length;j++){
                        arrayList.add(list_two[j]);
                    }


                    arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
                    listView.setAdapter(arrayAdapter);
                    arrayAdapter.notifyDataSetChanged();
                }else {
                    Log.i(TAG, "onDataChange: "+"NULL OBJECT");
                    Log.i(TAG, "onDataChange: "+soil_type);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
