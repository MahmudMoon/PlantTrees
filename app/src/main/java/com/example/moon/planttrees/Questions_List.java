package com.example.moon.planttrees;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Questions_List extends AppCompatActivity {

    private static final String TAG = "MyTag";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ListView listView;
    ArrayList<CreateObject> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions__list);
        Intent intent = getIntent();
        init_views();
        init_variables();
        init_listeners();

        String type = intent.getStringExtra("que");
        if(TextUtils.equals(type,"all")){
            getAllQuestions();
        }else if(TextUtils.equals(type,"my")){
            getMyQuestions();
        }else {

        }



    }

    private void init_views() {
        listView = (ListView)findViewById(R.id.list_show);
    }

    private void init_functions() {
        Log.i(TAG, "init_functions: "+arrayList.size());
       Question_Adapter question_adapter = new Question_Adapter(arrayList,getApplicationContext());
       listView.setAdapter(question_adapter);
       question_adapter.notifyDataSetChanged();
    }

    private void init_variables() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Info.Question_table);
        arrayList = new ArrayList<>();
    }

    private void init_listeners() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 Intent intent = new Intent(Questions_List.this,Answer.class);
                 intent.putExtra("ans", arrayList.get(i).getKey());
                 intent.putExtra("que",arrayList.get(i).getQuestion());
                 startActivity(intent);
            }
        });
    }

    private void getMyQuestions() {

        Log.i(TAG, "getMyQuestions: "+"reached");
        databaseReference.addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {

                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CreateObject CreateObject = snapshot.getValue(CreateObject.class);
                    if(TextUtils.equals(CreateObject.getUserName(),Info.getCurrent_user())) {
                        arrayList.add(CreateObject);
                        Log.i(TAG, "onDataChange: " + CreateObject.getKey());
                    }
                }

                init_functions();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getAllQuestions() {
        Log.i(TAG, "getAllQuestions: "+"reached");
        databaseReference.addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {

                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CreateObject CreateObject = snapshot.getValue(CreateObject.class);
                    arrayList.add(CreateObject);
                    Log.i(TAG, "onDataChange: "+ CreateObject.getKey());
                }

                init_functions();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
