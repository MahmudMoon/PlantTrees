package com.example.moon.planttrees;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

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
    ArrayAdapter arrayAdapter;
    private String TAG = "myTag";
    String soil_type;
    ArrayList<ObjectForTrees> arrayList;
    CustomAdapterTree customAdapterTree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose__tree);
        listView = (ListView)findViewById(R.id.list_view);
        arrayList = new ArrayList<>();
        Intent intent = getIntent();
        arrayList = new ArrayList<>();
        soil_type = intent.getStringExtra("soil");


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Info.soiltable).child(soil_type).child("tree");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    ObjectForTrees objectForTrees = snapshot.getValue(ObjectForTrees.class);
                    arrayList.add(objectForTrees);
                    Log.i(TAG, "onDataChange: "+objectForTrees.getName() );
                }
                customAdapterTree = new CustomAdapterTree(arrayList,getApplicationContext());
                listView.setAdapter(customAdapterTree);
                customAdapterTree.notifyDataSetChanged();


//                ObjectForTrees objectForTrees = dataSnapshot.getValue(ObjectForTrees.class);
//                if(objectForTrees!=null) {
//
//
//
//                    String one = objectForTrees.getTreeOne();
//                    String two = objectForTrees.getTreeTwo();
//                    String[] list_one = one.split(",");
//                    String[] list_two = two.split(",");
//
//                    for(int i=0;i<list_one.length;i++){
//                        arrayList.add(list_one[i]);
//                    }
//
//                    for (int j=0;j<list_two.length;j++){
//                        arrayList.add(list_two[j]);
//                    }
//
//
//                    arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
//                    listView.setAdapter(arrayAdapter);
//                    arrayAdapter.notifyDataSetChanged();
//                }else {
//                    Log.i(TAG, "onDataChange: "+"NULL OBJECT");
//                    Log.i(TAG, "onDataChange: "+soil_type);
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(Choose_Tree.this, ScrollingActivity.class);
                intent1.putExtra("name",arrayList.get(i).getName().toString());
                intent1.putExtra("description",arrayList.get(i).getDescription().toString());
                intent1.putExtra("url",arrayList.get(i).getUrl().toString());
                startActivity(intent1);
            }
        });

    }
}
