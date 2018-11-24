package com.example.moon.planttrees;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GiveAnswer extends AppCompatActivity {

    EditText et_answer;
    Button btn_submit_ans;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_answer);
        key = getIntent().getStringExtra("key");
        init_views();
        init_variables();
        init_functions();
        init_listeners();

    }

    private void init_views() {
        et_answer = (EditText)findViewById(R.id.et_give_answer);
        btn_submit_ans = (Button)findViewById(R.id.btn_submit_answer);
    }

    private void init_variables() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Info.Question_table).child(key).child(Info.answer_table);
    }

    private void init_functions() {

    }

    private void init_listeners() {
        btn_submit_ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(et_answer.getText().toString())){
                    String ans = et_answer.getText().toString();
                    String ans_key = databaseReference.push().getKey();
                    CreateObject createObject = new CreateObject(ans,Info.getCurrent_user(),ans_key);
                    Task<Void> voidTask = databaseReference.child(ans_key).setValue(createObject);
                    voidTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"Successfully Answered",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Failed to answer",Toast.LENGTH_SHORT).show();
                        }
                    });


                }else{
                    Toast.makeText(getApplicationContext(),"Answer Field Empty",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
