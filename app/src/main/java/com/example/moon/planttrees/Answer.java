package com.example.moon.planttrees;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Answer extends AppCompatActivity {

    TextView question;
    ListView list_ans;
    Button btn_ans;
    String key,ques;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<CreateObject> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Intent intent = getIntent();
        key = intent.getStringExtra("ans");
        ques = intent.getStringExtra("que");

        init_views();
        init_variables();
        init_functions();
        init_listeners();
    }

    private void init_views() {
        question = (TextView)findViewById(R.id.tv_ans);
        list_ans = (ListView)findViewById(R.id.lv_ans);
        btn_ans = (Button)findViewById(R.id.btn_ans);
    }

    private void init_variables() {
        arrayList = new ArrayList<>();
        question.setText(ques);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Info.Question_table).child(key).child(Info.answer_table);
    }

    private void init_functions() {
        setDataToList();
    }

    private void setDataToList() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                   CreateObject CreateObject = snapshot.getValue(CreateObject.class);
                   arrayList.add(CreateObject);
                }
                Question_Adapter question_adapter = new Question_Adapter(arrayList,getApplicationContext());
                list_ans.setAdapter(question_adapter);
                question_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void init_listeners() {
        btn_ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Answer.this,GiveAnswer.class);
                intent.putExtra("key",key);
                startActivity(intent);
            }
        });
    }
}
